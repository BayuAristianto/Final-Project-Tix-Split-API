package tests.splitBillTest;

import base.UtilsTest;
import body.payloadEvent.UpdateEventBody;
import body.payloadSplitBill.UpdateSplitBillBody;
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

public class UpdateSplitBillTest {
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

    @Test(priority = 1, description = "Update splitbill by id dengan response status code 200 OK - Success")
    public void UpdateSplitBillById() throws Exception{
        UpdateSplitBillBody updateSplitBillBody = new UpdateSplitBillBody();
        JSONObject requestBody = updateSplitBillBody.getBodyUpdateSplitBill(Utils.generateRandomTitle());
        System.out.println("Payload yang dikirim: " + requestBody.toString(4));
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(requestBody.toString())
                .when()
                .put("/api/tools/split-bill/" + ConfigReader.getProperty("UpdateSplitBillById"))
                .then()
                .extract().response();
        System.out.println("Response: " + response.asString());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 2, description = "Failed update splitbill by id dengan response status code 404 Split bill not found - Success")
    public void UpdateSplitBillFailedById() throws Exception{
        UpdateSplitBillBody updateSplitBillBody = new UpdateSplitBillBody();
        JSONObject requestBody = updateSplitBillBody.getBodyUpdateSplitBill(Utils.generateRandomTitle());
        System.out.println("Payload yang dikirim: " + requestBody.toString(4));
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(requestBody.toString())
                .when()
                .put("/api/tools/split-bill/" + ConfigReader.getProperty("splitBillInvalidId"))
                .then()
                .extract().response();
        System.out.println("Response: " + response.asString());
        String actualErrorMessage = UtilsTest.getErrorMessage(response);
        Assert.assertEquals(actualErrorMessage, "Split bill not found");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test(priority = 3, description = "Failed update splitbill dengan role member (no edit access) dengan response status code 403 Forbidden - Success")
    public void UpdateSplitBillNoEditAccessRole() throws Exception{
        UpdateSplitBillBody updateSplitBillBody = new UpdateSplitBillBody();
        JSONObject requestBody = updateSplitBillBody.getBodyUpdateSplitBill(Utils.generateRandomTitle());
        System.out.println("Payload yang dikirim: " + requestBody.toString(4));
        Response response = given()
                .header("Authorization", "Bearer " + tokenMember)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(requestBody.toString())
                .when()
                .put("/api/tools/split-bill/" + ConfigReader.getProperty("UpdateSplitBillById"))
                .then()
                .extract().response();
        System.out.println("Response: " + response.asString());
        String actualErrorMessage = UtilsTest.getErrorMessage(response);
        Assert.assertEquals(actualErrorMessage, "Forbidden");
        Assert.assertEquals(response.getStatusCode(), 403);
    }

    @Test(priority = 4, description = "Failed update splitbill dengan detail exceeds total dengan response status code 400 Detail sum exceeds total for \"Makan Malam\". Confirm mismatch to save. - Success")
    public void UpdateSplitBillDetailExceedsTotal() throws Exception{
        UpdateSplitBillBody updateSplitBillBody = new UpdateSplitBillBody();
        JSONObject requestBody = updateSplitBillBody.getBodyUpdateSplitBillInvalidAmount(Utils.generateRandomTitle());
        System.out.println("Payload yang dikirim: " + requestBody.toString(4));
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(requestBody.toString())
                .when()
                .put("/api/tools/split-bill/" + ConfigReader.getProperty("UpdateSplitBillById"))
                .then()
                .extract().response();
        System.out.println("Response: " + response.asString());
        String actualErrorMessage = UtilsTest.getErrorMessage(response);
        Assert.assertEquals(actualErrorMessage, "Detail sum exceeds total for \"Makan Malam\". Confirm mismatch to save.");
        Assert.assertEquals(response.getStatusCode(), 400);
    }
}
