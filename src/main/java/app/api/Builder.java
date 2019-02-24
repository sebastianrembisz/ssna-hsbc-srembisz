package app.api;


public class Builder {
    public static PostRequest newPost(Long authorId, String message) {
        PostRequest postRequest = new PostRequest();
        postRequest.setUser(new User(authorId));
        postRequest.setMessage(message);
        return postRequest;
    }

    public static PostRequest newPost(String authorFirstName, String authorLastName, String message) {
        PostRequest postRequest = new PostRequest();
        postRequest.setUser(new User(authorFirstName, authorLastName));
        postRequest.setMessage(message);
        return postRequest;
    }
}
