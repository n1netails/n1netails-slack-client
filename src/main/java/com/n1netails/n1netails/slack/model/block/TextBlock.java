package com.n1netails.n1netails.slack.model.block;

import com.n1netails.n1netails.slack.model.SlackBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.PlainTextObject;
import lombok.Getter;

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
}
