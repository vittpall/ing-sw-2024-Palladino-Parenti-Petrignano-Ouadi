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

Once you've download them go into the directory, where they'd been downloaded, type
`java -jar server.jar`
Once the server is running, insert your Personal ip (press enter to play in local on the same machine) that you could find typing
`ipconfing` (in WinOS)
`ipconfig getifaddr en0` (in MacOS)
Then use the previous command to run also all the client
`java -jar clientWIN.jar`
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

# Install dependencies
npm install



