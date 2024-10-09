package api;

import api.client.User;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.notNullValue;

import static org.hamcrest.Matchers.equalTo;

public class UserLoginPositiveTest extends BaseTest {
    private User user;
    private final String email = "azali@yandex.ru";
    private final String password = "123";
    private final String name = "123";

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    @Description("Successful login of user")
    public void loginUser() {
        register();
        login();
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

    @Step("Login")
    void login() {
        user.login(email, name)
                .assertThat()
                .body("user", notNullValue())
                .statusCode(200);
    }


    @Step("Delete")
    void delete() {
        user.delete()
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(202);
    }
}