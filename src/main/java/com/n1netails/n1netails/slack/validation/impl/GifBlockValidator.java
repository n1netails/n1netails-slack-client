package com.n1netails.n1netails.slack.validation.impl;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.blocks.GifBlock;
import com.n1netails.n1netails.slack.validation.SlackValidator;

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
