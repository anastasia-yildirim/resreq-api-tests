package tests;

import io.restassured.RestAssured;
import models.users.CreateUpdateUserRequestModel;
import models.users.CreateUpdateUserResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static specs.DefaultReqresSpec.defaultRequestSpec;
import static specs.DefaultReqresSpec.defaultResponseSpec;

public class UsersTests extends TestBase {

    @BeforeAll
    static void prepare() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @DisplayName("Успешное создание пользователя")
    @Test
    void successfulCreateUserTest() {
        CreateUpdateUserRequestModel bodyData = new CreateUpdateUserRequestModel();
        bodyData.setName("Ronald McDonald");
        bodyData.setJob("Entertainment Manager");

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
        CreateUpdateUserRequestModel bodyData = new CreateUpdateUserRequestModel();
        bodyData.setName("Ronald McDonald");
        bodyData.setJob("Chief Entertainment Manager");

        CreateUpdateUserResponseModel response = step("Make request", () ->
                given(defaultRequestSpec)
                        .body(bodyData)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(defaultResponseSpec)
                        .statusCode(200)
                        .extract().as(CreateUpdateUserResponseModel.class));

        step("Check response", () -> {
            assertEquals(bodyData.getName(), response.getName());
            assertEquals(bodyData.getJob(), response.getJob());
        });
    }

    @DisplayName("Пользователь не найден")
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

    //TODO: delete user
    //TODO: successful get single user
    //TODO: successful get list of users
}