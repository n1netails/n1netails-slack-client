package com.n1netails.n1netails.slack.model;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.fallback.SlackFallbackHandler;
import com.n1netails.n1netails.slack.validation.SlackValidators;
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

        public Builder addBlock(SlackBlock block) throws SlackValidationException {
            if (!rawBlocks.isEmpty()) {
                throw new SlackValidationException(
                        "Cannot add SlackBlock when rawBlocks are already present"
                );
            }
            this.blocks.add(block);
            return this;
        }

        public Builder addRawBlock(LayoutBlock block) throws SlackValidationException {
            if (!blocks.isEmpty()) {
                throw new SlackValidationException(
                        "Cannot add rawBlock when SlackBlocks are already present"
                );
            }
            this.rawBlocks.add(block);
            return this;
        }

        public SlackMessage build() throws SlackValidationException {
            if (channel == null || channel.isBlank()) {
                throw new SlackValidationException("channel is required");
            }

            boolean hasContent = (text != null && !text.isBlank())
                    || !blocks.isEmpty()
                    || !rawBlocks.isEmpty();

            if (!hasContent) {
                throw new SlackValidationException(
                        "Either text, blocks, or rawBlocks must be provided"
                );
            }

            if (!blocks.isEmpty() && !rawBlocks.isEmpty()) {
                throw new SlackValidationException(
                        "Cannot mix SlackBlock and rawBlocks in the same message"
                );
            }
            List<SlackBlock> processedBlocks = new ArrayList<>();

            for (SlackBlock block : blocks) {
                try {
                    SlackValidators.validate(block);
                    processedBlocks.add(block);
                } catch (Exception e) {
                    processedBlocks.add(SlackFallbackHandler.handle(block, e));
                }
            }

            this.blocks.clear();
            this.blocks.addAll(processedBlocks);

            return new SlackMessage(this);
        }

    }
}
