package config;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

public class TestEnvironmentConfigurator {

    @Getter
    private static final Config config = ConfigFactory.create(Config.class, System.getProperties());
    //todo: сделать создание статического конфига

    //todo: расписать все методы и для них геттеры сделать

    public void createWebDriver() {
        //RestAssured.baseURI = config.getBaseUrl();
    }
}