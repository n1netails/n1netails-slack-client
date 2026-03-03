package com.n1netails.n1netails.slack.validation;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
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

/**
 * Registry of basic Slack validators for common blocks and elements.
 * <p>
 * Provides automatic validation of:
 * </p>
 * <ul>
 *     <li>{@link ImageBlock}</li>
 *     <li>{@link GifBlock}</li>
 *     <li>{@link TextBlock}</li>
 *     <li>{@link ActionsBlock}</li>
 *     <li>...</li>
 * </ul>
 * <p>
 * Can be extended with custom validators for additional Slack nodes.
 * </p>
 *
 * <p>Validation is skipped if the target is {@code null} or no validator exists for its type.</p>
 *
 * @author Artur Slimak
 */
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

    /**
     * Validates the given target using the registered validator for its type.
     * <p>
     * If no validator exists for the target type, or the target is {@code null}, validation is skipped.
     * </p>
     *
     * @param <T>    the type of object to validate
     * @param target the object to validate
     * @throws SlackValidationException if validation fails
     */
    @SuppressWarnings("unchecked")
    public <T> void validate(T target) {
        if (target == null) return;
        SlackValidator<T> validator = (SlackValidator<T>) validators.get(target.getClass());
        if (validator != null) validator.validate(target);
    }
}
