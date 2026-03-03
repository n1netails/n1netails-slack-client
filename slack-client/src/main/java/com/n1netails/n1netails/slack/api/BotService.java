package com.n1netails.n1netails.slack.api;

import com.n1netails.n1netails.slack.api.builder.SlackBlockBuilder;
import com.n1netails.n1netails.slack.exception.SlackErrorMapper;
import com.n1netails.n1netails.slack.exception.SlackClientException;
import com.n1netails.n1netails.slack.exception.SlackTransportException;
import com.n1netails.n1netails.slack.exception.SlackValidationException;
import com.n1netails.n1netails.slack.model.SlackMessage;
import com.n1netails.n1netails.slack.validation.SlackBlockValidator;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.block.LayoutBlock;

import java.io.IOException;
import java.util.List;

/**
 * Service responsible for sending messages to Slack via the Slack SDK.
 * <p>
 * Encapsulates the Slack {@link MethodsClient}, performs message validation,
 * and builds message blocks using {@link SlackBlockBuilder}.
 * Handles transport and validation exceptions internally.
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * BotService botService = new BotService("xoxb-your-token");
 * SlackMessage message = new SlackMessage("general", "Hello, Slack!");
 * botService.send(message);
 * }</pre>
 *
 * <p>All messages are validated before sending. Text or blocks must be present
 * in the {@link SlackMessage}, otherwise a {@link SlackValidationException} is thrown.</p>
 *
 * <p>Exceptions thrown:</p>
 * <ul>
 *     <li>{@link SlackTransportException} – for network/SDK errors</li>
 * </ul>
 *
 * @author Shahid Foy and Artur Slimak
 */
final public class BotService {
    private final MethodsClient methods;
    private final SlackBlockValidator blockValidator;
    private final SlackBlockBuilder blockBuilder;

    /**
     * Constructs a new {@link BotService} with the given Slack bot token.
     *
     * @param token Slack bot token for authentication; must not be null or blank
     */
    public BotService(String token) {
        this.methods = Slack.getInstance().methods(token);
        this.blockValidator = new SlackBlockValidator();
        this.blockBuilder = new SlackBlockBuilder();

    }


    /**
     * Sends a {@link SlackMessage} to Slack.
     * <p>
     * The message is validated for basic requirements (text or blocks present),
     * and all blocks are validated via {@link SlackBlockValidator}.
     * </p>
     *
     * @param slackMessage the message to send; must not be null
     * @throws SlackClientException if validation fails or sending fails due to network/Slack API errors
     */
    public void send(SlackMessage slackMessage) throws SlackClientException {
        validateBasic(slackMessage);

        blockValidator.validateMessageBlocks(slackMessage);

        try {
            List<LayoutBlock> blocks = blockBuilder.build(slackMessage);

            ChatPostMessageRequest request =
                    ChatPostMessageRequest.builder()
                            .channel(slackMessage.getChannel())
                            .text(slackMessage.getText())
                            .blocks(blocks)
                            .build();

            ChatPostMessageResponse response = methods.chatPostMessage(request);
            if (!response.isOk()) {
                throw SlackErrorMapper.map(response);
            }
        } catch (IOException e) {
            throw new SlackTransportException("Network error while calling Slack API", e);
        } catch (SlackApiException e) {
            throw new SlackTransportException("Slack SDK failure for channel: " + slackMessage.getChannel(), e);
        }
    }

    /**
     * Performs basic validation on a {@link SlackMessage}.
     * <p>
     * Ensures that the message is not null and contains either text, blocks, or raw blocks.
     * </p>
     *
     * @param message the Slack message to validate
     * @throws SlackValidationException if the message is null or contains no text/blocks
     */
    private void validateBasic(SlackMessage message) throws SlackValidationException {
        if (message == null) {
            throw new SlackValidationException("SlackMessage cannot be null");
        }

        if ((message.getText() == null || message.getText().isBlank()) &&
                (message.getBlocks() == null || message.getBlocks().isEmpty()) &&
                (message.getRawBlocks() == null || message.getRawBlocks().isEmpty())) {

            throw new SlackValidationException("Message must contain text or blocks");
        }
    }
}
