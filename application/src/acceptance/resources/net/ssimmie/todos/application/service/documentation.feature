Feature: Documentation

  Potential integrators will utilize the API documentation as a guide
  So that they can learn about the resources available and the capabilities offered

  Scenario: API documentation published at runtime
    Given a running instance of the application
    When an api client requests the application's documentation site
    Then the api documentation content is returned
