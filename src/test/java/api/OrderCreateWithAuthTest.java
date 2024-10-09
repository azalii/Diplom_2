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

public class OrderCreateWithAuthTest extends BaseTest {
    private User user;
    private final String email = "azali@yandex.ru";
    private final String password = "123";
    private final String name = "123";

    private Order order;

    @Before
    public void setUp() {
        user = new User();
        register();
        order = new Order();
    }

    @Test
    @Description("Successful order creation with authorisation")
    public void createOrderWithAuth() {
        List<String> ingredients = getIngredients();
        create(ingredients);
    }

    @After
    public void tearDown() {
        delete();
    }

    @Step("Registration")
    void register() {
        user.register(email, password, name)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Step("Create")
    void create(List<String> ingredients) {
        order.create(user, ingredients)
                .assertThat()
                .body("success", equalTo(true))
                .body("name", notNullValue())
                .body("order", notNullValue())
                .body("order.ingredients.size()", equalTo(2))
                .body("order.owner", notNullValue())
                .body("order.owner.name", equalTo(name))
                .body("order.owner.email", equalTo(email));
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
}