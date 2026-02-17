package com.n1netails.n1netails.slack.model;

import com.slack.api.model.block.LayoutBlock;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Slack Message
 *
 * @author shahid foy
 */
@Getter
public class SlackMessage {

    private String channel;
    private String text;
    private List<SlackBlock> blocks;
    private List<LayoutBlock> rawBlocks;

    private SlackMessage(Builder builder) {
        this.channel = builder.channel;
        this.text = builder.text;
        this.blocks = List.copyOf(builder.blocks);
        this.rawBlocks = List.copyOf(builder.rawBlocks);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String channel;
        private String text;
        private final List<SlackBlock> blocks = new ArrayList<>();
        private final List<LayoutBlock> rawBlocks = new ArrayList<>();

        public Builder channel(String channel) {
            this.channel = channel;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder addBlock(SlackBlock block) {
            this.blocks.add(block);
            return this;
        }

        public Builder addRawBlock(LayoutBlock block) {
            this.rawBlocks.add(block);
            return this;
        }

        public SlackMessage build() {
            if (channel == null || channel.isBlank()) {
                throw new IllegalStateException("channel is required");
            }

            boolean hasTextOrBlocks = (text != null && !text.isBlank())
                    || (!blocks.isEmpty())
                    || (!rawBlocks.isEmpty());

            if (!hasTextOrBlocks) {
                throw new IllegalStateException("Either text, blocks, or rawBlocks must be provided");
            }

            if (!blocks.isEmpty() && !rawBlocks.isEmpty()) {
                throw new IllegalStateException("Cannot mix SlackBlock and rawBlocks in the same message");
            }

            return new SlackMessage(this);
        }

    }
}
