package com.n1netails.n1netails.slack.exception;

public class SlackApiExceptionWrapper extends SlackClientException {
    public SlackApiExceptionWrapper(String slackError) {
        super("Slack API error: " + slackError);

    }
}
