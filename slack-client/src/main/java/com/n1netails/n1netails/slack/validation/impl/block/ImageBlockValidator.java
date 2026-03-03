package com.n1netails.n1netails.slack.validation.impl.block;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.block.ImageBlock;
import com.n1netails.n1netails.slack.validation.SlackValidator;

/**
 * Validator for {@link ImageBlock}.
 * <p>
 * Ensures that an image block has both a valid URL and alternative text for accessibility.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * ImageBlock image = ImageBlock.of("https://example.com/image.png", "Descriptive alt text");
 * new ImageBlockValidator().validate(image); // passes validation
 * }</pre>
 *
 * <p>Throws {@link SlackValidationException} if the image URL or alt text is missing or blank.</p>
 *
 * @author Artur Slimak
 */
public class ImageBlockValidator implements SlackValidator<ImageBlock> {
    @Override
    public void validate(ImageBlock target) throws SlackValidationException {
        if (target.getImageUrl() == null || target.getImageUrl().isBlank()) {
            throw new SlackValidationException("Image URL cannot be empty");
        }

        if (target.getAltText() == null || target.getAltText().isBlank()) {
            throw new SlackValidationException("altText is required for accessibility");
        }
    }
}
