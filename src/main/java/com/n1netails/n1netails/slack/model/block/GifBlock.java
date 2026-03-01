package com.n1netails.n1netails.slack.model.block;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.SlackBlock;
import com.n1netails.n1netails.slack.model.SlackNode;
import com.slack.api.model.block.LayoutBlock;
import lombok.Getter;

import java.util.List;

/**
 * Represents a Slack Image Block containing a GIF.
 * <p>
 * This block displays a GIF in a Slack message. Use {@link #of(String, String)} or the {@link Builder}
 * to create instances. The GIF URL must be publicly accessible, and altText provides a description
 * for accessibility and fallback display.
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * GifBlock gif = GifBlock.of(
 *     "https://media.giphy.com/media/3oEjI6SIIHBdRxXI40/giphy.gif",
 *     "Funny dancing cat"
 * );
 * }</pre>
 *
 * <p>Converts to a Slack API {@link LayoutBlock} via {@link #toLayoutBlock()}.</p>
 *
 * <p>This block does not contain child nodes, so {@link #getChildren()} returns an empty list.</p>
 *
 * <p>All instances are immutable once created.</p>
 *
 * @author Artur Slimak
 */
@Getter
public class GifBlock implements SlackBlock {
    private final String gifUrl;
    private final String altText;

    private GifBlock(String gifUrl, String altText) {
        this.gifUrl = gifUrl;
        this.altText = altText;
    }

    public static GifBlock of(String gifUrl, String altText) {
        return new GifBlock(gifUrl, altText);
    }


    @Override
    public LayoutBlock toLayoutBlock() {
        return
                com.slack.api.model.block.ImageBlock.builder()
                        .altText(altText)
                        .imageUrl(gifUrl)
                        .build()
                ;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public List<SlackNode> getChildren() {
        return List.of();
    }

    public static class Builder {
        private String gifUrl;
        private String altText;

        public Builder gifUrl(String gifUrl) {
            this.gifUrl = gifUrl;
            return this;
        }

        public Builder altText(String altText) {
            this.altText = altText;
            return this;
        }

        public GifBlock build() {
            return new GifBlock(gifUrl, altText);
        }
    }
}