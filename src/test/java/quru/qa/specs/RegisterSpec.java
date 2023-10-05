package quru.qa.specs;

import io.restassured.specification.RequestLogSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import static quru.qa.helpers.CustomAllureListener.withCustomTemplates;

public class RegisterSpec {
    public static RequestLogSpecification registerRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .contentType(JSON)
            .basePath("/register");
}
