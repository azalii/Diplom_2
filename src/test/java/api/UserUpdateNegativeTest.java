package api;

import api.client.User;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserUpdateNegativeTest extends BaseTest {
    private User user;
    private final String email = "azali@yandex.ru";
    private final String name = "123";

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    @Description("Failed user update")
    public void failedUserUpdate() {
        update();
    }

    @Step("Update")
    void update() {
        user.update(email + "1", name + "1")
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"))
                .statusCode(401);
    }
}