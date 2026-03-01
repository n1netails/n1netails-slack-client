package com.n1netails.n1netails.slack.model.block;

import com.n1netails.n1netails.slack.model.SlackBlock;
import com.n1netails.n1netails.slack.model.SlackNode;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.Getter;

import java.util.List;

/**
 * Represents a Slack Text Block (Section Block) containing plain text.
 * <p>
 * Use {@link #of(String)} or the {@link Builder} to create instances.
 * This block displays text in a Slack message and does not support child nodes.
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * TextBlock block = TextBlock.of("Hello, Slack!");
 * }</pre>
 *
 * <p>Converts to a Slack API {@link LayoutBlock} via {@link #toLayoutBlock()}.</p>
 *
 * <p>All instances are immutable once created.</p>
 *
 * <p>{@link #getChildren()} always returns an empty list.</p>
 *
 * @author Artur Slimak
 */
@Getter
public class TextBlock implements SlackBlock {
    private final String text;

    private TextBlock(String text) {
        this.text = text;
    }

    public TextBlock of(String text) {
        return new TextBlock(text);
    }

    @Override
    public LayoutBlock toLayoutBlock() {
        return com.slack.api.model.block.SectionBlock.builder()
                .text(new PlainTextObject(text, false))
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public List<SlackNode> getChildren() {
        return List.of();
    }

    public static class Builder {
        private String text;

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public TextBlock build() {
            return new TextBlock(text);
        }
    }

}
