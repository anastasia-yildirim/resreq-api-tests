package config;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

public class TestEnvironmentConfigurator {

    //public final Config config;
    @Getter
    private static final Config config = ConfigFactory.create(Config.class, System.getProperties());

    public TestEnvironmentConfigurator() {
        //this.config = ConfigFactory.create(Config.class, System.getProperties());
        createWebDriver();
    }

    public void createWebDriver() {
        Configuration.baseUrl = config.getBaseUrl();
        RestAssured.baseURI = config.getBaseUrl();

//        Configuration.browser = System.getProperty("browser");
//        Configuration.browserVersion = System.getProperty("browserVersion");
//        Configuration.browserSize = System.getProperty("browserSize");

        Configuration.browser = config.browserName();
        Configuration.browserVersion = config.browserVersion();
        Configuration.browserSize = config.browserSize();

        Configuration.pageLoadStrategy = "eager";

        if (config.isRemote()) {
            Configuration.remote = config.getRemoteUrl();

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true));

            Configuration.browserCapabilities = capabilities;
        }
    }
}