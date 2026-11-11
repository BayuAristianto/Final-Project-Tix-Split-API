package tests.eventTest;

import base.UtilsTest;
import body.payloadEvent.UpdateEventBody;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.Utils;

import java.io.FileReader;

import static io.restassured.RestAssured.given;

public class UpdateEventTest {
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

    @Test(priority = 1, description = "Update data event dengan response status code 200 OK - Success")
    public void UpdateEventById() throws Exception{
        UpdateEventBody  updateEventBody = new UpdateEventBody();

        JSONObject requestBody = updateEventBody.getBodyUpdateEvent(Utils.generateRandomTitle());
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(requestBody.toString())
                .when()
                .patch("/api/events/" + ConfigReader.getProperty("updateEventById"))
                .then()
                .extract().response();
        System.out.println("Response: " + response.asString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2, groups = {"API Event"}, description = "Failed update data event dengan response status code 403 Only the event creator or community admin can edit this event - Success")
    public void UpdateEventFailedById() throws Exception{
        UpdateEventBody  updateEventBody = new UpdateEventBody();

        JSONObject requestBody = updateEventBody.getBodyUpdateEvent(Utils.generateRandomTitle());
        Response response = given()
                .header("Authorization", "Bearer " + tokenMember)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(requestBody.toString())
                .when()
                .patch("/api/events/" + ConfigReader.getProperty("updateEventById"))
                .then()
                .extract().response();
        System.out.println("Response: " + response.asString());
        String actualErrorMessage = UtilsTest.getErrorMessage(response);
        Assert.assertEquals(actualErrorMessage, "Only the event creator or community admin can edit this event");
        Assert.assertEquals(response.getStatusCode(), 403);
    }
}
