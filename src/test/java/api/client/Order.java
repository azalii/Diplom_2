package api.client;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.util.List;

import static io.restassured.RestAssured.given;


public class Order {
    private static final String BASE_URL = "/api/orders";
    private User user;

    public ValidatableResponse create(User user, List<String> ingredients) {
        this.user = user;

        JSONObject jo = new JSONObject();
        jo.put("ingredients", ingredients);

        RequestSpecification rs = given()
                .log().all()
                .header("Content-type", "application/json")
                .body(jo.toString());

        if (this.user != null && user.getAccessToken() != null) {
            rs.header("Authorization", user.getAccessToken());
        }

        return rs.
                when()
                .post(BASE_URL)
                .then()
                .log().all();
    }

    public static ValidatableResponse getList(User user) {
        RequestSpecification rs = given()
                .log().all()
                .header("Content-type", "application/json");

        if (user != null && user.getAccessToken() != null) {
            rs.header("Authorization", user.getAccessToken());
        }

        return rs.
                when()
                .get(BASE_URL)
                .then()
                .log().all();
    }
}