package api.client;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class Ingredient {
    private static final String GET_URL = "/api/ingredients";

    public static ValidatableResponse getList() {
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .when()
                .get(GET_URL)
                .then()
                .log().all();
    }
}