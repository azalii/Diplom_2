package api;

import api.client.Order;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class OrderCreateWithoutIngredientsTest extends BaseTest {
    private Order order;

    @Before
    public void setUp() {
        order = new Order();
    }

    @Test
    @Description("Order creation without ingredients")
    public void createOrderWithoutIngredients() {
        create(List.of());
    }

    @Step("Create")
    void create(List<String> ingredients) {
        order.create(null, ingredients)
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"))
                .statusCode(400);
    }

}