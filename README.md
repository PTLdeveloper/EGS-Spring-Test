# EGS-Spring-Test

## About
This is the solution to the technical test presented for the interview process at EGS Global.

## Dependencies
This project contain multiple dependencies managed with Gradle. Consult the file gradle.build for a list of them.

## Running locally
To run this application:
- In Eclipse (or other IDE):
    1. Run ServiceApplication. This will start the service that will process the requests.
    2. Run CustomerManager. This will show the command line menu with access to the different options.
- In Git:
    1. Open a Git CLI and navigate to CSVReaderService\CSVReaderService
    2. Execute ./gradlew bootrun
    3. Open a second Git CLI and navigate to CSVReaderService\CSVReaderService
    4. Execute java -jar lib/build/libs/lib.jar

## Technical resources
IDE: Eclipse version 2022-12 (4.26.0)
Java 19
Spring Boot 3.0.4
JUnit 5

## Owner
Pablo Tejero