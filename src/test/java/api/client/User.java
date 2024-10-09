package api.client;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class User {
    private static final String REGISTER_URL = "/api/auth/register";
    private static final String LOGIN_URL = "/api/auth/login";
    private static final String UPDATE_URL = "/api/auth/user";
    private static final String DELETE_URL = "/api/auth/user";

    private String accessToken;

    public ValidatableResponse register(String email, String password, String name) {
        JSONObject jo = new JSONObject();
        jo.put("email", email);
        jo.put("password", password);
        jo.put("name", name);

        ValidatableResponse vr = given()
                .log().all()
                .header("Content-type", "application/json")
                .body(jo.toString())
                .when()
                .post(REGISTER_URL)
                .then()
                .log().all();

        if (vr.extract().path("accessToken") != null) {
            accessToken = vr.extract().path("accessToken").toString();
        }

        return vr;
    }

    public ValidatableResponse login(String email, String password) {
        JSONObject jo = new JSONObject();
        jo.put("email", email);
        jo.put("password", password);

        ValidatableResponse vr = given()
                .log().all()
                .header("Content-type", "application/json")
                .body(jo.toString())
                .when()
                .post(LOGIN_URL)
                .then()
                .log().all();

        if (vr.extract().path("accessToken") != null) {
            accessToken = vr.extract().path("accessToken").toString();
        }

        return vr;
    }

    public ValidatableResponse update(String email, String name) {
        JSONObject jo = new JSONObject();
        jo.put("email", email);
        jo.put("name", name);

        RequestSpecification rs = given()
                .log().all()
                .header("Content-type", "application/json")
                .body(jo.toString());

        if (accessToken != null) {
            rs.header("Authorization", accessToken);
        }

        return rs
                .when()
                .patch(UPDATE_URL)
                .then()
                .log().all();

    }

    public ValidatableResponse delete() {
        RequestSpecification rs = given()
                .log().all()
                .header("Content-type", "application/json");

        if (accessToken != null) {
            rs.header("Authorization", accessToken);
        }

        return rs
                .when()
                .delete(DELETE_URL)
                .then()
                .log().all();
    }

    public String getAccessToken() {
        return accessToken;
    }
}