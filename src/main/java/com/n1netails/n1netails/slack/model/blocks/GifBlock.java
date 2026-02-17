package com.n1netails.n1netails.slack.model.blocks;

import com.n1netails.n1netails.slack.model.SlackBlock;
import com.slack.api.model.block.LayoutBlock;
import lombok.Getter;

@Getter
public class GifBlock implements SlackBlock {
    private final String gifUrl;
    private final String altText;

    public GifBlock(String gifUrl, String altText) {
        this.gifUrl = gifUrl;
        this.altText = altText;
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