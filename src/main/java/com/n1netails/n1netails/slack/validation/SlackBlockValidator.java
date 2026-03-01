package com.n1netails.n1netails.slack.validation;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.SlackBlock;
import com.n1netails.n1netails.slack.model.SlackMessage;
import com.n1netails.n1netails.slack.model.SlackNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates the blocks of a {@link SlackMessage}.
 * <p>
 * This validator iterates over all {@link SlackBlock} instances in a message and validates
 * each block and its children recursively using {@link BasicSlackValidators}.
 * </p>
 *
 * <p>Validation rules:</p>
 * <ul>
 *     <li>If {@link SlackMessage#rawBlocks} is non-empty, validation is skipped.</li>
 *     <li>If {@link SlackMessage#blocks} is empty or null, validation is skipped.</li>
 *     <li>All blocks and child elements are validated using their respective {@link SlackValidator} implementations.</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * SlackMessage message = SlackMessage.builder()
 *     .channel("general")
 *     .addBlock(TextBlock.of("Hello!"))
 *     .build();
 *
 * SlackBlockValidator validator = new SlackBlockValidator();
 * validator.validateMessageBlocks(message); // throws SlackValidationException if invalid
 * }</pre>
 *
 * <p>Throws {@link SlackValidationException} if any block or child node fails validation,
 * with detailed error messages including the block path in the message.</p>
 *
 * @author Artur Slimak
 */
public class SlackBlockValidator {

    private final BasicSlackValidators basicSlackValidators;

    public SlackBlockValidator() {
        this.basicSlackValidators = new BasicSlackValidators();
    }

    /**
     * Validates all {@link SlackBlock} instances in the given Slack message.
     * <p>
     * Validation is recursive: all child nodes of blocks are also validated.
     * </p>
     *
     * @param message the Slack message to validate
     * @throws SlackValidationException if any block or child node fails validation
     */
    public void validateMessageBlocks(SlackMessage message) {
        if (message.getRawBlocks() != null && !message.getRawBlocks().isEmpty()) {
            return;
        }

        if (message.getBlocks() == null || message.getBlocks().isEmpty()) {
            return;
        }

        List<String> errors = new ArrayList<>();

        for (int i = 0; i < message.getBlocks().size(); i++) {
            SlackBlock block = message.getBlocks().get(i);
            validateNode(block, "Block[" + i + "]", errors);
        }

        if (!errors.isEmpty()) {
            throw new SlackValidationException(
                    "Slack message validation failed:\n - " + String.join("\n - ", errors)
            );
        }
    }

    /**
     * Recursively validates a {@link SlackNode} and its children, collecting any validation errors.
     *
     * @param node the node to validate
     * @param path the path of the node in the message (for error reporting)
     * @param errors the list to collect validation error messages
     */
    private void validateNode(SlackNode node, String path, List<String> errors) {
        try {
            basicSlackValidators.validate(node);
        } catch (SlackValidationException e) {
            errors.add(path + " (" + node.getClass().getSimpleName() + "): " + e.getMessage());
        }

        List<SlackNode> children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            validateNode(children.get(i), path + ".elements[" + i + "]", errors);
        }
    }
}