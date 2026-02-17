# N1netails

<div align="center">
  <img src="https://raw.githubusercontent.com/n1netails/n1netails/refs/heads/main/n1netails_icon_transparent.png" alt="N1ne Tails" width="500" style="display: block; margin: auto;"/>
</div>

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

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
    <version>0.2.0</version>
</dependency>
```

Gradle (Groovy)
```groovy
implementation 'com.n1netails:n1netails-slack-client:0.2.0'
```

## Usage
Here's how to use the client to send a message, pics or gifs:

```java
import com.n1netails.n1netails.slack.api.SlackClient;
import com.n1netails.n1netails.slack.api.SlackClientImpl;
import com.n1netails.n1netails.slack.model.SlackMessage;
import com.n1netails.n1netails.slack.api.BotService;
import com.n1netails.n1netails.slack.model.blocks.GifBlock;
import com.n1netails.n1netails.slack.model.blocks.TextBlock;

public class Example {
    public static void main(String[] args) {
        // Your bot token
        String token = "xoxb-your-bot-token";

        // The channel you want to send the message to (e.g., "#general")
        String channel = "#prototype"; // or "#channel-name"

        // Use builder to create a client
        SlackClient client = SlackClient.builder().token(token).build();

        // Create the message
        SlackMessage message = SlackMessage.builder()
                .channel("test")
                .text("New content 🚀") //Remember if use ANY blocks this text becomes a fallback message for notifications
                .addBlock(new TextBlock("New content 🚀"))
                .addBlock(new ImageBlock("YOUR IMG LINK", "YOUR ALT TEXT"))
                .addBlock(new GifBlock("YOUR GIF LINK", "YOUR ALT TEXT"))
                .build();

        try {
            // Send the message
            slackClient.sendMessage(message);
            System.out.println("Message sent successfully!");
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }
}
```

## Advanced Usage (Using Block Kit)
You can also send more complex messages using [Slack's Block Kit](https://api.slack.com/block-kit).

```java
import com.n1netails.n1netails.slack.api.SlackClient;
import com.n1netails.n1netails.slack.api.SlackClientImpl;
import com.n1netails.n1netails.slack.model.SlackMessage;
import com.n1netails.n1netails.slack.api.BotService;
import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.composition.BlockCompositions;

import java.util.Arrays;

public class AdvancedExample {
    public static void main(String[] args) {
        String token = "xoxb-your-bot-token";
        String channel = "#prototype";

        SlackClient client = SlackClient.builder().token(token).build();

        SlackMessage message = new SlackMessage();
        message.setChannel(channel);
        message.setText("This is a fallback message for notifications.");
        message.setBlocks(Arrays.asList(
                Blocks.section(section -> section.text(BlockCompositions.markdownText("*This is a message with blocks.*")))
        ));

        try {
            slackClient.sendMessage(message);
            System.out.println("Advanced message sent successfully!");
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }
}
```

#### Example message output
<div align="center">
  <img src="slack-message.png" alt="N1netails slack message simple" width="500" style="display: block; margin: auto;"/>
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

## Support

For community users, open an issue on GitHub or Join our Discord

[![Join our Discord](https://img.shields.io/badge/Join_Discord-7289DA?style=for-the-badge&logo=discord&logoColor=white)](https://discord.gg/ma9CCw7F2x)

## Contributing

Please use the following guidelines for contributions [CONTRIBUTING](./contributing.md)