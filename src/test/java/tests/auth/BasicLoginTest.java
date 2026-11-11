package tests.auth;

import body.auth.LoginBody;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class BasicLoginTest {

    @Test
    public void loginOwner() throws IOException {
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        LoginBody loginBody = new LoginBody();

        String requestBody = loginBody.loginData().toString();

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/auth/login")
                .then()
                .extract().response();
        System.out.println("Response: " + response.asString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void loginMember() throws IOException {
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        LoginBody loginBody = new LoginBody();

        String requestBody = loginBody.loginDataMember().toString();

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/auth/login")
                .then()
                .extract().response();
        String token = response.jsonPath().getString("token");
        System.out.println("Response: " + response.asString());
        System.out.println("Token: " + token);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

}
