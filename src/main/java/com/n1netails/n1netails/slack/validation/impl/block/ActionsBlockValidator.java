package com.n1netails.n1netails.slack.validation.impl.block;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.SlackElement;
import com.n1netails.n1netails.slack.model.block.ActionsBlock;
import com.n1netails.n1netails.slack.validation.BasicSlackValidators;
import com.n1netails.n1netails.slack.validation.SlackValidator;

public class ActionsBlockValidator implements SlackValidator<ActionsBlock> {
    @Override
    public void validate(ActionsBlock target) throws SlackValidationException {
        if (target.getElements() == null || target.getElements().isEmpty()) {
            throw new SlackValidationException("ActionsBlock must contain at least one element");
        }

    }
}
