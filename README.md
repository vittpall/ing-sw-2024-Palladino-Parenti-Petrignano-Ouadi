
# Codex Naturalis Academic Year 2023/2024

This is a software engineering project for the academic year 2023/2024, presented on July 3, 2024.

## Table of Contents

- [Group Members](#group-members)
- [Functionalities](#functionalities)
- [How to Use](#how-to-use)


## Group Members

- Vittorio Palladino
- Marouan Ouadi
- Carolina Parenti
- Valentina Petrignano

## Functionalities

This project has implemented 2 out of 4 advanced functionalities:

| Feature                              | Implemented        |
|:-------------------------------------|:-------------------|
| Multiple Games                       | :heavy_check_mark: |
| Chat                                 | :heavy_check_mark: |
| Socket and RMI Communication         | :heavy_check_mark: |
| Complete Rules                       | :heavy_check_mark: |
| TUI + GUI Interfaces                 | :heavy_check_mark: |
| Server Disconnections Handling       | :x:                |
| Resilience to Client Disconnections  | :x:                |

## How to Use

The application is divided into two executable JAR files: `client.jar` and `server.jar`.

### Running the Server

1. Download the JAR files to your local machine.
2. Navigate to the directory containing the downloads.
3. Launch the server with the following command:
   ```shell
   java -jar server.jar
   ```
4. When prompted, enter your IP address (or press Enter to use localhost for local play).
   - To find your IP address:
      - On Windows: `ipconfig`
      - On MacOS: `ipconfig getifaddr en0`

### Running the Client with JavaFX Configuration

To avoid JavaFX warnings, configure your environment as follows before starting the client:

1. Set the module path to the location of your JavaFX SDK installation:
   ```shell
   java --module-path "path\to\your\javafx-sdk\lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media,java.desktop,java.logging -jar clientWIN.jar
   ```

2. After starting the client, enter the server IP address obtained earlier.
3. Select your preferred game mode:
   | Game Mode    | Value to Enter |
   |--------------|----------------|
   | TUI + RMI    | 1              |
   | TUI + SOCKET | 2              |
   | GUI + RMI    | 3              |
   | GUI + SOCKET | 4              |

4. Follow the on-screen instructions to begin playing.

### Additional Instructions for Other Interfaces

For interfaces other than Windows, you will need to clone the repository and build the project yourself. Follow these steps:

1. Clone the repository to your local machine.
2. Navigate to the repository directory.
3. Run the following Maven command to clean the project and package it into JAR files:
   ```shell
   mvn clean package
   ```
4. This process will generate the necessary JAR files in the `target` class directory.
