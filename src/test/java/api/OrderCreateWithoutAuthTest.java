package api;

import api.client.Ingredient;
import api.client.Order;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderCreateWithoutAuthTest extends BaseTest {
    private Order order;

    @Before
    public void setUp() {
        order = new Order();
    }

    @Test
    @Description("Order creation attempt without authorized user")
    public void createOrderWithoutAuth() {
        List<String> ingredients = getIngredients();
        create(ingredients);
    }

    @Step("Create")
    void create(List<String> ingredients) {
        order.create(null, ingredients)
                .assertThat()
                .body("success", equalTo(true))
                .body("name", notNullValue())
                .body("order", notNullValue())
                .body("order.number", notNullValue());
    }

    @Step("Get list of ingredients")
    List<String> getIngredients() {
        List<String> ingredients = new ArrayList<>();

        ValidatableResponse vr = Ingredient.getList()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));

        ingredients.add(vr.extract().path("data[0]._id").toString());
        ingredients.add(vr.extract().path("data[1]._id").toString());

        return ingredients;
    }
}