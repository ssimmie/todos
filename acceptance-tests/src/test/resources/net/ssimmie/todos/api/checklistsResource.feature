Feature: Checklists Resource

  Our clients want to create new checklists
  And query their existing checklists
  So that they can manage their workload

  Scenario: Create named checklists
    Given an api client
    And the api client has discovered available resources
    When the api client creates a new checklist
    Then the checklist will be maintained by the service
