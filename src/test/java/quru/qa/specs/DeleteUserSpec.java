package quru.qa.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static quru.qa.helpers.CustomAllureListener.withCustomTemplates;

public class DeleteUserSpec {
    public static RequestSpecification deleteUserRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .contentType(JSON)
            .basePath("/api");

    public static ResponseSpecification deleteUserResponseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(204)
            .build();
}
