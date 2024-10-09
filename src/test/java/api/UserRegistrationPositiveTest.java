package api;

import api.client.User;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserRegistrationPositiveTest extends BaseTest {
    private User user;
    private final String email = "azali@yandex.ru";
    private final String password = "123";
    private final String name = "123";

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    @Description("Successful user registration")
    public void registerUser() {
        register();
    }

    @After
    public void tearDown() {
        delete();
    }

    @Step("Registration a user")
    void register() {
        user.register(email, password, name)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Step("Delete user")
    void delete() {
        user.delete()
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(202);
    }
}