package com.n1netails.n1netails.slack.model;

import com.slack.api.model.block.element.BlockElement;

public interface SlackElement extends SlackNode {
    BlockElement toBlockElement();
}
