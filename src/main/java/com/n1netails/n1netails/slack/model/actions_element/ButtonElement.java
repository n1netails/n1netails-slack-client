package com.n1netails.n1netails.slack.model.actions_element;

import com.n1netails.n1netails.slack.model.SlackElement;
import com.slack.api.model.block.element.BlockElement;
import lombok.Getter;

@Getter
public class ButtonElement implements SlackElement {
    private final String text;
    private final String actionId;
    private final String url;

    private ButtonElement(String text, String actionId, String url) {
        this.text = text;
        this.actionId = actionId;
        this.url = url;
    }

    public static ButtonElement link(String text, String url) {
        return new ButtonElement(text, null, url);
    }

    public static ButtonElement action(String text, String actionId) {
        return new ButtonElement(text, actionId, null);
    }

    @Override
    public BlockElement toBlockElement() {
        return com.slack.api.model.block.element.ButtonElement.builder()
                .text(com.slack.api.model.block.composition.PlainTextObject.builder()
                        .text(text)
                        .build())
                .actionId(actionId)
                .url(url)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String text;
        private String actionId;
        private String url;

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder actionId(String actionId) {
            this.actionId = actionId;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public ButtonElement build() {
            return new ButtonElement(text, actionId, url);
        }
    }
}
