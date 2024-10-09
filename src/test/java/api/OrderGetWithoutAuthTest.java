package api;

import api.client.Order;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class OrderGetWithoutAuthTest extends BaseTest {
    @Test
    @Description("Get orders without authorisation")
    public void getOrdersWithoutAuth() {
        getList();
    }

    @Step("Get orders")
    void getList() {
        Order.getList(null)
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"))
                .statusCode(401);
    }
}