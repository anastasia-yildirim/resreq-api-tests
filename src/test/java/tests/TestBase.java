package tests;

import config.TestEnvironmentConfigurator;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static helpers.Attachments.*;
import static helpers.LoginExtension.clearSession;
import static io.qameta.allure.Allure.step;

public class TestBase {

    private static final TestEnvironmentConfigurator driver = new TestEnvironmentConfigurator();

    @BeforeAll
    public static void setUp() {
        driver.createWebDriver();
    }

    @AfterEach
    void shutDown() {
        //generateDataForAllureReport();

//        screenshotAs("Last screenshot");
//        pageSource();
//        browserConsoleLogs();
//        addVideo();
//
//        closeWebDriver();
//        clearSession();

        step("Take last screenshot", () -> screenshotAs("Last screenshot"));
        step("Capture page source", () -> pageSource());
        step("Log browser console messages", () -> browserConsoleLogs());
        step("Add video recording to report", () -> addVideo());

        step("Close the WebDriver", () -> closeWebDriver());
        step("Clear browser session", () -> clearSession());
    }
}