package com.n1netails.n1netails.slack.exception;

public class SlackApiExceptionWrapper extends SlackClientException {
    private final String slackError;

    public SlackApiExceptionWrapper(String slackError) {
        super("Slack API error: " + slackError);
        this.slackError = slackError;
    }

    public String getSlackError() {
        return slackError;
    }
}
