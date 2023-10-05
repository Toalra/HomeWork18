package quru.qa.tests;

import org.junit.jupiter.api.Test;
import quru.qa.TestBase;
import quru.qa.in.reqres.models.LoginBodyModel;
import quru.qa.in.reqres.models.LoginResponseModel;


import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusTests extends TestBase {

    @Test
    void successRegister() {
        LoginBodyModel regBody = new LoginBodyModel();
        regBody.setEmail("eve.holt@reqres.in");
        regBody.setPassword("pistol");

        LoginResponseModel response = given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(regBody)
                .post("/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .extract().as(LoginResponseModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    void successLogin() {
        String regBody = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}";
        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(regBody)
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void successCreateUsers() {
        String regBody = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";
        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(regBody)
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"))
                .body("id", notNullValue());
    }
    @Test
    void successUpdateUsers() {
        String regBody = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";
        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(regBody)
                .post("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }
    @Test
    void successDeleteUsers() {
        given()
                .log().uri()
                .contentType(JSON)
                .post("/users/2")
                .then()
                .log().status()
                .statusCode(201);
    }
}
