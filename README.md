# SaeJBot: Advanced Discord Chat Bot
This project introduces an advanced Discord bot, SaeJBot, which uses OpenAI's GPT-3 and Java to foster real-time user interactions with AI-powered responses.

## Description
- SaeJBot processes user queries, communicates with the OpenAI API, and delivers high-quality responses on Discord.

## Installation
- The bot uses Maven as a build tool. To install and run the bot, you need JDK 11 or higher and Maven installed on your system.

1. Clone the repository to your local machine.
2. Navigate to the project directory in your terminal.
3. Run the command mvn clean install to build the project.
4. Now, run the bot using java -jar target/<your-jar-file>.jar.

## Configuration
SaeJBot requires some environment variables to run:

- `TOKEN`: The Discord bot token.
- `OPENAI_KEY`: The API key from OpenAI.
These can be added in a ".env" file in the project root directory, or directly set in your system's environment variables.

## Usage
Once the bot is running and connected to your Discord server, you can interact with it using the following command:

- `!ask <your-question>`: Ask the bot a question, and it will respond using AI.

## Contributing
Contributions, issues, and feature requests are welcome. Feel free to check the issues page if you want to contribute.
