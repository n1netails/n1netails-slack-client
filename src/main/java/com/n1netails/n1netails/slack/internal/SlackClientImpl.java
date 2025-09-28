package com.n1netails.n1netails.slack.internal;

import com.n1netails.n1netails.slack.api.SlackClient;
import com.n1netails.n1netails.slack.exception.SlackClientException;
import com.n1netails.n1netails.slack.model.SlackMessage;
import com.n1netails.n1netails.slack.service.BotService;

/**
 * Slack Client Implementation
 * @author shahid foy
 */
public class SlackClientImpl implements SlackClient {

    private final BotService botService;

    public SlackClientImpl(BotService botService) { this.botService = botService; }

    /**
     * Send slack notification
     * @param slackMessage slack message
     */
    @Override
    public void sendMessage(SlackMessage slackMessage) throws SlackClientException {
        botService.send(slackMessage);
    }
}
