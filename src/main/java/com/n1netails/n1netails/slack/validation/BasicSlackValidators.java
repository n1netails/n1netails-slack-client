package com.n1netails.n1netails.slack.validation;

import com.n1netails.n1netails.slack.model.actions_element.ButtonElement;
import com.n1netails.n1netails.slack.model.block.ActionsBlock;
import com.n1netails.n1netails.slack.model.block.GifBlock;
import com.n1netails.n1netails.slack.model.block.ImageBlock;
import com.n1netails.n1netails.slack.model.block.TextBlock;
import com.n1netails.n1netails.slack.validation.impl.block.ActionsBlockValidator;
import com.n1netails.n1netails.slack.validation.impl.block.GifBlockValidator;
import com.n1netails.n1netails.slack.validation.impl.block.ImageBlockValidator;
import com.n1netails.n1netails.slack.validation.impl.block.TextBlockValidator;
import com.n1netails.n1netails.slack.validation.impl.element.ButtonElementValidator;

import java.util.HashMap;
import java.util.Map;

public class BasicSlackValidators {
    private final Map<Class<?>, SlackValidator<?>> validators = new HashMap<>();


    BasicSlackValidators() {
        init();
    }

    private void init() {
        validators.put(ImageBlock.class, new ImageBlockValidator());
        validators.put(GifBlock.class, new GifBlockValidator());
        validators.put(TextBlock.class, new TextBlockValidator());
        validators.put(ActionsBlock.class, new ActionsBlockValidator());
        validators.put(ButtonElement.class, new ButtonElementValidator());
    }

    public <T> void validate(T target) {
        if (target == null) return;
        SlackValidator<T> validator = (SlackValidator<T>) validators.get(target.getClass());
        if (validator != null) validator.validate(target);
    }
}
