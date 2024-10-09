package api;

import api.client.User;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserLoginNegativeTest extends BaseTest {
    private User user;
    private final String email = "abc";
    private final String name = "1";

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    @Description("Login attempt with invalid credentials")
    public void incorrectUser() {
        login();
    }

    @Step("Login")
    void login() {
        user.login(email, name)
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"))
                .statusCode(401);

    }
}