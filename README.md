# Codex Naturalis Academic Year 2023/2024

Software engeeniering project for the academic year 2023/2024 presented on the 3rd of July 2024.

## Table of Contents

- [Introduction](#Introduction)
- [Group Members](#GroupMembers)
- [Functionalities](#Functionality)
- [How to use](#HowToUse)

## Introduction

Introduce your project. Explain what it does, the problem it solves, and any other relevant information. 

## Group Members

Palladino Vittorio, Ouadi Marouan, Parenti Carolina, Petrignano Valentina.

## Functionalities

2 out of 4 AF (Advanced functionalites) implemented

   | Feature | Implemented  |
|:--------|:----|
| Multiple Games   | :heavy_check_mark:    |
| Chat  | :heavy_check_mark:    |
| Socket and RMI  | :heavy_check_mark:    |
| Complete rules  | :heavy_check_mark:    |
| TUI + GUI  | :heavy_check_mark:    |
| Server disconnections  | :x:    |
| Resilience to clients disconnections  | :x:    |
  
## How to Use

In order to run the application the project was divided into three jar files.
clientWIN.jar
clientMAC.jar
server.jar

### Running the Server

Once you've download them go into the directory, where they'd been downloaded, type
`java -jar server.jar`
Once the server is running, insert your Personal ip (press enter to play in local on the same machine) that you could find typing
`ipconfing` (in WinOS)
`ipconfig getifaddr en0` (in MacOS)

### Running the Client with JavaFX Configuration

Before executing the client application, it is crucial to configure JavaFX to prevent warnings regarding unsupported configurations. If JavaFX is not correctly set up, you may encounter the following warning:

```plaintext
WARNING: Unsupported JavaFX configuration: classes were loaded from 'unnamed module @1e2010df'
```
To configure JavaFX properly and avoid this warning, use the command below, making sure to update the module path to reflect the location of your JavaFX installation on your computer:
Then use the previous command to run also all the client

java --module-path "C:\Users\YourUsername\Desktop\openjfx-22.0.1_windows-x64_bin-sdk\javafx-sdk-22.0.1\lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media,java.desktop,java.logging -jar clientWIN.jar
This format ensures clear instructions and helps maintain the proper structure within your Markdown document.

Once the client is running enter the server ip that you retrieve before, then choose the game mode. 
You can choose between: 

   | Game Mode| Value to enter  |
|:--------|:--------|
| TUI + RMI     | 1 |
| TUI + SOCKET  | 2 |
| GUI + RMI     | 3 |
| GUI + SOCKET  | 4 |

Then follow the instructions on the screen and you're ready to go.

## Installation

Instructions on how to install and set up your project.

# Clone the repository
git clone https://github.com/yourusername/yourprojectname.git

# Change to the project directory
cd yourprojectname




