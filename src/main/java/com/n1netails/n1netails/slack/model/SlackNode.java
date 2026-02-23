package com.n1netails.n1netails.slack.model;

import java.util.List;

public interface SlackNode {
    List<SlackNode> getChildren();
}
