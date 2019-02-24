package app.service;

import app.api.User;
import app.data.UserEntity;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity findOrRegister(User user) {
        UserEntity authorEntity = null;
        if (user.getId() != null) {
            authorEntity = get(user.getId());
        } else {
            List<UserEntity> users = userRepository.findByFirstNameAndLastNameAllIgnoreCase(user.getFirstName(), user.getLastName());
            if (users.isEmpty()) {
                authorEntity = userRepository.save(new UserEntity(user.getFirstName(), user.getLastName()));
            } else {
                authorEntity = users.get(0);
            }
        }
        return authorEntity;
    }

    public UserEntity get(Long userId) {
        return  userRepository.findById(userId).
                orElseThrow(() -> new IllegalArgumentException("Can't find user with id: " + userId));
    }
}
