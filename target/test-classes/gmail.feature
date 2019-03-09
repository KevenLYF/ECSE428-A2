Feature: gmail

  Scenario: Send an email with picture A to recipient A
    Given I am logged in to gmail main page
    When I click on Compose
    And I add recipient A to my email
    And I add a subject to my email
    And I add Picture A to my email
    And I click Send
    Then the email should be sent

  Scenario: Send an email with picture B to recipient B
    Given I am logged in to gmail main page
    When I click on Compose
    And I add recipient B to my email
    And I add a subject to my email
    And I add Picture B to my email
    And I click Send
    Then the email should be sent

  Scenario: Send an email with picture A to recipient B
    Given I am logged in to gmail main page
    When I click on Compose
    And I add recipient B to my email
    And I add a subject to my email
    And I add Picture A to my email
    And I click Send
    Then the email should be sent

  Scenario: Send an email with picture B to recipient A
    Given I am logged in to gmail main page
    When I click on Compose
    And I add recipient A to my email
    And I add a subject to my email
    And I add Picture B to my email
    And I click Send
    Then the email should be sent

#
#  Scenario: Send an email without specifying a recipient
#    Given I am logged in to gmail main page
#    When I click on Compose
#    And I add a subject to my email
#    And I add a picture to my email
#    And I click Send
#    Then an error message is displayed specifying recipient is needed

