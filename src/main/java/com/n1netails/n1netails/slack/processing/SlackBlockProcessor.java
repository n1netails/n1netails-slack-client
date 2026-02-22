package com.n1netails.n1netails.slack.processing;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.SlackBlock;
import com.n1netails.n1netails.slack.model.SlackMessage;
import com.n1netails.n1netails.slack.validation.BasicSlackValidators;
import com.slack.api.model.block.LayoutBlock;

import java.util.ArrayList;
import java.util.List;

public class SlackBlockProcessor {

    private final BasicSlackValidators basicSlackValidators;

    public SlackBlockProcessor() {
        this.basicSlackValidators = new BasicSlackValidators();
    }

    public List<LayoutBlock> process(SlackMessage message) {
        if (message.getRawBlocks() != null && !message.getRawBlocks().isEmpty()) {
            return message.getRawBlocks();
        }

        if (message.getBlocks() == null || message.getBlocks().isEmpty()) {
            return null;
        }

        List<LayoutBlock> result = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        int index = 0;

        for (SlackBlock block : message.getBlocks()) {
            try {
                basicSlackValidators.validate(block);
                result.add(block.toLayoutBlock());
            } catch (SlackValidationException e) {
                errors.add(formatError(index, block, e));
            }
            index++;
        }

        if (!errors.isEmpty()) {
            throw new SlackValidationException(buildErrorMessage(errors));
        }

        return result;
    }

    private String formatError(int index, SlackBlock block, Exception e) {
        return String.format(
                "Block[%d] (%s): %s",
                index,
                block.getClass().getSimpleName(),
                e.getMessage()
        );
    }

    private String buildErrorMessage(List<String> errors) {
        return "Slack message validation failed:\n - " + String.join("\n - ", errors);
    }
}