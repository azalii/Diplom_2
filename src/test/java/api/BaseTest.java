package api;

import io.restassured.RestAssured;

public class BaseTest {
    public BaseTest() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }
}