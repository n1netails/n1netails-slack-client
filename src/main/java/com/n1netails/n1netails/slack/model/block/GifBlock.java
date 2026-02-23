package com.n1netails.n1netails.slack.model.block;

import com.n1netails.n1netails.slack.model.SlackBlock;
import com.slack.api.model.block.LayoutBlock;
import lombok.Getter;

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
}