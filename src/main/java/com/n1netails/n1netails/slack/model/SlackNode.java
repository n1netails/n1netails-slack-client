package com.n1netails.n1netails.slack.model;

import com.n1netails.n1netails.slack.model.actions_element.ButtonElement;
import com.n1netails.n1netails.slack.model.block.GifBlock;
import com.n1netails.n1netails.slack.model.block.ImageBlock;
import com.n1netails.n1netails.slack.model.block.TextBlock;

import java.util.List;

/**
 * Represents a node in a Slack message composition hierarchy.
 * <p>
 * All Slack blocks and elements implement this interface to participate
 * in a tree-like structure for compositional validation and traversal.
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * SlackNode block = TextBlock.of("Hello Slack!");
 * List<SlackNode> children = block.getChildren(); // returns an empty list
 * }</pre>
 *
 * <p>Implementations should be immutable wherever possible.</p>
 *
 * @author Artur SLimak
 */
public interface SlackNode {
    /**
     * Returns the children of this node in the Slack composition tree.
     * <p>
     * For leaf nodes (e.g., {@link TextBlock}, {@link GifBlock}, {@link ImageBlock}, {@link ButtonElement}),
     * this method returns an empty list.
     * </p>
     *
     * @return a list of child {@link SlackNode} instances
     */
    List<SlackNode> getChildren();
}
