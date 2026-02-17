package com.n1netails.n1netails.slack.validation;

import com.n1netails.n1netails.slack.exception.SlackValidationException;

public interface SlackValidator<T> {
    void validate(T target) throws SlackValidationException;
}
