package app.repository;


import app.data.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    List<UserEntity> findByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);
}
