package com.n1netails.n1netails.slack.model.block;

import com.n1netails.n1netails.slack.model.SlackBlock;
import com.n1netails.n1netails.slack.model.SlackElement;
import com.n1netails.n1netails.slack.model.SlackNode;
import com.n1netails.n1netails.slack.model.actions_element.ButtonElement;
import com.slack.api.model.block.LayoutBlock;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Slack Actions Block.
 * <p>
 * An Actions Block contains a set of interactive elements, such as {@link ButtonElement}.
 * Use {@link #of(List)} or {@link #builder()} to create instances.
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * ActionsBlock block = ActionsBlock.builder()
 *     .addElement(ButtonElement.link("Open", "https://example.com"))
 *     .addElement(ButtonElement.action("Click", "action_123"))
 *     .build();
 * }</pre>
 *
 * <p>Converts to Slack API block via {@link #toLayoutBlock()}.</p>
 *
 * <p>Children elements can be retrieved using {@link #getChildren()}.</p>
 *
 * @author Artur Slimak
 */
@Getter
public class ActionsBlock implements SlackBlock {

    private final List<SlackElement> elements;

    private ActionsBlock(List<SlackElement> elements) {
        this.elements = List.copyOf(elements);
    }

    public static ActionsBlock of(List<SlackElement> elements) {
        return new ActionsBlock(elements);
    }

    @Override
    public LayoutBlock toLayoutBlock() {
        return com.slack.api.model.block.ActionsBlock.builder()
                .elements(
                        elements.stream()
                                .map(SlackElement::toBlockElement)
                                .toList()
                )
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public List<SlackNode> getChildren() {
        return List.copyOf(elements);
    }

    public static class Builder {
        private final List<SlackElement> elements = new ArrayList<>();

        public Builder addElement(SlackElement element) {
            elements.add(element);
            return this;
        }

        public ActionsBlock build() {
            return new ActionsBlock(elements);
        }
    }
}
