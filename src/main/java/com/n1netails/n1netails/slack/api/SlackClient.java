package com.n1netails.n1netails.slack.api;

import com.n1netails.n1netails.slack.exception.SlackClientException;
import com.n1netails.n1netails.slack.model.SlackMessage;

/**
 * Slick Client
 * @author shahid foy
 */
public interface SlackClient {

    /**
     * Send slack message
     * @param slackMessage slack message
     */
    void sendMessage(SlackMessage slackMessage) throws SlackClientException;
}
