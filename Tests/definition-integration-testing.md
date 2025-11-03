# Definition of Integration Tests
This document defines the integration tests for the population reporting system, the testing startegy and the technolgies to be used.

## Scope of integration tests
The integration tests for this project are designed to verify that the population reporting system successfully interacts with the database and produces the expected results. 


## Features covered
1. Country Reports
Integration tests for city reports will verify that the system correctly retrieves all the cities globally that’s in the database, as well as by continent, region, country and district. Top N city reports will also be tested to confirm that the expected number of cities is returned for any filter. 

2. Population Reports
Integration tests for population reports will verify that the system correctly retrieves population information for all countries, continents, and regions in descending order.  Additionally, these tests will ensure that the top N populations are correctly selected when requested by the user. Population aggregation tests will also check that the total number of people, the number of people living in cities, and the number of people not living in cities are accurately calculated. This will ensure that the percentage calculations for city versus non-city populations are correct.

3. Capital Reports
The integration tests for capital cities will verify that all capital cities are returned in descending order of population (largest to smallest). These tests will be run globally and also filtered by content or region. Additionally, top N capital city queries will be checked to ensure the system correctly limits results while maintaining the correct order. Since capitals are a subset of cities, testing capital reports will also reinforce the correctness of city-level queries.

4. Language Reports
The integration tests for languages will verify that the system correctly shows the number of speakers of major languages like Chinese, English, Hindi etc. 


## Test Strategy
Tests will contain comments to explain the test and expected output and Tests will be named appropriately to reflect their purpose. For example, test_percentageof_chinesespeakers_globally_equals.

Each integration test verifies that the reports produce the expected output. We’ll test code that should work, fail, and handle any exceptions.

JUNit5 assertion methods will be used, including assertEquals, assertNotNull, assertTrue and assertFalse.
Tests will be tagged using @test_integration so that they can run sperately from unit tests.

Instead of testing every possible report combination, representative tests are chosen for each major feature. For example, population reports are checked to ensure countries, cities, and capitals are returned in the correct order of population.

## Technologies to be used
Java will be used for programming the tests.

IntelliJ will be used to run, debug and inspect integration tests.

Junit5 will be used for creating automated tests.

codecov will be used to track codecoverage.

GitHub Actions will be used to run integration tests automnattically. Tests will run on every pull request or merge to ensure rthat code changes wont brrak integration functionality.

## Test Coverage Reporting
Codecov will be integrated to track the code coverage, it will display a % coverage badge on GitHub. The coverage reports will be generated via JUnit5 tests.
The goal is to maintain a high coverage through the development phase.

unusued notes:
would integration tests test docker?

