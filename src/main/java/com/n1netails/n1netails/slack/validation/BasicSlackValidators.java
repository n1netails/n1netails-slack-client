package com.n1netails.n1netails.slack.validation;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.blocks.GifBlock;
import com.n1netails.n1netails.slack.model.blocks.ImageBlock;
import com.n1netails.n1netails.slack.model.blocks.TextBlock;
import com.n1netails.n1netails.slack.validation.impl.GifBlockValidator;
import com.n1netails.n1netails.slack.validation.impl.ImageBlockValidator;
import com.n1netails.n1netails.slack.validation.impl.TextBlockValidator;

import java.util.HashMap;
import java.util.Map;

public class BasicSlackValidators {
    private final Map<Class<?>, SlackValidator<?>> validators = new HashMap<>();

    public BasicSlackValidators() {
        validators.put(ImageBlock.class, new ImageBlockValidator());
        validators.put(GifBlock.class, new GifBlockValidator());
        validators.put(TextBlock.class, new TextBlockValidator());
    }
    public <T> void validate(T target) {
        if (target == null) return;
        SlackValidator<T> validator = (SlackValidator<T>) validators.get(target.getClass());
        if (validator != null) validator.validate(target);
    }
}
