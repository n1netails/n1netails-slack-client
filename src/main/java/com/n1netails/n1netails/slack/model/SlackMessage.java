package com.n1netails.n1netails.slack.model;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.slack.api.model.block.LayoutBlock;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Slack message that can be sent to a channel.
 * <p>
 * A Slack message can contain plain text, structured {@link SlackBlock}s, or raw Slack API blocks
 * (<a href="https://docs.slack.dev/block-kit">Block Kit Documentation</a>).
 * Messages are immutable once built and should be constructed via the {@link Builder}.
 * </p>
 *
 * Example usage:
 * <pre>{@code
 * SlackMessage message = SlackMessage.builder()
 *     .channel("general")
 *     .text("Hello, Slack!")
 *     .build();
 * }</pre>
 *
 * <p>Cannot mix {@link SlackBlock} and raw Block Kit blocks in the same message.</p>
 *
 * <p>All instances are immutable and thread-safe after creation.</p>
 *
 * @author Shahid Foy and Artur Slimak
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


    /**
     * Returns a new {@link Builder} for constructing a {@link SlackMessage}.
     *
     * @return a new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for creating {@link SlackMessage} instances.
     * <p>
     * Allows setting the target channel, text, and adding blocks (either {@link SlackBlock} or raw {@link LayoutBlock}).
     * Enforces validation rules to prevent invalid combinations.
     * </p>
     */
    public static class Builder {
        private String channel;
        private String text;
        private final List<SlackBlock> blocks = new ArrayList<>();
        private final List<LayoutBlock> rawBlocks = new ArrayList<>();

        /**
         * Sets the target Slack channel for this message.
         *
         * @param channel the Slack channel name or ID
         * @return this builder
         */
        public Builder channel(String channel) {
            this.channel = channel;
            return this;
        }


        /**
         * Sets the plain text content of this Slack message.
         * <p>
         * If one or more blocks are present in the message, this text serves as a fallback
         * for notifications or clients that cannot render blocks.
         * </p>
         *
         * @param text the message text
         * @return this builder
         */
        public Builder text(String text) {
            this.text = text;
            return this;
        }


        /**
         * Adds a structured {@link SlackBlock} to this message.
         *
         * @param block the Slack block to add
         * @return this builder
         * @throws SlackValidationException if rawBlocks are already present
         */
        public Builder addBlock(SlackBlock block) throws SlackValidationException {
            if (!rawBlocks.isEmpty()) {
                throw new SlackValidationException(
                        "Cannot add SlackBlock when rawBlocks are already present"
                );
            }
            this.blocks.add(block);
            return this;
        }

        /**
         * Adds a raw {@link LayoutBlock} to this message.
         *
         * @param block the raw Slack API layout block (<a href="https://docs.slack.dev/block-kit">Block Kit Documentation</a>)
         * @return this builder
         * @throws SlackValidationException if SlackBlocks are already present
         */
        public Builder addRawBlock(LayoutBlock block) throws SlackValidationException {
            if (!blocks.isEmpty()) {
                throw new SlackValidationException(
                        "Cannot add rawBlock when SlackBlocks are already present"
                );
            }
            this.rawBlocks.add(block);
            return this;
        }

        /**
         * Builds an immutable {@link SlackMessage} instance after performing validation.
         *
         * @return a new {@link SlackMessage}
         * @throws SlackValidationException if channel is missing, content is missing, or block types are mixed
         */
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

            return new SlackMessage(this);
        }

    }
}
