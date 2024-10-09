package api;

import api.client.Ingredient;
import api.client.Order;
import api.client.User;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderGetWithAuthTest extends BaseTest {
    private User user;
    private final String email = "azali@yandex.ru";
    private final String password = "123";
    private final String name = "123";

    @Before
    public void setUp() {
        user = new User();
        register();
        List<String> ingredients = getIngredients();
        create(new Order(), ingredients);
        create(new Order(), ingredients);
    }

    @Test
    @Description("Get orders with authorisation")
    public void getOrdersWithAuth() {
        getList();
    }

    @After
    public void tearDown() {
        delete();
    }

    @Step("Get orders")
    void getList() {
        Order.getList(user)
                .assertThat()
                .body("success", equalTo(true))
                .body("orders.size()", equalTo(2))
                .statusCode(200);
    }

    @Step("Registration")
    void register() {
        user.register(email, password, name)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Step("Delete")
    void delete() {
        user.delete()
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(202);
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

    @Step("Create")
    void create(Order order, List<String> ingredients) {
        order.create(user, ingredients)
                .assertThat()
                .body("success", equalTo(true))
                .body("name", notNullValue())
                .body("order", notNullValue())
                .body("order.number", notNullValue());
    }
}