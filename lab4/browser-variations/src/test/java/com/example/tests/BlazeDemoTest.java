package com.example.tests;

import com.example.pages.BlazeDemoChooseFlightPage;
import com.example.pages.BlazeDemoHomePage;
import com.example.pages.BlazeDemoPurchasePage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlazeDemoTest {
    private WebDriver driver;
    private BlazeDemoHomePage homePage;
    private BlazeDemoChooseFlightPage chooseFlightPage;
    private BlazeDemoPurchasePage purchasePage;

    @BeforeEach
    void setUp() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();

        // url for docker
        //docker pull selenium/standalone-chrome
        //docker run -d -p 4444:4444 selenium/standalone-chrome
        URL gridUrl = new URL("http://localhost:4444/wd/hub");

        //init web driver remote
        driver = new RemoteWebDriver(gridUrl, options);

        //init page controls
        homePage = new BlazeDemoHomePage(driver);
        chooseFlightPage = new BlazeDemoChooseFlightPage(driver);
        purchasePage = new BlazeDemoPurchasePage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testBlazeDemo() {
        homePage.navigateToHomePage();

        homePage.selectDepartureCity("Boston");
        homePage.selectDestinationCity("Rome");
        homePage.clickFindFlightsButton();

        chooseFlightPage.clickChooseThisFlightButton();

        purchasePage.enterName("Artur Denderski");
        purchasePage.enterAddress("Jaracza 36/15");
        purchasePage.enterCity("Lodz");
        purchasePage.enterState("lodzkie");
        purchasePage.enterZipCode("90-252");
        purchasePage.enterCreditCardNumber("1111111111111111");
        purchasePage.enterNameOnCard("Artur Denderski");
        purchasePage.clickPurchaseButton();

        String confirmationPageTitle = purchasePage.getPageTitle();
        assertEquals("BlazeDemo Confirmation", confirmationPageTitle);
    }
}
