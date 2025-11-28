package com.n1netails.n1netails.slack.service;

import com.n1netails.n1netails.slack.exception.SlackClientException;
import com.n1netails.n1netails.slack.model.SlackMessage;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;

/**
 * Slack Bot Service
 * @author shahid foy
 */
public class BotService {

    private final String token;

    /**
     * Bot Service Constructor
     * @param token slack bot token
     */
    public BotService(String token) {
        this.token = token;
    }

    public void send(SlackMessage slackMessage) throws SlackClientException {
        try {
            MethodsClient methods = Slack.getInstance().methods(token);
            ChatPostMessageRequest.Builder requestBuilder = ChatPostMessageRequest.builder()
                    .channel(slackMessage.getChannel())
                    .text(slackMessage.getText());

            if (slackMessage.getBlocks() != null && !slackMessage.getBlocks().isEmpty()) {
                requestBuilder.blocks(slackMessage.getBlocks());
            }

            methods.chatPostMessage(requestBuilder.build());
        } catch (Exception e) {
            throw new SlackClientException("Failed to send Slack message", e);
        }
    }
}
