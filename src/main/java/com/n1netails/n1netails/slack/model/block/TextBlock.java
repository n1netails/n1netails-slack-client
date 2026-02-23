package com.n1netails.n1netails.slack.model.block;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.SlackBlock;
import com.n1netails.n1netails.slack.model.SlackNode;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.Getter;

import java.util.List;

@Getter
public class TextBlock implements SlackBlock {
    private final String text;

    private TextBlock(String text) {
        this.text = text;
    }

    public TextBlock of(String text) {
        return new TextBlock(text);
    }

    @Override
    public LayoutBlock toLayoutBlock() {
        return com.slack.api.model.block.SectionBlock.builder()
                .text(new PlainTextObject(text, false))
                .build();
    }

    @Override
    public List<SlackNode> getChildren() {
        return List.of();
    }

    public static class Builder {
        private String text;

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public TextBlock build() {
            return new TextBlock(text);
        }
    }

}
