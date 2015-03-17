Feature: Find an Application
  As a Service Administrator
  I want to find a specific Application
  In order to view its data

  Scenario: Find a specific Application using its key
    Given the service location is known
    Given there are Applications
    Given the Application key is known
    When an attempt is made to find an application by key
    Then there are no errors
    Then the Application is produced

  Scenario: Attempt to find an Application with an invalid key
    Given the service location is known
    Given there are Applications
    Given the Application key is known
    When an attempt is made to find an application by key
    Then a message indicating the Application could not be found is produced