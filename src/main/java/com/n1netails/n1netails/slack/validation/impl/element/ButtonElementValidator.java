package com.n1netails.n1netails.slack.validation.impl.element;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.actions_element.Button;
import com.n1netails.n1netails.slack.validation.SlackValidator;

public class ButtonElementValidator implements SlackValidator<Button> {
    @Override
    public void validate(Button target) throws SlackValidationException {
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
