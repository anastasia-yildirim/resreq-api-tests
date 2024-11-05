package tests;

import models.register.RegisterRequestModel;
import models.register.RegisterResponseModel;
import models.register.UnsuccessfulRegisterResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static specs.ReqresSpec.defaultRequestSpec;
import static specs.ReqresSpec.responseSpec200;
import static specs.ReqresSpec.responseSpec400;

public class RegistrationTests extends TestBase {

    @DisplayName("Успешная регистрация")
    @Test
    void successfulRegisterTest() {
        RegisterRequestModel bodyData = new RegisterRequestModel();
        bodyData.setEmail("eve.holt@reqres.in");
        bodyData.setPassword("pistol");

        RegisterResponseModel response = step("Make request", ()->
                given(defaultRequestSpec)
                    .body(bodyData)
                .when()
                    .post("/register")
                .then()
                    .spec(responseSpec200)
                    .extract().as(RegisterResponseModel.class));

        step("Check response", ()-> {
        assertNotEquals(null, response.getId());
        assertThat(response.getToken().length(), greaterThan(10));
        assertThat(response.getToken(), is(notNullValue()));
        assertThat(response.getToken(), matchesPattern("^[a-zA-Z0-9]+$"));
        });
    }

    @DisplayName("Неуспешная регистрация - невалидный пользователь")
    @Test
    void unsuccessfulRegisterUndefinedUserTest() {
        RegisterRequestModel bodyData = new RegisterRequestModel();
        bodyData.setEmail("lewis.carol@reqres.in");
        bodyData.setPassword("alice");

        String expectedErrorText = "Note: Only defined users succeed registration";

        UnsuccessfulRegisterResponseModel response = step("Make request", ()->
                given(defaultRequestSpec)
                    .body(bodyData)
                .when()
                    .post("/register")
                .then()
                    .spec(responseSpec400)
                    .extract().as(UnsuccessfulRegisterResponseModel.class));

        step("Check response", ()-> assertEquals(expectedErrorText, response.getError()));
    }

    @DisplayName("Неуспешная регистрация - отсутствует пароль")
    @Test
    void unsuccessfulRegisterMissingPasswordTest() {
        RegisterRequestModel bodyData = new RegisterRequestModel();
        bodyData.setEmail("eveasdas.holt@reqres.in");
        bodyData.setPassword(null);

        String expectedErrorText = "Missing password";

        UnsuccessfulRegisterResponseModel response = step("Make request", ()->
                given(defaultRequestSpec)
                    .body(bodyData)
                .when()
                    .post("/register")

                .then()
                    .spec(responseSpec400)
                    .extract().as(UnsuccessfulRegisterResponseModel.class));

        step("Check response", ()-> assertEquals(expectedErrorText, response.getError()));
    }
}