package body.auth;

import org.json.JSONObject;
import utils.ConfigReader;

public class LoginBody {
    public JSONObject loginData() {
        JSONObject body = new JSONObject();
        body.put("email", ConfigReader.getProperty("usnOwner"));
        body.put("password", ConfigReader.getProperty("globalPassword"));
        body.put("invitationId","");
        return body;
    }
    public JSONObject loginDataMember() {
        JSONObject body = new JSONObject();
        body.put("email", ConfigReader.getProperty("usnMember"));
        body.put("password", ConfigReader.getProperty("globalPassword"));
        body.put("invitationId","");
        return body;
    }
}
