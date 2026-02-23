package com.n1netails.n1netails.slack.validation.impl.block;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.block.GifBlock;
import com.n1netails.n1netails.slack.validation.SlackValidator;

/**
 * Validator for {@link GifBlock}.
 * <p>
 * Ensures that a GIF block has both a valid URL and alternative text for accessibility.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * GifBlock gif = GifBlock.of("https://media.giphy.com/media/3oEjI6SIIHBdRxXI40/giphy.gif", "Dancing cat");
 * new GifBlockValidator().validate(gif); // passes validation
 * }</pre>
 *
 * <p>Throws {@link SlackValidationException} if the GIF URL or alt text is missing or blank.</p>
 *
 * @author Artur Slimak
 */
public class GifBlockValidator implements SlackValidator<GifBlock> {
    @Override
    public void validate(GifBlock target) throws SlackValidationException {
        if (target.getGifUrl() == null || target.getGifUrl().isBlank()) {
            throw new SlackValidationException("Gif URL cannot be empty");
        }

        if (target.getAltText() == null || target.getAltText().isBlank()) {
            throw new SlackValidationException("altText is required for accessibility");
        }
    }
}
