package com.n1netails.n1netails.slack.api.builder;

import com.n1netails.n1netails.slack.model.SlackBlock;
import com.n1netails.n1netails.slack.model.SlackMessage;
import com.slack.api.model.block.LayoutBlock;

import java.util.List;

public class SlackBlockBuilder {

    public List<LayoutBlock> build(SlackMessage message) {

        if (message.getRawBlocks() != null && !message.getRawBlocks().isEmpty()) {
            return message.getRawBlocks();
        }

        if (message.getBlocks() == null || message.getBlocks().isEmpty()) {
            return null;
        }

        return message.getBlocks()
                .stream()
                .map(SlackBlock::toLayoutBlock)
                .toList();
    }
}
