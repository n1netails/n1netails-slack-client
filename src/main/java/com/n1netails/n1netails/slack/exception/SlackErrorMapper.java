package com.n1netails.n1netails.slack.exception;

import com.slack.api.methods.response.chat.ChatPostMessageResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for mapping Slack API {@link ChatPostMessageResponse} errors
 * into human-readable {@link SlackValidationException} messages.
 * <p>
 * This class parses raw Slack API error messages, including JSON pointers,
 * and formats them for easier debugging and display in your application.
 * </p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * ChatPostMessageResponse response = methodsClient.chatPostMessage(request);
 * if (!response.isOk()) {
 *     throw SlackErrorMapper.map(response);
 * }
 * }</pre>
 *
 * <p>Implements static, stateless methods and is thread-safe.</p>
 *
 * @author Artur Slimak
 */
public class SlackErrorMapper {

    /**
     * Maps a {@link ChatPostMessageResponse} from the Slack API into a {@link SlackValidationException}.
     *
     * @param response the Slack API response
     * @return a {@link SlackValidationException} containing formatted error messages
     */
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

    /**
     * Formats a raw Slack API error message, including parsing JSON pointers if present.
     *
     * @param rawError the raw error string from Slack API
     * @return a human-readable formatted error message
     */
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
            return rawError;
        }
    }


    /**
     * Maps a JSON pointer from Slack API errors into a readable path format.
     *
     * @param pointer the JSON pointer string (e.g., "/blocks/0/elements/1")
     * @param message the original error message
     * @return a human-readable error with the pointer path
     */
    private static String mapPointer(String pointer, String message) {
        String[] parts = pointer.split("/");

        StringBuilder path = new StringBuilder();

        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];

            if (isNumeric(part)) {
                path.append("[").append(part).append("]");
            } else {
                if (!path.isEmpty()) {
                    path.append(".");
                }
                path.append(part);
            }
        }

        return path + ": " + message;
    }


    /**
     * Checks if a string is numeric.
     *
     * @param str the string to check
     * @return {@code true} if the string consists only of digits, {@code false} otherwise
     */
    private static boolean isNumeric(String str) {
        return str.chars().allMatch(Character::isDigit);
    }
}
