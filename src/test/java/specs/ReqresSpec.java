package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;

public class ReqresSpec {
    public static RequestSpecification defaultRequestSpec = with()
            .filter(withCustomTemplates())
            .log().all()
            .contentType(JSON);

    public static ResponseSpecification responseSpec(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .log(ALL)
                .build();
    }

    public static ResponseSpecification responseSpec200 = responseSpec(200);
    public static ResponseSpecification responseSpec201 = responseSpec(201);
    public static ResponseSpecification responseSpec204 = responseSpec(204);
    public static ResponseSpecification responseSpec400 = responseSpec(400);
    public static ResponseSpecification responseSpec404 = responseSpec(404);
}