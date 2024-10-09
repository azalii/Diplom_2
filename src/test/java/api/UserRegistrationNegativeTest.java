package api;

import api.client.User;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class UserRegistrationNegativeTest extends BaseTest {
    private User user;
    private final String email;
    private final String password;
    private final String name;

    public UserRegistrationNegativeTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"azali@yandex.ru", null, null},
                {null, "123", null},
                {null, null, "123"},
                {null, null, null},
        };
    }

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    @Description("Attempt to register a user without required fields")
    public void withoutRequiredFields() {
        register();
    }

    @Step("Registration a user")
    void register() {
        user.register(email, password, name)
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("Email, password and name are required fields"))
                .statusCode(403);
    }
}
