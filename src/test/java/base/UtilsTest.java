package base;

import io.restassured.response.Response;

public class UtilsTest {
    public static String getErrorMessage(Response response) {
        return response.jsonPath().getString("error");
    }
}
