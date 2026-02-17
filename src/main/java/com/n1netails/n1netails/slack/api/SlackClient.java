package com.n1netails.n1netails.slack.api;

import com.n1netails.n1netails.slack.api.SlackClientImpl;
import com.n1netails.n1netails.slack.exception.SlackClientException;
import com.n1netails.n1netails.slack.model.SlackMessage;

/**
 * Slick Client
 *
 * @author shahid foy
 */
public sealed interface SlackClient permits SlackClientImpl {

    /**
     * Send slack message
     *
     * @param slackMessage slack message
     */
    void sendMessage(SlackMessage slackMessage) throws SlackClientException;

    static Builder builder() {
        return new SlackClientImpl.Builder();
    }

    sealed interface Builder permits SlackClientImpl.Builder {
        Builder token(String token);

        SlackClient build() throws SlackClientException;
    }
}
