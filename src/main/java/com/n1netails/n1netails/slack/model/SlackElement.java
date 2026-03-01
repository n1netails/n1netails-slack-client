package com.n1netails.n1netails.slack.model;

import com.n1netails.n1netails.slack.model.actions_element.ButtonElement;
import com.slack.api.model.block.element.BlockElement;

/**
 * Represents a Slack interactive element that can be converted into a Slack API {@link BlockElement}.
 * <p>
 * Examples of Slack elements include buttons, select menus, and other interactive UI components.
 * All concrete elements (e.g., {@link ButtonElement}) should implement this interface.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * SlackElement button = ButtonElement.link("Open URL", "https://example.com");
 * BlockElement blockElement = button.toBlockElement();
 * }</pre>
 *
 * <p>Extends {@link SlackNode}, allowing elements to participate in SlackNode hierarchies for compositional validation.</p>
 *
 * <p>Implementations should be immutable wherever possible.</p>
 *
 * @author Artur Slimak
 */
public interface SlackElement extends SlackNode {
    /**
     * Converts this Slack element into a Slack API {@link BlockElement}.
     *
     * @return the Slack API representation of this element
     */
    BlockElement toBlockElement();
}
