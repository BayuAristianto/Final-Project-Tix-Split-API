package body.payloadEvent;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.Utils;

public class CreateEventBody {

    public JSONObject getBodyCreateEventValid(String name, String category, String description) {
        JSONObject body = new JSONObject();

        body.put("name", name);
        body.put("category", category);
        body.put("description", description);
        body.put("date", Utils.getDateAfterSevenDays());
        body.put("time", "18:30");
        body.put("location", "Lapangan Josjis, Jakarta Timur");
        body.put("coverImageUrl", "https://maps.app.goo.gl/h1AV4bfB2cojJMxK7");
        body.put("participantLimit", 4);
        body.put("visibility", "PUBLIC");
        body.put("pricingType", "FREE");
        body.put("priceAmount", 50000);
        body.put("adminFeeBearer", "PARTICIPANT");
        body.put("paymentNotes", "Isi aja sendiri");

        JSONArray paymentMethodsArray = new JSONArray();

        JSONObject paymentDetail = new JSONObject();
        paymentDetail.put("note", "BCA");
        paymentDetail.put("accountNumber", "1234567890");
        paymentDetail.put("qrisImageUrl", "");

        paymentMethodsArray.put(paymentDetail);

        body.put("paymentMethods", paymentMethodsArray);

        return body;
    }

    public JSONObject getBodyCreateEventInvalid(String category, String description) {
        JSONObject body = new JSONObject();

        body.put("name", "");
        body.put("category", category);
        body.put("description", description);
        body.put("date", Utils.getDateAfterSevenDays());
        body.put("time", "18:30");
        body.put("location", "Lapangan Josjis, Jakarta Timur");
        body.put("coverImageUrl", "https://maps.app.goo.gl/h1AV4bfB2cojJMxK7");
        body.put("participantLimit", 4);
        body.put("visibility", "PUBLIC");
        body.put("pricingType", "FREE");
        body.put("priceAmount", 50000);
        body.put("adminFeeBearer", "PARTICIPANT");
        body.put("paymentNotes", "Isi aja sendiri");

        JSONArray paymentMethodsArray = new JSONArray();

        JSONObject paymentDetail = new JSONObject();
        paymentDetail.put("note", "BCA");
        paymentDetail.put("accountNumber", "1234567890");
        paymentDetail.put("qrisImageUrl", "");

        paymentMethodsArray.put(paymentDetail);

        body.put("paymentMethods", paymentMethodsArray);

        return body;
    }
}
