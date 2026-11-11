package tests.splitBillTest;

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

public class GetSplitBillTest {
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

    @Test(priority = 1, description = "Get splitbill by id dengan response status code 200 OK - Success")
    public void GetSplitBillById() throws IOException {

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .get("/api/tools/split-bill/" + ConfigReader.getProperty("splitBillId"))
                .then()
                .extract().response();

        System.out.println("Response: " + response.asString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2, description = "Get list splitbill by event id dengan response status code 200 OK - Success")
    public void GetSplitBillList() throws IOException {

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .get("/api/tools/split-bill/from-event/"+ ConfigReader.getProperty("eventId"))
                .then()
                .extract().response();

        System.out.println("Response: " + response.asString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3, description = "Failed get splitbill by event id dengan response status code 404 Split bill not found - Success")
    public void GetSplitBillFailedById() throws IOException {

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .get("/api/tools/split-bill/" + ConfigReader.getProperty("eventInvalidId"))
                .then()
                .extract().response();

        System.out.println("Response: " + response.asString());
        String actualErrorMessage = UtilsTest.getErrorMessage(response);
        Assert.assertEquals(actualErrorMessage, "Split bill not found");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test(priority = 4, groups = {"API SplitBill"}, description = "Failed get list splitbill by event id dengan response status code 404 Event not found - success")
    public void GetSplitBillFailedList() throws IOException {

        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .get("/api/tools/split-bill/from-event/"+ ConfigReader.getProperty("eventInvalidId"))
                .then()
                .extract().response();

        System.out.println("Response: " + response.asString());
        String actualErrorMessage = UtilsTest.getErrorMessage(response);
        Assert.assertEquals(actualErrorMessage, "Event not found");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

}
