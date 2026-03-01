package com.n1netails.n1netails.slack.exception;

/**
 * Slack Client Exception
 * @author shahid foy
 */
public class SlackClientException extends RuntimeException {

    /**
     * Slack Client Exception Constructor
     * @param message exception message
     */
    public SlackClientException(String message) {
        super(message);
    }

    /**
     * Slack Client Exception Constructor
     * @param message exception message
     * @param cause cause
     */
    public SlackClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
