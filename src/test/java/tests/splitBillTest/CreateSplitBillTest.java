package tests.splitBillTest;

import body.payloadEvent.CreateEventBody;
import body.payloadSplitBill.CreateSplitBillBody;
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

public class CreateSplitBillTest {
    private String token;

    @BeforeClass
    public void setup() throws Exception{
        RestAssured.baseURI = ConfigReader.getProperty("baseUrl");

        FileReader reader = new FileReader("src/resources/json/token.json");
        JSONObject tokenJson = new JSONObject(new org.json.JSONTokener(reader));
        token = tokenJson.getString("token");
        reader.close();
    }

    @Test(priority = 1, description = "Create splitbill dengan response status code 201 Created - Success")
    public void CreateSplitBillTest() throws IOException {
        CreateSplitBillBody createSplitBillBody = new CreateSplitBillBody();
        JSONObject requestBody = createSplitBillBody.getBodyCreateSplitBill(Utils.generateRandomTitle());
        System.out.println("Payload yang dikirim: " + requestBody.toString(4));
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(requestBody.toString())
                .when()
                .post("/api/tools/split-bill")
                .then()
                .extract().response();

        System.out.println("Response: " + response.asString());
        Assert.assertEquals(response.getStatusCode(), 201);
    }
}
