Feature: Documentation

  Potential integrators will utilize the API documentation as a guide
  So that they can learn about the resources available and the capabilities offered

  Scenario: API documentation published at runtime
    Given an api client
    When the api client requests the application's documentation site
    Then the api documentation content is returned
