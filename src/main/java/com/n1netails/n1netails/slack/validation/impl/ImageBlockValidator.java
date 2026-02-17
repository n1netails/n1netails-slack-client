package com.n1netails.n1netails.slack.validation.impl;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.blocks.ImageBlock;
import com.n1netails.n1netails.slack.validation.SlackValidator;

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
