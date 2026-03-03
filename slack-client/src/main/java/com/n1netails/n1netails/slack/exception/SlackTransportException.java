package com.n1netails.n1netails.slack.exception;

public class SlackTransportException extends SlackClientException {
    public SlackTransportException(String message) {
        super(message);
    }

    public SlackTransportException(String message, Throwable cause) {
        super(message, cause);
    }
}
