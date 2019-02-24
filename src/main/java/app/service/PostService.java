package app.service;

import app.api.Post;
import app.api.User;
import app.data.PostEntity;
import app.data.UserEntity;
import app.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired
    ModelMapper modelMapper;

    public Post post(User author, String message) {
        UserEntity authorEntity = userService.findOrRegister(author);
        PostEntity newPost = new PostEntity(authorEntity.getId(), message);
        newPost.setCreationTime(LocalDateTime.now());
        newPost = postRepository.save(newPost);

        return modelMapper.map(newPost, Post.class);
    }

    public List<Post> wall(Long authorId) {
        List<PostEntity> posts = postRepository.findByAuthorIdOrderByCreationTimeDesc(authorId);
        return posts.stream()
                .map(post -> modelMapper.map(post, Post.class))
                .collect(Collectors.toList());
    }

    public List<Post> timeline(Long followerId) {
        List<PostEntity> posts = postRepository.findTimeLine(followerId);
        return posts.stream()
                .map(post -> modelMapper.map(post, Post.class))
                .collect(Collectors.toList());
    }
}
