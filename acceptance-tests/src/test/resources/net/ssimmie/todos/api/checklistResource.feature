Feature: Checklist Resource

  Our clients want to view checklists
  And query their existing todos
  So that they can manage their workload

  Scenario: View existing checklist
    Given an api client
    And the api client has discovered available resources
    And the api client creates a new checklist
    When the api client requests the checklist
    Then the checklist will be returned by the service
