import app.Application;
import app.api.Post;
import app.api.PostRequest;
import app.api.User;
import io.restassured.RestAssured;
import io.restassured.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.stream.Collectors;

import static app.api.Builder.newPost;
import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static util.Util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleSocialNetworkAppEnd2EndTest {

    @LocalServerPort
    private int port;


    @Test
    public void registerUserOnFirstPost() {
        // given
        String message = "First Post";
        PostRequest postRequest = newPost("Sebastian", "RembiszPost1", message);
        RequestSpecification specification = given().contentType("application/json").body(postRequest);

        //when
        // api for new post sending
        Post post = specification.post("/post/new").as(Post.class);

        // then
        assertThat(post.getMessage()).isEqualTo(message);
        assertThat(post.getAuthorId()).isNotNull();
        assertThat(post.getCreationTime()).isNotNull();
    }

    @Test
    public void maxPostLenght140Characters() {
        // given
        User author = registerRandomUser();

        // when
        PostRequest post = newPost(author.getId(), RandomStringUtils.randomAlphanumeric(141));
        RequestSpecification specification = given().contentType("application/json").body(post);
        Response response = specification.post("/post/new").andReturn();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getBody().print()).contains("Post lenght must be between 1 and 140 characters");
    }

    @Test
    public void findOrRegisterUser() {
        // given
        User author = new User("Sebastian", "Rembisz8999");
        RequestSpecification specification = given().contentType("application/json").body(author);

        // when
        // api for finding or registering user
        Long registerUserId = specification.post("/user").as(Long.class);
        Long existingUserId = specification.post("/user").as(Long.class);

        // then
        assertThat(registerUserId).isNotNull();
        assertThat(existingUserId).isEqualTo(registerUserId);
    }

    @Test
    public void wall() {
        // given
        User author = registerRandomUser();
        Post firstPost = postMessage(author, "First Post");
        Post secondPost = postMessage(author, "Second Post");

        // when
        // api for retrieving author posts
        List<Post> posts = get("/wall/{userId}", author.getId()).as(new TypeRef<List<Post>>() {
        });

        // then
        assertThat(posts.stream().map(p -> p.getMessage()).collect(Collectors.toList())).containsExactly(secondPost.getMessage(), firstPost.getMessage());
    }

    @Test
    public void fallow() {
        // given
        User author = registerRandomUser();
        User follower1 = registerRandomUser();
        User follower2 = registerRandomUser();

        // when
        // api for following of specific author
        put("/{followerId}/follows/{authorId}", follower1.getId(), author.getId());
        put("/{followerId}/follows/{authorId}", follower2.getId(), author.getId());
        // api for retrieving author followers
        List<User> authorFollowers = get("/followers/{authorId}", author.getId()).as(new TypeRef<List<User>>() {});
        List<User> follower1Followers = get("/followers/{authorId}", follower1.getId()).as(new TypeRef<List<User>>() {});

        // then
        assertThat(authorFollowers.stream().map(f -> f.getId()).collect(Collectors.toList())).containsExactlyInAnyOrder(follower1.getId(), follower2.getId());
        // assure that following is not reciprocal
        assertThat(follower1Followers).isEmpty();
    }

    @Test
    public void timeLine() {
        // given
        User author1 = registerRandomUser();
        User author2 = registerRandomUser();
        User follower = registerRandomUser();
        addFollower(author1, follower);
        addFollower(author2, follower);
        postMessage(author1, "FirstMessage of author 1");
        postMessage(author2, "FirstMessage of author 2");
        postMessage(author1, "SecondMessage of author 1");

        // when
        // api for follower timeline
        List<Post> posts = get("/timeline/{followerId}", follower.getId()).as(new TypeRef<List<Post>>() {
        });

        // then
        assertThat(posts.stream().map(post -> post.getMessage()).collect(Collectors.toList()))
                .containsExactly("SecondMessage of author 1", "FirstMessage of author 2", "FirstMessage of author 1");
    }

    @Before
    public void setBaseUri() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";// replace as appropriate
    }
}
