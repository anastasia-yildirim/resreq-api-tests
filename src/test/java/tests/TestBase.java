package tests;

import config.Config;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    private static final Config config = ConfigFactory.create(Config.class, System.getProperties());

    @BeforeAll
    public static void setUp() {

        RestAssured.baseURI = config.baseURI();
        RestAssured.basePath = config.basePath();
    }
}