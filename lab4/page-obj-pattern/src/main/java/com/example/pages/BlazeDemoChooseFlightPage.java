package com.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BlazeDemoChooseFlightPage {
    private WebDriver driver;

    @FindBy(css = "input[type='submit']")
    private WebElement chooseThisFlightButton;

    public BlazeDemoChooseFlightPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickChooseThisFlightButton() {
        chooseThisFlightButton.click();
    }
}

