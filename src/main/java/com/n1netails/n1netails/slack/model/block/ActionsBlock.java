package com.n1netails.n1netails.slack.model.block;

import com.n1netails.n1netails.slack.model.SlackBlock;
import com.n1netails.n1netails.slack.model.SlackElement;
import com.slack.api.model.block.LayoutBlock;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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
