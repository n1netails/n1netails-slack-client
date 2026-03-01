package com.n1netails.n1netails.slack.validation.impl.block;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.SlackElement;
import com.n1netails.n1netails.slack.model.block.ActionsBlock;
import com.n1netails.n1netails.slack.validation.BasicSlackValidators;
import com.n1netails.n1netails.slack.validation.SlackValidator;

/**
 * Validator for {@link ActionsBlock}.
 * <p>
 * Ensures that an {@link ActionsBlock} contains at least one child element.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * ActionsBlock block = ActionsBlock.builder()
 *     .addElement(ButtonElement.link("Click me", "https://example.com"))
 *     .build();
 * new ActionsBlockValidator().validate(block); // passes validation
 * }</pre>
 *
 * <p>Throws {@link SlackValidationException} if the block is empty.</p>
 *
 * @author Artur Slimak
 */
public class ActionsBlockValidator implements SlackValidator<ActionsBlock> {
    @Override
    public void validate(ActionsBlock target) throws SlackValidationException {
        if (target.getElements() == null || target.getElements().isEmpty()) {
            throw new SlackValidationException("ActionsBlock must contain at least one element");
        }

    }
}
