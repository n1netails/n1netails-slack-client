package com.n1netails.n1netails.slack.api;

import com.n1netails.n1netails.slack.exception.SlackApiExceptionWrapper;
import com.n1netails.n1netails.slack.exception.SlackClientException;
import com.n1netails.n1netails.slack.exception.SlackTransportException;
import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.SlackBlock;
import com.n1netails.n1netails.slack.model.SlackMessage;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.block.LayoutBlock;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

/**
 * Slack Bot Service
 *
 * @author shahid foy
 */
class BotService {
    private final MethodsClient methods;

    /**
     * Bot Service Constructor
     *
     * @param token slack bot token
     */
    public BotService(String token) {
        this.methods = Slack.getInstance().methods(token);
    }

    public void send(SlackMessage slackMessage) throws SlackClientException {
        validateSlackMessage(slackMessage);
        try {
            ChatPostMessageRequest.ChatPostMessageRequestBuilder requestBuilder =
                    ChatPostMessageRequest.builder()
                            .channel(slackMessage.getChannel())
                            .text(slackMessage.getText());

            List<LayoutBlock> finalBlocks;
            if (slackMessage.getRawBlocks() != null && !slackMessage.getRawBlocks().isEmpty()) {
                finalBlocks = slackMessage.getRawBlocks();

            } else if (slackMessage.getBlocks() != null && !slackMessage.getBlocks().isEmpty()) {
                finalBlocks = slackMessage.getBlocks().stream()
                        .map(SlackBlock::toLayoutBlock)
                        .toList();
            } else
                finalBlocks = null;

            if (finalBlocks != null && !finalBlocks.isEmpty()) {
                requestBuilder.blocks(finalBlocks);
            }

            ChatPostMessageResponse response = methods.chatPostMessage(requestBuilder.build());

            if (!response.isOk()) {
                throw new SlackApiExceptionWrapper(response.getError());
            }

        } catch (IOException e) {
            throw new SlackTransportException("Network error while calling Slack API", e);
        } catch (SlackApiException e) {
            throw new SlackTransportException("Slack SDK failure" + slackMessage.getChannel(), e);
        }
    }

    private void validateSlackMessage(SlackMessage slackMessage) throws SlackClientException {
        if (slackMessage == null) {
            throw new SlackValidationException("slackMessage cannot be null");
        }

    }
}
