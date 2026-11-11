package tests.eventTest;

import base.UtilsTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.io.FileReader;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class GetEventTest {
    private String token;

    @BeforeClass
    public void setup() throws Exception {
        // Set base URI dari config.properties
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        // Baca token dari file src/test/resources/json/token.json
        FileReader reader = new FileReader("src/resources/json/token.json");
        JSONObject tokenJson = new JSONObject(new JSONTokener(reader));
        token = tokenJson.getString("token");
        reader.close();

        System.out.println("Token loaded: " + token);
    }

    @Test(priority = 1, description = "Get list event dengan response status code 200 OK - Success")
    public void GetEventList() throws IOException {

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .get("/api/events" )
                .then()
                .extract().response();

        System.out.println("Response: " + response.asString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2, description = "Get event by id dengan response status code 200 OK - Success")
    public void GetEventById() throws IOException {

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .get("/api/events/" + ConfigReader.getProperty("eventId"))
                .then()
                .extract().response();

        System.out.println("Response: " + response.asString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3, description = "Failed get event by id dengan response status code 404 Event Not Found - Success")
    public void GetEventInvalidId() throws IOException {
        UtilsTest utilsTest = new UtilsTest();
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .get("/api/events/" + ConfigReader.getProperty("eventInvalidId"))
                .then()
                .extract().response();

        String actualErrorMessage = UtilsTest.getErrorMessage(response);
        System.out.println("Response: " + response.asString());
        System.out.println("Errornya ini: " + actualErrorMessage);
        Assert.assertEquals(actualErrorMessage, "Event not found");
        Assert.assertEquals(response.getStatusCode(), 404);
    }
}
