package com.n1netails.n1netails.slack.validation;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.SlackBlock;
import com.n1netails.n1netails.slack.model.SlackMessage;
import com.n1netails.n1netails.slack.model.SlackNode;

import java.util.ArrayList;
import java.util.List;

public class SlackBlockValidator {

    private final BasicSlackValidators basicSlackValidators;

    public SlackBlockValidator() {
        this.basicSlackValidators = new BasicSlackValidators();
    }

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