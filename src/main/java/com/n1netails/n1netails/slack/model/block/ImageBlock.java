package com.n1netails.n1netails.slack.model.block;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.SlackBlock;
import com.n1netails.n1netails.slack.model.SlackNode;
import com.slack.api.model.block.LayoutBlock;
import lombok.Getter;

import java.util.List;


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

    @Override
    public List<SlackNode> getChildren() {
        return List.of();
    }

    public static class Builder {
        private String imageUrl;
        private String altText;

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder altText(String altText) {
            this.altText = altText;
            return this;
        }

        public ImageBlock build() {
            return new ImageBlock(imageUrl, altText);
        }
    }
}
