package api;

import api.client.Order;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class OrderCreateWithInvalidHashTest extends BaseTest {
    private Order order;

    @Before
    public void setUp() {
        order = new Order();
    }

    @Test
    @Description("Order creation using invalid hash")
    public void createOrderWithInvalidHash() {
        create(List.of("123", "123"));
    }

    @Step("Create")
    void create(List<String> ingredients) {
        order.create(null, ingredients)
                .assertThat()
                .statusCode(500);
    }

}