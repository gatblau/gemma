Feature: Find Applications
  As a Service Administrator
  I want to find all registered applications
  In order to view specific application data

  Scenario: Find all Applications
    Given the service location is known
    Given there are Applications
    When an attempt is made to find all registered applications
    Then a list of Applications is produced