package body.payloadSplitBill;

import org.json.JSONArray;
import org.json.JSONObject;

public class CreateSplitBillBody {

    public JSONObject getBodyCreateSplitBill(String title){
        JSONObject body = new JSONObject();

        body.put("title", title);

        JSONArray participantArray = new JSONArray();
        //=============Participant1=================
        JSONObject participant1 = new JSONObject();
        participant1.put("name", "Natalia");
        participant1.put("userId", "natalia001");
        participant1.put("sortOrder", 0);

        participantArray.put(participant1);
        //=============Participant2=================
        JSONObject participant2 = new JSONObject();
        participant2.put("name", "Lesley");
        participant2.put("userId", "lesley001");
        participant2.put("sortOrder", 1);

        participantArray.put(participant2);
        body.put("participants", participantArray);
        return body;
    }

}
