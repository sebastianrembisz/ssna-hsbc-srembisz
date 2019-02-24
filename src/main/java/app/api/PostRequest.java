package app.api;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostRequest {
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @NotNull(message = "Post cannot be null")
    @Size(min = 1, max = 140,message = "Post lenght must be between 1 and 140 characters")
    private String message;
}
