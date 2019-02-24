package app.rest;


import app.api.Post;
import app.api.PostRequest;
import app.api.User;
import app.service.FollowService;
import app.service.PostService;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ApiController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @RequestMapping(value = "/post/new", method = RequestMethod.POST)
    public Post post(@Valid @RequestBody PostRequest postRequest) {
       return postService.post(postRequest.getUser(), postRequest.getMessage());
    }
    @RequestMapping(value = "/wall/{authorId}")
    public List<Post> wall(@PathVariable("authorId") Long authorId) {
        return postService.wall(authorId);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Long getUserId(@RequestBody User user) {
        return userService.findOrRegister(user).getId();
    }

    @RequestMapping(value = "/{followerId}/follows/{authorId}", method = RequestMethod.PUT)
    public void follow(@PathVariable("followerId") Long followerId, @PathVariable("authorId") Long authorId) {
        followService.follow(followerId, authorId);
    }

    @RequestMapping(value = "/followers/{authorId}")
    public List<User> followers(@PathVariable("authorId") Long authorId) {
        return followService.followers(authorId);
    }

    @RequestMapping(value = "/timeline/{followerId}")
    public List<Post> timeline(@PathVariable("followerId") Long followerId) {
        return postService.timeline(followerId);
    }
}
