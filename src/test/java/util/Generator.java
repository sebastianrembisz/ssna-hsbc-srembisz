package util;


import app.api.User;
import org.apache.commons.lang3.RandomStringUtils;

public class Generator {
    public static User randomUser() {
        return new User(RandomStringUtils.randomAlphabetic(3, 8), RandomStringUtils.randomAlphabetic(5, 10));
    }
}
