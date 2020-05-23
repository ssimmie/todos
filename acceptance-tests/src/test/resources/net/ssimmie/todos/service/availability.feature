Feature: Availability

  Our edge distribution network wants to know if the application is available now
  So that it can determine whether or not to route traffic to it

  Scenario: Once started the application is available
    Given an api client
    When the api client queries the application's actuator healthcheck resource
    Then status should be UP in the healthcheck resource representation

