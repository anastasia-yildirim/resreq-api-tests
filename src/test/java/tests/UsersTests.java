package tests;

import models.users.CreateUpdateUserRequestModel;
import models.users.CreateUpdateUserResponseModel;
import models.users.GetUserResponseModel;
import models.users.GetUsersListResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specs.ReqresSpec.*;

public class UsersTests extends TestBase {

    @DisplayName("Успешное создание пользователя")
    @Test
    void successfulCreateUserTest() {
        CreateUpdateUserRequestModel bodyData = new CreateUpdateUserRequestModel("Ronald McDonald",
                "Entertainment Manager");

        CreateUpdateUserResponseModel response = step("Make request", () ->
                given(defaultRequestSpec)
                        .body(bodyData)
                        .when()
                        .post("/users")
                        .then()
                        .spec(defaultResponseSpec)
                        .statusCode(201)
                        .extract().as(CreateUpdateUserResponseModel.class));

        step("Check response", () -> {
            assertEquals(bodyData.getName(), response.getName());
            assertEquals(bodyData.getJob(), response.getJob());
        });
    }

    @DisplayName("Успешное обновление данных пользователя")
    @Test
    void successfulUpdateUserTest() {
        CreateUpdateUserRequestModel bodyData = new CreateUpdateUserRequestModel("Ronald McDonald",
                "Chief Entertainment Manager");

        CreateUpdateUserResponseModel response = step("Make request", () ->
                given(defaultRequestSpec)
                        .body(bodyData)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(responseSpec200)
                        .extract().as(CreateUpdateUserResponseModel.class));

        step("Check response", () -> {
            assertEquals(bodyData.getName(), response.getName());
            assertEquals(bodyData.getJob(), response.getJob());
        });
    }

    @DisplayName("Успешное получение данных пользователя")
    @Test
    void successfulGetUserDataTest() {

        String expectedEmail = "janet.weaver@reqres.in", expectedFirstName = "Janet",
                expectedLastName = "Weaver";

        GetUserResponseModel response = step("Make request", () ->
                given(defaultRequestSpec)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(responseSpec200)
                        .extract().as(GetUserResponseModel.class));

        step("Check response", () -> {
            assertNotNull(response.getUserData(), "Data should not be null");
            assertNotNull(response.getSupport(), "Support should not be null");
            assertEquals(expectedEmail, response.getUserData().getEmail());
            assertEquals(expectedFirstName, response.getUserData().getFirstName());
            assertEquals(expectedLastName, response.getUserData().getLastName());
        });
    }

    @DisplayName("Неуспешное получение данных пользователя - пользователь не найден")
    @Test
    void userNotFoundTest() {
        step("Make request and check 404 is returned", () ->
                given(defaultRequestSpec)
                        .when()
                        .get("/users/23")
                        .then()
                        .spec(defaultResponseSpec)
                        .statusCode(404));
    }

    @DisplayName("Успешное удаление пользователя")
    @Test
    void successfulDeleteUserTest() {
        step("Make request and check 204 is returned", () ->
                given(defaultRequestSpec)
                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(defaultResponseSpec)
                        .statusCode(204));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    @DisplayName("Успешное получение списка пользователей")
    void successfulGetUsersListTest(int page) {

        GetUsersListResponseModel response = step("Make request", () ->
                given(defaultRequestSpec)
                        .when()
                        .get("/users?page=" + page)
                        .then()
                        .spec(responseSpec200)
                        .extract().as(GetUsersListResponseModel.class));

        step("Check response", () -> {
            assertNotNull(response.getSupport(), "Support should not be null");
            assertEquals(6, response.getUserData().size());
            assertEquals(page, response.getPage());
            assertNotNull(response.getUserData().get(0).getEmail(), "Email should not be null");
            assertNotNull(response.getUserData().get(1).getFirstName(), "First name should not be null");
        });
    }
}