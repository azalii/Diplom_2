package api;

import api.client.User;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;


import static org.hamcrest.Matchers.equalTo;

public class UserRegistrationAlreadyExistTest extends BaseTest {
    private User user;
    private final String email = "azali+10@yandex.ru";
    private final String password = "qweqwe";
    private final String name = "123";

    @Before
    public void setUp() {
        user = new User();
        register();
    }

    @Test
    @Description("Verify that user registration fails for an already existing")
    public void alreadyExistTest() {
        registerAlreadyExist();
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

    @Step("Attempt to register with an existing user")
    void registerAlreadyExist() {
        user.register(email, password, name)
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"))
                .statusCode(403);
    }

    @Step("Delete user")
    void delete() {
        user.delete()
                .assertThat()
                .body("success", equalTo(true))
                .statusCode(202);
    }
}
