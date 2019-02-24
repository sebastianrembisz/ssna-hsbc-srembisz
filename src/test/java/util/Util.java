package util;

import app.api.Post;
import app.api.PostRequest;
import app.api.User;
import io.restassured.specification.RequestSpecification;

import static app.api.Builder.newPost;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static io.restassured.RestAssured.put;
import static util.Generator.randomUser;

public class Util {
    public static User registerRandomUser(){
        User user = randomUser();
        RequestSpecification specification = given().contentType("application/json").body(user);
        Long userId = specification.post("/user").as(Long.class);
        user.setId(userId);

        return user;
    }

    public static void addFollower(User author, User follower) {
        put("/{followerId}/follows/{authorId}", follower.getId(), author.getId());
    }

    public static Post postMessage(User author, String message) {
        PostRequest post = newPost(author.getId(), message);
        RequestSpecification specification = given().contentType("application/json").body(post);
        return specification.post("/post/new").as(Post.class);
    }
}
