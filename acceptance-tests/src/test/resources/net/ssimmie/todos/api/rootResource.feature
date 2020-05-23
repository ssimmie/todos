Feature: Root Resource

  Our clients want a listing of available resources
  So that they can potentially utilize what's available

  Scenario: Root Resource reveals available resources
    Given an api client
    When the api client queries the application's root resource
    Then the services available resources should be returned

