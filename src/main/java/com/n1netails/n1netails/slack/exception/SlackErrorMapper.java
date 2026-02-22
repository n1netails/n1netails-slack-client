package com.n1netails.n1netails.slack.exception;

import com.slack.api.methods.response.chat.ChatPostMessageResponse;

import java.util.ArrayList;
import java.util.List;

public class SlackErrorMapper {

    public static SlackValidationException map(ChatPostMessageResponse response) {
        if (response.getErrors() == null || response.getErrors().isEmpty()) {
            return new SlackValidationException(response.getError());
        }

        List<String> formattedErrors = new ArrayList<>();

        for (String error : response.getErrors()) {
            formattedErrors.add(format(error));
        }

        return new SlackValidationException(
                "Slack API validation failed:\n - " + String.join("\n - ", formattedErrors)
        );
    }

    private static String format(String rawError) {
        try {
            String message = rawError;
            int pointerStart = rawError.indexOf("[json-pointer:");

            if (pointerStart > -1) {
                message = rawError.substring(0, pointerStart).trim();

                String pointer = rawError.substring(pointerStart)
                        .replace("[json-pointer:", "")
                        .replace("]", "");

                return mapPointer(pointer, message);
            }

            return message;

        } catch (Exception e) {
            return rawError; // fallback
        }
    }

    private static String mapPointer(String pointer, String message) {
        String[] parts = pointer.split("/");

        if (parts.length >= 4 && "blocks".equals(parts[1])) {
            int index = Integer.parseInt(parts[2]);
            String field = parts[3];

            return String.format(
                    "Block[%d].%s: %s",
                    index,
                    field,
                    message
            );
        }

        return pointer + ": " + message;
    }
}
