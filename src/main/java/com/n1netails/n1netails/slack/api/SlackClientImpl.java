package com.n1netails.n1netails.slack.api;

import com.n1netails.n1netails.slack.exception.SlackClientException;
import com.n1netails.n1netails.slack.model.SlackMessage;

/**
 * Slack Client Implementation
 *
 * @author shahid foy
 */
final class SlackClientImpl implements SlackClient {

    private final BotService botService;

    public SlackClientImpl(Builder builder) {
        this.botService = new BotService(builder.token);
    }

    /**
     * Send slack notification
     *
     * @param slackMessage slack message
     */
    @Override
    public void sendMessage(SlackMessage slackMessage) throws SlackClientException {
        botService.send(slackMessage);
    }

    public static final class Builder implements SlackClient.Builder {
        private String token;

        @Override
        public SlackClient.Builder token(String token) {
            this.token = token;
            return this;
        }

        @Override
        public SlackClient build() throws SlackClientException {
            if (this.token == null || this.token.isBlank())
                throw new SlackClientException("Token must be provided");

            return new SlackClientImpl(this);
        }
    }
}
