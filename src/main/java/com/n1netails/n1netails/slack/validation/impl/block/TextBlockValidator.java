package com.n1netails.n1netails.slack.validation.impl.block;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.block.GifBlock;
import com.n1netails.n1netails.slack.model.block.TextBlock;
import com.n1netails.n1netails.slack.validation.SlackValidator;

/**
 * Validator for {@link TextBlock}.
 * <p>
 * Ensures that a text block contains non-empty text.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * TextBlock text = TextBlock.of("Hello Slack!");
 * new TextBlockValidator().validate(text); // passes validation
 * }</pre>
 *
 * <p>Throws {@link SlackValidationException} if the text is null or blank.</p>
 *
 * @author Artur Slimak
 */
public class TextBlockValidator implements SlackValidator<TextBlock> {
    @Override
    public void validate(TextBlock target) throws SlackValidationException {
        if (target.getText() == null || target.getText().isBlank()) {
            throw new SlackValidationException("Text cannot be empty");
        }
    }
}
