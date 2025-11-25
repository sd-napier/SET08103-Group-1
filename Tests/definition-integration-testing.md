# Definition of Integration Tests

This document defines the integration tests for the **Population Reporting System**, including the testing strategy, design, and technologies to be used.


## Scope of Integration Tests

The integration tests for this project are designed to verify that the Population Reporting System successfully interacts with the database and produces the expected results.


## Features Covered

1. **Country Reports**  
   Integration tests for city reports will verify that the system correctly retrieves all the cities globally that are in the database, as well as by continent, region, country, and district.  
   Top N city reports will also be tested to confirm that the expected number of cities is returned for any filter.

2. **Population Reports**  
   Integration tests for population reports will verify that the system correctly retrieves population information for all countries, continents, and regions in descending order.  
   Additionally, these tests will confirm that the top N populations are correctly selected when requested by the user.  
   Population aggregation tests will also check that the total number of people, the number of people living in cities, and the number of people not living in cities are accurately calculated.  
   This ensures that percentage calculations for city versus non-city populations are correct.

3. **Capital Reports**  
   The integration tests for capital cities will verify that all capital cities are returned in descending order of population (largest to smallest).  
   These tests will be run globally and also filtered by continent or region.  
   Additionally, top N capital city queries will be checked to verify the system correctly limits results while maintaining the correct order.  
   Since capitals are a subset of cities, testing capital reports will also reinforce the correctness of city-level queries.

4. **Language Reports**  
   The integration tests for languages will verify that the system correctly reports the number of speakers of major languages such as Chinese, English, Hindi, and others.  
   These tests will ensure that the total and percentage of speakers are calculated accurately and that data ordering and aggregation are consistent across different queries.


## Test Strategy

Integration testing focuses on verifying the application’s functionality and ensuring that query results match the database data.

Instead of testing every possible report combination, **representative tests** are chosen for each major feature.  
For example, population report tests verify that population data for countries, cities, and languages are returned in the correct order.  
Since one well-structured test for each level provides confidence in consistent query logic across all filters, exhaustive testing of every regional or continental variation is unnecessary.


## Test Design

Tests will have clear and descriptive names reflecting their purpose — for example:  
test_percentage_of_chinese_speakers_globally_equals()

GitHub integration tests will also be tagged using @test_integration so that they can run separately from unit tests.  
Comments will be added for each test to explain the expected outcome.

Tests will fall into three main categories:

- **Positive Tests** — Verify the system’s correct behavior with valid data and inputs.  
  Example: A method testing the top five countries by population should return exactly five countries in the correct order.

- **Negative Tests** — Check how the system handles invalid, missing, or unexpected inputs.  
  Example: Entering a letter instead of a number for "Top N countries" should be gracefully handled or rejected.

- **Edge Cases** — Focus on the limits of acceptable input data ranges.  
  Example: Queries with N=0, N=1, or large N values should behave consistently and not cause errors.

JUnit 5 assertion methods will be used for creating each test. Example methods include:

- assertEquals()
- assertNotNull()
- assertTrue()
- assertFalse()


## Technologies to Be Used

- **Java** will be used for programming the tests.
- **IntelliJ IDEA** will be used to run, debug, and inspect integration tests.
- **JUnit 5** will be used for creating automated tests.
- **Codecov** will be used to track code coverage.
- **GitHub Actions** will be used for continuous integration.

Integration tests will run automatically through GitHub Actions on every pull request or merge to guarantee that code changes do not break integration functionality.


## Test Coverage Reporting

Codecov will be integrated to track code coverage and display a percentage badge on GitHub.  
Coverage reports will be generated via JUnit 5 tests.  
The goal is to maintain high coverage throughout the development phase to ensure system reliability and early detection of integration issues.
