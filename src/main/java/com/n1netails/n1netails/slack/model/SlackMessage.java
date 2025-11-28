package com.n1netails.n1netails.slack.model;

import com.slack.api.model.block.LayoutBlock;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Slack Message
 * @author shahid foy
 */
@Getter
@Setter
public class SlackMessage {

    private String channel;
    private String text;
    private List<LayoutBlock> blocks;

    /**
     * Slack Message Constructor
     */
    public SlackMessage() {}
}
