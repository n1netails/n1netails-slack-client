package com.n1netails.n1netails.slack.api.builder;

import com.n1netails.n1netails.slack.model.SlackBlock;
import com.n1netails.n1netails.slack.model.SlackMessage;
import com.slack.api.model.block.LayoutBlock;

import java.util.List;

/**
 * Builder class responsible for converting a {@link SlackMessage} into a list of Slack {@link LayoutBlock}s.
 * <p>
 * The builder first checks if the message contains raw {@link LayoutBlock}s. If so, it returns them directly.
 * Otherwise, it converts the high-level {@link SlackBlock} objects into {@link LayoutBlock}s using their {@code toLayoutBlock()} method.
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * SlackMessage message = new SlackMessage("general", "Hello!");
 * List<LayoutBlock> blocks = new SlackBlockBuilder().build(message);
 * }</pre>
 *
 * @author Artur Slimak
 */
public class SlackBlockBuilder {

    /**
     * Builds a list of {@link LayoutBlock}s from a {@link SlackMessage}.
     * <p>
     * Priority order:
     * </p>
     * <ol>
     *     <li>If {@code rawBlocks} are present in the message, return them as-is.</li>
     *     <li>If {@code blocks} are present, convert each {@link SlackBlock} to {@link LayoutBlock}.</li>
     *     <li>If neither is present, returns {@code null}.</li>
     * </ol>
     *
     * @param message the Slack message to convert; must not be null
     * @return list of {@link LayoutBlock}s or {@code null} if none are available
     */
    public List<LayoutBlock> build(SlackMessage message) {

        if (message.getRawBlocks() != null && !message.getRawBlocks().isEmpty()) {
            return message.getRawBlocks();
        }

        if (message.getBlocks() == null || message.getBlocks().isEmpty()) {
            return null;
        }

        return message.getBlocks()
                .stream()
                .map(SlackBlock::toLayoutBlock)
                .toList();
    }
}
