package com.example.webdriver;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
class WebDriverApplicationTests {


    private WebDriver driver;

    @Test
    void test(ChromeDriver driver) {
        // Exercise
        String sutUrl = "https://www.youtube.com/";
        driver.get(sutUrl);
        String title = driver.getTitle();

        // Verify
        assertThat(title).isEqualTo("YouTube");
    }

}
