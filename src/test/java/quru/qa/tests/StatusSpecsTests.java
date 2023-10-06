package quru.qa.tests;

import org.junit.jupiter.api.Test;
import quru.qa.TestBase;
import quru.qa.in.reqres.models.*;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static quru.qa.specs.CreateUserSpec.createUserRequestSpec;
import static quru.qa.specs.CreateUserSpec.createUserResponseSpec;
import static quru.qa.specs.DeleteUserSpec.deleteUserRequestSpec;
import static quru.qa.specs.DeleteUserSpec.deleteUserResponseSpec;
import static quru.qa.specs.LoginSpec.loginRequestSpec;
import static quru.qa.specs.LoginSpec.loginResponseSpec;
import static quru.qa.specs.RegisterSpec.registerRequestSpec;
import static quru.qa.specs.RegisterSpec.registerResponseSpec;
import static quru.qa.specs.UpdateUserSpec.updateUserRequestSpec;
import static quru.qa.specs.UpdateUserSpec.updateUserResponseSpec;

public class StatusSpecsTests extends TestBase {

    @Test
    void successAllureRegister() {
        RegisterBodyLombokModel regBody = new RegisterBodyLombokModel();
        regBody.setEmail("eve.holt@reqres.in");
        regBody.setPassword("pistol");

        RegisterResponseLombokModel response = step("Register request", () ->
            given(registerRequestSpec)
                    .body(regBody)
                    .post("/register")
                    .then()
                    .spec(registerResponseSpec)
                    .extract().as(RegisterResponseLombokModel.class));
        step("Register verify", () -> {
                    assertEquals("4", response.getId());
                    assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        });
    }

    @Test
    void successLogin() {
        LoginBodyLombokModel regBody = new LoginBodyLombokModel();
        regBody.setEmail("eve.holt@reqres.in");
        regBody.setPassword("cityslicka");

        LoginResponseLombokModel loginResponse = step("Login request", () ->
        given(loginRequestSpec)
                .body(regBody)
                .post("/login")
                .then()
                .spec(loginResponseSpec)
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
        given(createUserRequestSpec)
                .body(regBody)
                .post("/users")
                .then()
                .spec(createUserResponseSpec)
                .extract().as(CreateUserResponseLombokModel.class));

        step("Create user verify", () -> {
                assertEquals("morpheus", userCreateResponse.getName());
                assertEquals("leader", userCreateResponse.getJob());
                assertNotNull(userCreateResponse.getId());
                assertNotNull(userCreateResponse.getCreatedAt());
        });
    }
    @Test
    void successUpdateUsers() {
        UpdateUserBodyLombokModel updateUser = new UpdateUserBodyLombokModel();
        updateUser.setName("morpheus");
        updateUser.setJob("zion resident");

        UpdateUserResponseLombokModel userUpdateResponse = step("User update request", () ->
        given(updateUserRequestSpec)
                .body(updateUser)
                .when()
                .put("/users/2")
                .then()
                .spec(updateUserResponseSpec)
                .extract().as(UpdateUserResponseLombokModel.class));

        step("Update user verify", () -> {
                assertEquals("morpheus", userUpdateResponse.getName());
                assertEquals("zion resident", userUpdateResponse.getJob());
                assertNotNull(userUpdateResponse.getUpdatedAt());
        });
    }
    @Test
    void successDeleteUsers() {//
        step("Delete user", () -> {
            given(deleteUserRequestSpec)
                    .delete("/users/2")
                    .then()
                    .spec(deleteUserResponseSpec);
        });
    }
}
