package com.n1netails.n1netails.slack.api;

import com.n1netails.n1netails.slack.api.SlackClientImpl;
import com.n1netails.n1netails.slack.exception.SlackClientException;
import com.n1netails.n1netails.slack.model.SlackMessage;

/**
 * Slack Client interface for sending messages to Slack.
 * <p>
 * This is a sealed interface, allowing only {@link SlackClientImpl} to implement it.
 * Use the {@link #builder()} method to create instances.
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * SlackClient client = SlackClient.builder()
 *                                 .token("xoxb-your-token")
 *                                 .build();
 * client.sendMessage(new SlackMessage("Hello, Slack!"));
 * }</pre>
 *
 * <p>All implementations are expected to handle exceptions via {@link SlackClientException}.</p>
 *
 * @author Shahid Foy and Artur Slimak
 */
public sealed interface SlackClient permits SlackClientImpl {

    /**
     * Sends a message to Slack.
     *
     * @param slackMessage the message to send, must not be null
     * @throws SlackClientException if sending fails (network, authentication, or other Slack API issues)
     */
    void sendMessage(SlackMessage slackMessage) throws SlackClientException;

    /**
     * Returns a new {@link Builder} for constructing a {@link SlackClient}.
     *
     * @return a new builder instance
     */
    static Builder builder() {
        return new SlackClientImpl.Builder();
    }

    /**
     * Builder interface for {@link SlackClient}.
     * <p>
     * This is a sealed interface, allowing only {@link SlackClientImpl.Builder} to implement it.
     * Provides a fluent API for setting configuration parameters.
     * </p>
     */
    sealed interface Builder permits SlackClientImpl.Builder {

        /**
         * Sets the Slack authentication token.
         *
         * @param token the OAuth token for Slack API access
         * @return this builder instance
         */
        Builder token(String token);

        /**
         * Builds the {@link SlackClient} instance using the provided configuration.
         *
         * @return a configured {@link SlackClient}
         * @throws SlackClientException if required parameters are missing or invalid
         */
        SlackClient build() throws SlackClientException;
    }
}
