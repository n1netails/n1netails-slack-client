package com.n1netails.n1netails.slack.api;

import com.n1netails.n1netails.slack.exception.SlackErrorMapper;
import com.n1netails.n1netails.slack.exception.SlackClientException;
import com.n1netails.n1netails.slack.exception.SlackTransportException;
import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.SlackMessage;
import com.n1netails.n1netails.slack.processing.SlackBlockProcessor;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.block.LayoutBlock;

import java.io.IOException;
import java.util.List;

/**
 * Slack Bot Service
 *
 * @author shahid foy
 */
class BotService {
    private final MethodsClient methods;
    private final SlackBlockProcessor blockProcessor;

    /**
     * Bot Service Constructor
     *
     * @param token slack bot token
     */
    public BotService(String token) {
        this.methods = Slack.getInstance().methods(token);
        this.blockProcessor = new SlackBlockProcessor();

    }

    public void send(SlackMessage slackMessage) throws SlackClientException {
        validateBasic(slackMessage);
        try {
            ChatPostMessageRequest request = buildRequest(slackMessage);
            ChatPostMessageResponse response = methods.chatPostMessage(request);
            if (!response.isOk()) {
                throw SlackErrorMapper.map(response);
            }
        } catch (IOException e) {
            throw new SlackTransportException("Network error while calling Slack API", e);
        } catch (SlackApiException e) {
            throw new SlackTransportException("Slack SDK failure for channel: " + slackMessage.getChannel(), e);
        }
    }


    private void validateBasic(SlackMessage message) throws SlackValidationException {
        if (message == null) {
            throw new SlackValidationException("SlackMessage cannot be null");
        }

        if ((message.getText() == null || message.getText().isBlank()) &&
                (message.getBlocks() == null || message.getBlocks().isEmpty()) &&
                (message.getRawBlocks() == null || message.getRawBlocks().isEmpty())) {

            throw new SlackValidationException("Message must contain text or blocks");
        }
    }


    private ChatPostMessageRequest buildRequest(SlackMessage slackMessage) {
        ChatPostMessageRequest.ChatPostMessageRequestBuilder requestBuilder =
                ChatPostMessageRequest.builder()
                        .channel(slackMessage.getChannel())
                        .text(slackMessage.getText());

        List<LayoutBlock> blocks = blockProcessor.process(slackMessage);
        if ((blocks != null && !blocks.isEmpty()))
            requestBuilder.blocks(blocks);
        return requestBuilder.build();
    }


}
