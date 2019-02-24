package app.service;

import app.api.Post;
import app.api.User;
import app.data.FollowEntity;
import app.data.PostEntity;
import app.data.UserEntity;
import app.repository.FollowRepository;
import app.repository.PostRepository;
import app.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowService {
    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserService userService;

    @Autowired
    ModelMapper modelMapper;

    public void follow(Long followerId, Long authorId) {
        followRepository.save(new FollowEntity(followerId, authorId));
    }

    public List<User> followers(Long authorId) {
        return userService.get(authorId).getFollowers().
                stream().map(userEntity -> modelMapper.map(userEntity, User.class)).collect(Collectors.toList());
    }
}
