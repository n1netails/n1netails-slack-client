package com.n1netails.n1netails.slack.model.blocks;

import com.n1netails.n1netails.slack.model.SlackBlock;
import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.BlockCompositions;
import lombok.Getter;


@Getter
public class ImageBlock implements SlackBlock {
    private final String imageUrl;
    private final String altText;

    public ImageBlock(String imageUrl, String altText) {
        this.imageUrl = imageUrl;
        this.altText = altText;
    }


    @Override
    public LayoutBlock toLayoutBlock() {
        return
                com.slack.api.model.block.ImageBlock.builder()
                        .imageUrl(imageUrl)
                        .altText(altText)
                        .build()
                ;
    }
}
