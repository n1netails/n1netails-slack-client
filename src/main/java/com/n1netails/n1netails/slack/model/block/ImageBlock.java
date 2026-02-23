package com.n1netails.n1netails.slack.model.block;

import com.n1netails.n1netails.slack.model.SlackBlock;
import com.slack.api.model.block.LayoutBlock;
import lombok.Getter;


@Getter
public class ImageBlock implements SlackBlock {
    private final String imageUrl;
    private final String altText;

    private ImageBlock(String imageUrl, String altText) {
        this.imageUrl = imageUrl;
        this.altText = altText;
    }

    public ImageBlock of(String imageUrl, String altText) {
        return new ImageBlock(imageUrl, altText);
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
