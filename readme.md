# N1netails

<div align="center">
  <img src="https://raw.githubusercontent.com/n1netails/n1netails/refs/heads/main/n1netails_icon_transparent.png" alt="N1ne Tails" width="500" style="display: block; margin: auto;"/>
</div>

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](slack-client/LICENSE)

![Stars](https://img.shields.io/github/stars/n1netails/n1netails-slack-client)
![Issues](https://img.shields.io/github/issues/n1netails/n1netails-slack-client)
![Contributors](https://img.shields.io/github/contributors/n1netails/n1netails-slack-client)
![Last Commit](https://img.shields.io/github/last-commit/n1netails/n1netails-slack-client)

# Slack Client
This client allows you to send messages to a Slack channel using a Slack App.

## Setup
1.  **Create a Slack App:**
    *   Go to [https://api.slack.com/apps](https://api.slack.com/apps) and click "Create New App".
    *   Choose "From scratch", give your app a name, and select the workspace where you'll use it.
2.  **Add Permissions:**
    *   In your app's settings, go to "OAuth & Permissions".
    *   Scroll down to the "Scopes" section. Under "Bot Token Scopes", click "Add an OAuth Scope".
    *   Add the `chat:write` scope. This allows your app to send messages to channels it's a member of.
3.  **Install the App:**
    *   At the top of the "OAuth & Permissions" page, click "Install App to Workspace".
    *   Follow the prompts to authorize the app.
4.  **Get Your Bot Token:**
    *   After installation, you'll see a "Bot User OAuth Token". It will start with `xoxb-`. Copy this token.
5.  **Add the App to a Channel:**
    *   In your Slack workspace, go to the channel where you want to send messages.
    *   Type `/invite @your-app-name` (replace `your-app-name` with the name of your app) and send the message.

## Install
Install the slack client by adding the following dependency:
```xml
<dependency>
    <groupId>com.n1netails</groupId>
    <artifactId>n1netails-slack-client</artifactId>
    <version>0.3.0</version>
</dependency>
```

Gradle (Groovy)
```groovy
implementation 'com.n1netails:n1netails-slack-client:0.3.0'
```

## Usage
Here's how to use the client to send a message, pics or gifs:

```java
import com.n1netails.n1netails.slack.api.SlackClient;
import com.n1netails.n1netails.slack.model.SlackMessage;
import com.n1netails.n1netails.slack.model.actions_element.ButtonElement;
import com.n1netails.n1netails.slack.model.block.TextBlock;
import com.n1netails.n1netails.slack.model.block.ImageBlock;
import com.n1netails.n1netails.slack.model.block.GifBlock;
import com.n1netails.n1netails.slack.model.block.ActionsBlock;
import com.n1netails.n1netails.slack.model.element.ButtonElement;
import com.n1netails.n1netails.slack.exception.SlackClientException;

public class Example {
    public static void main(String[] args) {
        // Your bot token
        String token = "xoxb-your-bot-token";

        // Channel to send the message to
        String channel = "#prototype";

        // Build the Slack client
        SlackClient slackClient = SlackClient.builder()
                .token(token)
                .build();

        // Build an ActionsBlock
        ActionsBlock actionsBlock = ActionsBlock.builder()
                .addElement(ButtonElement.link("Visit Website", "https://example.com"))
                .addElement(ButtonElement.builder().text("Click me").actionId("YOUR ACTION ID").build()) //Elements support 2 types of creation
                .build();

        // Build the Slack message
        SlackMessage message = SlackMessage.builder()
                .channel(channel)
                .text("New content 🚀") // fallback message for notifications
                .addBlock(TextBlock.builder().text("New content 🚀").build())
                .addBlock(ImageBlock.builder()
                        .imageUrl("https://n1netails.com/img/n1netails_icon_transparent.png")
                        .altText("N1netails token")
                        .build())
                .addBlock(ImageBlock.of("https://n1netails.com/img/quickstart/n1netails-letter.jpg", "N1netails letter")) //Blocks support 2 types of creation
                .addBlock(GifBlock.builder()
                        .gifUrl("https://media1.giphy.com/media/v1.Y2lkPTc5MGI3NjExaDRhOWtpMnVsM2NiMzJ4aXpoOXpuamZzcHpudG4zbzIzenVlaHN0eSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/xsE65jaPsUKUo/giphy.gif")
                        .altText("Fox GIF")
                        .build())
                .addBlock(actionsBlock) // add the actions block with buttons
                .build();

        try {
            // Send the message
            slackClient.sendMessage(message);
            System.out.println("Message sent successfully!");
        } catch (SlackClientException e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }
}
```

## Advanced Usage (Using Block Kit)
You can also send more complex messages using [Slack's Block Kit](https://api.slack.com/block-kit).

```java
import com.n1netails.n1netails.slack.api.SlackClient;
import com.n1netails.n1netails.slack.model.SlackMessage;
import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.composition.BlockCompositions;
import com.n1netails.n1netails.slack.exception.SlackClientException;

import java.util.List;

public class AdvancedExample {
    public static void main(String[] args) {
        String token = "xoxb-your-bot-token";
        String channel = "#prototype";

        // Build the Slack client
        SlackClient slackClient = SlackClient.builder()
                .token(token)
                .build();

        // Build a Slack message with custom Block Kit blocks
        SlackMessage message = SlackMessage.builder()
                .channel(channel)
                .text("This is a fallback message for notifications.") // fallback
                .addRawBlock(
                        Blocks.section(section ->
                                section.text(BlockCompositions.markdownText("*This is a message with custom blocks.*"))
                        )
                )
                .build();

        try {
            slackClient.sendMessage(message);
            System.out.println("Advanced message sent successfully!");
        } catch (SlackClientException e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }
}
```

#### Example message output
<div align="center">
  <img src="slack-message.png" alt="N1netails slack message simple" width="500" style="display: block; margin: auto;"/>
</div>

#### Example media output
<div align="center">
  <img src="slack-media-message.png" alt="N1netails slack message simple" width="500" style="display: block; margin: auto;"/>
</div>

# Develop
## Build
Build the project using the following command
```bash
mvn clean install
```

## Maven Central Repository
Use the following doc to get setup with publishing to the maven central repository
https://central.sonatype.org/register/central-portal/#publishing

Maven install using release profile.
```bash
mvn clean install -P release
```

Maven deploy to the maven central repository
```bash
mvn deploy -P release
```

### GPG
Generate keys
```bash
gpg --full-generate-key
```

List keys
```bash
gpg --list-secret-keys --keyid-format LONG
```

Export
### Export private key
```bash
gpg --export-secret-keys --armor YOUR_KEY_ID > private.asc
```

### Export public key

```bash
gpg --export --armor YOUR_KEY_ID > public.asc
```

Import
```bash
gpg --import private.asc
gpg --import public.asc
```

List packets (good for validating keys were exported correctly)
```bash
gpg --list-packets public.asc
```


## Support

For community users, open an issue on GitHub or Join our Discord

[![Join our Discord](https://img.shields.io/badge/Join_Discord-7289DA?style=for-the-badge&logo=discord&logoColor=white)](https://discord.gg/ma9CCw7F2x)

## Contributing

Please use the following guidelines for contributions [CONTRIBUTING](slack-client/contributing.md)

## N1netails Slack Client Contributors

Thanks to all the amazing people who contributed to N1netails Slack Client! 💙

[![Contributors](https://contrib.rocks/image?repo=n1netails/n1netails-slack-client)](https://github.com/n1netails/n1netails-slack-client/graphs/contributors)