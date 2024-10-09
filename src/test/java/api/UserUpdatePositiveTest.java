package api;

import api.client.User;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserUpdatePositiveTest extends BaseTest {
    private User user;
    private final String email = "azali@yandex.ru";
    private final String password = "123";
    private final String name = "123";

    @Before
    public void setUp() {
        user = new User();
        register();
    }

    @Test
    @Description("Successful user update")
    public void successfulUpdate() {
        update();
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

    @Step("Update")
    void update() {
        user.update(email + "1", name + "1")
                .assertThat()
                .body("success", equalTo(true))
                .body("user.email", equalTo(email + "1"))
                .body("user.name", equalTo(name + "1"));
    }

    @Step("Delete")
    void delete() {
        user.delete()
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(202);
    }
}