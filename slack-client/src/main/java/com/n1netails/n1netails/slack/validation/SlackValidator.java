package com.n1netails.n1netails.slack.validation;

import com.n1netails.n1netails.slack.exception.SlackValidationException;

/**
 * Generic interface for validating Slack nodes or elements.
 * <p>
 * Implementations provide type-specific validation logic for Slack blocks or elements.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * SlackValidator<TextBlock> validator = new TextBlockValidator();
 * validator.validate(TextBlock.of("Hello"));
 * }</pre>
 *
 * @param <T> the type of object to validate
 * @author Artur Slimak
 */
public interface SlackValidator<T> {
    /**
     * Validates the given target object.
     *
     * @param target the object to validate
     * @throws SlackValidationException if validation fails
     */
    void validate(T target) throws SlackValidationException;
}
