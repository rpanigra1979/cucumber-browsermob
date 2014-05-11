@presentation

Feature: As a Which? analytics user
  I want to track clickouts and paid page visits
  So that we understand which of our pages are more valuable than the others

  Background:
  Given I have created a new HAR page for the proxy server

  Scenario Outline: Clickout event tracking when navigating away from Reviews page

    And I am on page <page>
    When I bring up Campaigns Which Conversation from the main menu
    Then the clickout event <event> should be fired
  Examples:
    | page        | event                                                                                                   |
    | /technology | conversation\\*http:\\/\\/conversation\\.which\\.co\\.uk\\/#\\?intcmp=GNH\\.Campaigns\\.Conversation\\) |


  Scenario Outline: Event tracking on pages behind paywall

    And I am on page <page>
    When I login
    Then the custom variable <variable> with the value <value> should be set
  Examples:
    | page                                                             | variable   | value |
    | /cars/choosing-a-car/best-cars/best-superminis/                  | PaidAccess | paid  |
