package com.n1netails.n1netails.slack.validation.impl.element;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.actions_element.ButtonElement;
import com.n1netails.n1netails.slack.validation.SlackValidator;

/**
 * Validator for {@link ButtonElement}.
 * <p>
 * Ensures that a button element has valid text and either an {@code actionId} or a URL.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * ButtonElement button = ButtonElement.link("Visit Site", "https://example.com");
 * new ButtonElementValidator().validate(button); // passes validation
 *
 * ButtonElement actionButton = ButtonElement.action("Click me", "action_123");
 * new ButtonElementValidator().validate(actionButton); // passes validation
 * }</pre>
 *
 * <p>Throws {@link SlackValidationException} if:</p>
 * <ul>
 *     <li>Text is null or blank</li>
 *     <li>Both {@code actionId} and {@code url} are missing or blank</li>
 * </ul>
 *
 * @author Artur Slimak
 */
public class ButtonElementValidator implements SlackValidator<ButtonElement> {
    @Override
    public void validate(ButtonElement target) throws SlackValidationException {
        if (target.getText() == null || target.getText().isBlank()) {
            throw new SlackValidationException("Button text is required");
        }
        if ((target.getActionId() == null || target.getActionId().isBlank()) &&
                (target.getUrl() == null || target.getUrl().isBlank())) {
            throw new SlackValidationException(
                    "Button must have either actionId or url"
            );
        }
    }
}
