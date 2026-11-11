package tests.eventTest;

import base.UtilsTest;
import body.payloadEvent.CreateEventBody;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.Utils;

import java.io.FileReader;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class CreateEventTest {

    private String token;
    private String tokenMember;

    @BeforeClass
    public void setup() throws Exception{
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        FileReader reader = new FileReader("src/resources/json/token.json");
        JSONObject tokenJson = new JSONObject(new org.json.JSONTokener(reader));
        token = tokenJson.getString("token");
        reader.close();

        FileReader readerMember = new FileReader("src/resources/json/tokenMember.json");
        JSONObject tokenJsonMember = new JSONObject(new org.json.JSONTokener(readerMember));
        tokenMember = tokenJsonMember.getString("token");
        readerMember.close();
    }

    @Test(priority = 1, description = "Create event data valid dengan response status code 201 Created - Success")
    public void CreateEventSuccess() throws IOException {
        CreateEventBody createEventBody = new CreateEventBody();
        JSONObject requestBody = createEventBody.getBodyCreateEventValid(
                Utils.generateRandomTitle(),Utils.generateRandomCategory(),Utils.generateRandomDescription());

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(requestBody.toString())
                .when()
                .post("/api/communities/" +ConfigReader.getProperty("slugQAClub")+"/events")
                .then()
                .extract().response();

        System.out.println("Response: " + response.asString());
        Assert.assertEquals(response.getStatusCode(), 201);
    }

    @Test(priority = 2, description = "Create event invalid data dengan response status code 400 Bad Request - Success")
    public void CreateEventInvalidData() throws IOException {
        CreateEventBody createEventBody = new CreateEventBody();
        JSONObject requestBody = createEventBody.getBodyCreateEventInvalid(Utils.generateRandomCategory(),Utils.generateRandomDescription());

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(requestBody.toString())
                .when()
                .post("/api/communities/" +ConfigReader.getProperty("slugQAClub")+"/events")
                .then()
                .extract().response();

        System.out.println("Response: " + response.asString());
        String actualErrorMessage = UtilsTest.getErrorMessage(response);
        Assert.assertEquals(actualErrorMessage, "name, category, description, date, time, and location are required");
        Assert.assertEquals(response.getStatusCode(), 400);
    }

    @Test(priority = 3, description = "Failed create event dengan role member dengan response status code 403 Owner or admin only - Success")
    public void CreateEventRoleMember() throws IOException {
        CreateEventBody createEventBody = new CreateEventBody();
        JSONObject requestBody = createEventBody.getBodyCreateEventValid(
                Utils.generateRandomTitle(),Utils.generateRandomCategory(),Utils.generateRandomDescription());

        Response response = given()
                .header("Authorization", "Bearer " + tokenMember)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(requestBody.toString())
                .when()
                .post("/api/communities/" +ConfigReader.getProperty("slugQAClub")+"/events")
                .then()
                .extract().response();

        System.out.println("Response: " + response.asString());
        System.out.println("Status Code: " + response.statusCode());

        String actualErrorMessage = UtilsTest.getErrorMessage(response);
        Assert.assertEquals(actualErrorMessage, "Not an active community member");
        Assert.assertEquals(response.getStatusCode(), 403);
    }
}
