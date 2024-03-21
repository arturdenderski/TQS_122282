Feature: Calculator Test

  Scenario: Addition
    When I add 4 and 5
    Then the result is 9

  Scenario: Subtraction
    When I subtract 2 from 7
    Then the result is 5

  Scenario: Multiplication
    When I multiply 3 by 8
    Then the result is 24

  Scenario: Division
    When I divide 10 by 2
    Then the result is 5

  Scenario: Division by Zero
    When I divide 10 by 0
    Then the calculator throws an error
