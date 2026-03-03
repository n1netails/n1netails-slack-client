package com.n1netails.n1netails.slack.api;

import com.n1netails.n1netails.slack.exception.SlackClientException;
import com.n1netails.n1netails.slack.model.SlackMessage;

/**
 * Concrete implementation of {@link SlackClient}.
 * <p>
 * Uses {@link BotService} internally to send messages to Slack.
 * Created via {@link SlackClientImpl.Builder}.
 * </p>
 *
 * Example:
 * <pre>{@code
 * SlackClient client = SlackClientImpl.builder()
 *                                     .token("xoxb-your-token")
 *                                     .build();
 * client.sendMessage(new SlackMessage("Hello!"));
 * }</pre>
 *
 * @author Artur Slimak
 */
final class SlackClientImpl implements SlackClient {

    private final BotService botService;

    private SlackClientImpl(Builder builder) {
        this.botService = new BotService(builder.token);
    }

    @Override
    public void sendMessage(SlackMessage slackMessage) throws SlackClientException {
        botService.send(slackMessage);
    }

    /**
     * Builder for {@link SlackClientImpl}.
     * <p>
     * Implements the {@link SlackClient.Builder} interface.
     * Used to configure and construct an instance of {@link SlackClientImpl}.
     * </p>
     */
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
