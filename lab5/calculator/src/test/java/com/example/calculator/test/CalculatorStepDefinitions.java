package com.example.calculator.test;

import com.example.calculator.Calculator;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorStepDefinitions {

    private int result;
    private Calculator calculator = new Calculator();
    private Throwable exception = null;

    @When("I add {int} and {int}")
    public void iAdd(int x, int y) {
        result = calculator.add(x, y);
    }

    @When("I subtract {int} from {int}")
    public void iSubtract(int x, int y) {
        result = calculator.subtract(x, y);
    }

    @When("I multiply {int} by {int}")
    public void iMultiply(int x, int y) {
        result = calculator.multiply(x, y);
    }

    @When("I divide {int} by {int}")
    public void iDivideBy(int x, int y) {
        if (y == 0) {
            exception = assertThrows(IllegalArgumentException.class, () -> {
                result = calculator.divide(x, y);
            });
        } else {
            result = calculator.divide(x, y);
        }
    }

    @Then("the result is {int}")
    public void theResultIs(int expected) {
        assertEquals(expected, result);
    }

    @Then("the calculator throws an error")
    public void theCalculatorThrowsAnError() {
        assertEquals(IllegalArgumentException.class, exception.getClass());
    }
}
