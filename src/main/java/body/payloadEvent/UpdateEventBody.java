package body.payloadEvent;

import org.json.JSONObject;
import utils.Utils;

public class UpdateEventBody {
    public JSONObject getBodyUpdateEvent(String name) {
        JSONObject body = new JSONObject();

        body.put("name", name);
        body.put("category", "Mukbang");
        body.put("description", "JOSJIS BOLOOOO");
        body.put("date", Utils.getDateAfterSevenDays());
        body.put("time", "21:00");
        body.put("location", "DEPAN SD SONO");
        return body;
    }

    public JSONObject getBodyUpdateEventInvalid(String name) {
        JSONObject body = new JSONObject();

        body.put("name", name);
        body.put("category", "Mukbang");
        body.put("description", "JOSJIS BOLOOOO");
        body.put("date", Utils.getDateAfterSevenDays());
        body.put("time", "21:00");
        body.put("location", "DEPAN SD SONO");
        return body;
    }
}
