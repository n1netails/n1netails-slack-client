package com.n1netails.n1netails.slack.model;

import com.n1netails.n1netails.slack.model.block.ActionsBlock;
import com.n1netails.n1netails.slack.model.block.GifBlock;
import com.n1netails.n1netails.slack.model.block.ImageBlock;
import com.n1netails.n1netails.slack.model.block.TextBlock;
import com.slack.api.model.block.LayoutBlock;

import java.util.List;

/**
 * Represents a Slack block element that can be converted into a Slack API {@link LayoutBlock}.
 * <p>
 * All concrete Slack blocks (e.g., {@link TextBlock}, {@link ImageBlock}, {@link GifBlock}, {@link ActionsBlock})
 * should implement this interface.
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * SlackBlock block = TextBlock.of("Hello Slack!");
 * LayoutBlock layoutBlock = block.toLayoutBlock();
 * }</pre>
 *
 * <p>Extends {@link SlackNode}, allowing blocks to participate in SlackNode hierarchies for compositional validation.</p>
 *
 * <p>Implementations should be immutable wherever possible.</p>
 *
 * @author Artur Slimak
 */
public interface SlackBlock extends SlackNode {
    /**
     * Converts this Slack block into a Slack API {@link LayoutBlock}.
     *
     * @return the Slack API representation of this block
     */
    LayoutBlock toLayoutBlock();
}
