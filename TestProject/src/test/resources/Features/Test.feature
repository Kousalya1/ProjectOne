Feature: Get weather information

  @Test
  Scenario Outline: Get weather information from UI and API and compare
    Given I launch application url
    When I search location "<Location>"
    And Get weather information from UI
    And Get weather information from API for the "<Location>"
    Then Compare API response data with UI data

    Examples:
    | Location                  |
    | Chennai, Tamil Nadu, IN   |
    | Pasir Ris, North East, SG |
    | Los Angeles, CA, US       |