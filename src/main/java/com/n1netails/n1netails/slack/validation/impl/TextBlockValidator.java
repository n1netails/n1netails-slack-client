package com.n1netails.n1netails.slack.validation.impl;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.blocks.TextBlock;
import com.n1netails.n1netails.slack.validation.SlackValidator;

public class TextBlockValidator implements SlackValidator<TextBlock> {
    @Override
    public void validate(TextBlock target) throws SlackValidationException {
        if (target.getText() == null || target.getText().isBlank()) {
            throw new SlackValidationException("Text cannot be empty");
        }

    }
}
