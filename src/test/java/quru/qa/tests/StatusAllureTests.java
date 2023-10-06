package quru.qa.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.Test;
import quru.qa.TestBase;
import quru.qa.in.reqres.models.*;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusAllureTests extends TestBase {

    @Test
    void successAllureRegister() {
        RegisterBodyLombokModel regBody = new RegisterBodyLombokModel();
        regBody.setEmail("eve.holt@reqres.in");
        regBody.setPassword("pistol");

        RegisterResponseLombokModel response = step("Register request", () ->
            given()
                    .filter(new AllureRestAssured())
                    .log().uri()
                    .log().body()
                    .contentType(JSON)
                    .body(regBody)
                    .post("/register")
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .extract().as(RegisterResponseLombokModel.class));
        step("Register verify", () -> {
                    assertEquals(4, response.getId());
                    assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        });
    }

    @Test
    void successLogin() {
        LoginBodyLombokModel regBody = new LoginBodyLombokModel();
        regBody.setEmail("eve.holt@reqres.in");
        regBody.setPassword("cityslicka");

        LoginResponseLombokModel loginResponse = step("Register request", () ->
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
                .extract().as(LoginResponseLombokModel.class));

        step("Login verify", () ->
                assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken()));
    }

    @Test
    void successCreateUsers() {
        CreateUserBodyLombokModel regBody = new CreateUserBodyLombokModel();
        regBody.setName("morpheus");
        regBody.setJob("leader");

        CreateUserResponseLombokModel userCreateResponse = step("User create request", () ->

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
                .extract().as(CreateUserResponseLombokModel.class));

        step("Create user verify", () -> {
                assertEquals("morpheus", userCreateResponse.getName());
                assertEquals("leader", userCreateResponse.getJob());
                assertEquals(notNullValue(), userCreateResponse.getId());
        });

    }
    @Test
    void successUpdateUsers() {
        UpdateUserBodyLombokModel regBody = new UpdateUserBodyLombokModel();
        regBody.setName("morpheus");
        regBody.setJob("zion resident");

        UpdateUserResponseLombokModel userUpdateResponse = step("User update request", () ->
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
                .extract().as(UpdateUserResponseLombokModel.class));
        step("Update user verify", () -> {
                    assertEquals("morpheus", userUpdateResponse.getName());
                    assertEquals("zion resident", userUpdateResponse.getJob());
        });

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
