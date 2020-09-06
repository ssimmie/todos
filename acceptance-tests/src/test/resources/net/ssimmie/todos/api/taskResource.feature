Feature: Tasks Resource

  Our clients want to create new tasks
  And query their existing tasks
  So that they can manage their workload

  Scenario: Tasks Resource facilitates creation of new tasks
    Given an api client
    And the api client has discovered available resources
    When the api client creates a new task
    Then the task will be maintained by the service
