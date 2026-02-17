package com.n1netails.n1netails.slack.fallback;

import com.n1netails.n1netails.slack.model.SlackBlock;
import com.n1netails.n1netails.slack.model.blocks.FallbackBlock;
import com.n1netails.n1netails.slack.model.blocks.GifBlock;
import com.n1netails.n1netails.slack.model.blocks.ImageBlock;
import com.n1netails.n1netails.slack.model.blocks.TextBlock;

public class SlackFallbackHandler {
    public static SlackBlock handle(SlackBlock block, Exception e) {

        if (block instanceof ImageBlock img) {
            return new FallbackBlock("Image could not be displayed: " + img.getAltText());
        }

        if (block instanceof GifBlock gif) {
            return new FallbackBlock("GIF could not be displayed: " + gif.getAltText());
        }

        if (block instanceof TextBlock txt) {
            return new FallbackBlock("Text could not be displayed");
        }

        return new FallbackBlock("Unsupported content");
    }
}
