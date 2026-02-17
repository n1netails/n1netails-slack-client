package com.n1netails.n1netails.slack.model.blocks;

import com.n1netails.n1netails.slack.model.SlackBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.PlainTextObject;

public class TextBlock implements SlackBlock {
    private final String text;

    public TextBlock(String text) {
        this.text = text;
    }

    @Override
    public LayoutBlock toLayoutBlock() {
        return com.slack.api.model.block.SectionBlock.builder()
                .text(new PlainTextObject(text, false))
                .build();
    }
}
