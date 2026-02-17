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

public class SlackValidators {
    private static final Map<Class<?>, SlackValidator<?>> VALIDATORS = new HashMap<>();

    static {
        VALIDATORS.put(ImageBlock.class, new ImageBlockValidator());
        VALIDATORS.put(GifBlock.class, new GifBlockValidator());
        VALIDATORS.put(TextBlock.class, new TextBlockValidator());
    }

    @SuppressWarnings("unchecked")
    public static <T> void validate(T target) throws SlackValidationException {
        if (target == null) return;

        SlackValidator<T> validator =
                (SlackValidator<T>) VALIDATORS.get(target.getClass());

        if (validator != null) {
            validator.validate(target);
        }
    }
}
