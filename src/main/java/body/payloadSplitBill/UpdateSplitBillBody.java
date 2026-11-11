package body.payloadSplitBill;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.ConfigReader;

public class UpdateSplitBillBody {
    //    cmw6m8drt0005jq04skb5nz23
    //cmw78zptg0004lb04jotr4oml
    public JSONObject getBodyUpdateSplitBill(String title) {
        JSONObject body = new JSONObject();

        body.put("title", title);
        body.put("settlementMode", "MINIMIZE");

        JSONArray participantsArray = new JSONArray();

        //==============Participant1=================
        JSONObject participant1 = new JSONObject();
        participant1.put("id", ConfigReader.getProperty("nataliaId"));
        participant1.put("name", "Natalia");
        participant1.put("userId", JSONObject.NULL);
        participant1.put("sortOrder", 0);
        participant1.put("settled", false);
        participant1.put("transferNote", JSONObject.NULL);
        participantsArray.put(participant1);

        //==============Participant2=================
        JSONObject participant2 = new JSONObject();
        participant2.put("id", ConfigReader.getProperty("lesleyId"));
        participant2.put("name", "Lesley");
        participant2.put("userId", JSONObject.NULL);
        participant2.put("sortOrder", 1);
        participant2.put("settled", false);
        participant2.put("transferNote", JSONObject.NULL);
        participantsArray.put(participant2);

        body.put("participants", participantsArray);

        JSONArray expensesArray = new JSONArray();
        JSONObject expense1 = new JSONObject();

        expense1.put("id", ConfigReader.getProperty("expenseId"));
        expense1.put("name", "Makan Malam");
        expense1.put("totalAmount", 200000);
        expense1.put("payerParticipantId", ConfigReader.getProperty("nataliaId"));
        expense1.put("taxMode", "NONE");
        expense1.put("forceSaveMismatch", false);
        expense1.put("sortOrder", 0);

        JSONArray lineItemsArray = new JSONArray();

        JSONObject lineItem1 = new JSONObject();
        lineItem1.put("participantId", ConfigReader.getProperty("nataliaId"));
        lineItem1.put("amount", 100000);
//        item1.put("paid", false);
        lineItem1.put("note", "Bagi rata");
        lineItemsArray.put(lineItem1);

        // Item 2
        JSONObject lineItem2 = new JSONObject();
        lineItem2.put("participantId", ConfigReader.getProperty("lesleyId"));
        lineItem2.put("amount", 100000);
//        item2.put("paid", false);
        lineItem2.put("note", "Bagi rata");
        lineItemsArray.put(lineItem2);

        expense1.put("lineItems", lineItemsArray);

        expensesArray.put(expense1);

        body.put("expenses", expensesArray);

        return body;
    }

    public JSONObject getBodyUpdateSplitBillInvalidAmount(String title) {
        JSONObject body = new JSONObject();

        body.put("title", title);
        body.put("settlementMode", "MINIMIZE");

        JSONArray participantsArray = new JSONArray();

        //==============Participant1=================
        JSONObject participant1 = new JSONObject();
        participant1.put("id", ConfigReader.getProperty("nataliaId"));
        participant1.put("name", "Natalia");
        participant1.put("userId", JSONObject.NULL);
        participant1.put("sortOrder", 0);
        participant1.put("settled", false);
        participant1.put("transferNote", JSONObject.NULL);
        participantsArray.put(participant1);

        //==============Participant2=================
        JSONObject participant2 = new JSONObject();
        participant2.put("id", ConfigReader.getProperty("lesleyId"));
        participant2.put("name", "Lesley");
        participant2.put("userId", JSONObject.NULL);
        participant2.put("sortOrder", 1);
        participant2.put("settled", false);
        participant2.put("transferNote", JSONObject.NULL);
        participantsArray.put(participant2);

        body.put("participants", participantsArray);

        JSONArray expensesArray = new JSONArray();
        JSONObject expense1 = new JSONObject();

        expense1.put("id", ConfigReader.getProperty("expenseId"));
        expense1.put("name", "Makan Malam");
        expense1.put("totalAmount", 100000);
        expense1.put("payerParticipantId", ConfigReader.getProperty("nataliaId"));
        expense1.put("taxMode", "NONE");
        expense1.put("forceSaveMismatch", false);
        expense1.put("sortOrder", 0);

        JSONArray lineItemsArray = new JSONArray();

        JSONObject lineItem1 = new JSONObject();
        lineItem1.put("participantId", ConfigReader.getProperty("nataliaId"));
        lineItem1.put("amount", 100000);
//        item1.put("paid", false);
        lineItem1.put("note", "Bagi rata");
        lineItemsArray.put(lineItem1);

        // Item 2
        JSONObject lineItem2 = new JSONObject();
        lineItem2.put("participantId", ConfigReader.getProperty("lesleyId"));
        lineItem2.put("amount", 100000);
//        item2.put("paid", false);
        lineItem2.put("note", "Bagi rata");
        lineItemsArray.put(lineItem2);

        expense1.put("lineItems", lineItemsArray);

        expensesArray.put(expense1);

        body.put("expenses", expensesArray);

        return body;
    }



}
