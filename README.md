## Synopsis

This project tracks the development of Role-Based Middleware (RBMW) using java SDK with JBuilder as the IDE environment.

## Code Example

All the source code resides under src. We use CERN's random number generator and its in cern. The simulator includes the discrete-event-queue based simulation framework. Actual simulation of role-assigment based on several metrics on a randomly generated wireless sensor network topology is in folder rbmwsimulator.

## Motivation

This project was developed during the 2010-2012 timeframe when Wireless Sensor Networks (WSNs) were all the rage. It is now rebranded under the marketing term "IoT"

## Installation

Please follow the steps listed below to checkout RBMW Simulator. Some of the Swing and AWT components from jdk-1.2 are now deprecated. You may need to replace these deprecated ones with the current. Having said that, you can checkout this repository:
1. clone the git repo RBMWSimulator.git using the following command: git clone https://github.com/ag8775/RBMWSimulator.git
2. Launch your favorite merge tool to merge the components in your just cloned workspace
3. Identify the function you wish to change under rbmwsimulator

## API Reference

Depending on the size of the project, if it is small and simple enough the reference docs can be added to the README. For medium size to larger projects it is important to at least provide a link to where the API reference docs live.

## Tests

Describe and show how to run the tests with code examples.

## Contributors

Please checkout research papers on "Role-based Assignment in Wireless Sensor Networks" at Google Scholar: https://scholar.google.com/citations?user=vMfpdekAAAAJ&hl=en

## License

Free to reuse and disribute under the MIT license.