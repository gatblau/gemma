Feature: Create Web API Spec from Interface
  As a Web API developer
  I want to create a serializable object that contains both the functional and the technical specification for my Web API
  In order to return it to clients using the API for documentation purposes

  Scenario: Create Spec
    Given that there is a fully annotated Interface for the Web API
    Given a Specification instance is created passing in the Interface
    When the spec is serialised
    Then the spec contains the name of the service
    Then the spec contains the description of the service
    Then the spec contains the version of the service
    Then the spec contains the service release date
    Then the spec contains the terms of the service
    Then the spec contains the author of the service
    Then the spec contains multiple features
    Then the functional spec describes the feature summary
    Then the functional spec describes the user story
    Then the functional spec describes key usage scenarios
    Then the technical spec describes the API path
    Then the technical spec describes the API method
    Then the technical spec describes the API parameters
    Then the technical spec describes the consumed representations
    Then the technical spec describes the produced representations
    Then the technical spec describes examples of use
