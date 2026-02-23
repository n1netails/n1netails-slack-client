package com.n1netails.n1netails.slack.model.actions_element;

import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.SlackElement;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.BlockElement;
import com.slack.api.model.block.element.ButtonElement;
import lombok.Getter;

@Getter
public class Button implements SlackElement {
    private final String text;
    private final String actionId;
    private final String url;

    public Button(String text, String actionId, String url) {
        this.text = text;
        this.actionId = actionId;
        this.url = url;
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
}
