package com.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BlazeDemoHomePage {
    private WebDriver driver;

    @FindBy(name = "fromPort")
    private WebElement fromPortDropdown;

    @FindBy(name = "toPort")
    private WebElement toPortDropdown;

    @FindBy(css = ".btn-primary")
    private WebElement findFlightsButton;

    public BlazeDemoHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectDepartureCity(String city) {
        fromPortDropdown.sendKeys(city);
    }

    public void selectDestinationCity(String city) {
        toPortDropdown.sendKeys(city);
    }

    public void clickFindFlightsButton() {
        findFlightsButton.click();
    }

    public void navigateToHomePage() {
        driver.get("https://blazedemo.com/");
    }

    public void maximizeWindow() {
        driver.manage().window().maximize();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}
