package com.n1netails.n1netails.slack.model;

import com.slack.api.model.block.LayoutBlock;

import java.util.List;

public interface SlackBlock extends SlackNode {
    LayoutBlock toLayoutBlock();
}
