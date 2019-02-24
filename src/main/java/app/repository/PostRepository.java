package app.repository;

import app.data.PostEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface PostRepository extends CrudRepository<PostEntity, Long> {
    List<PostEntity> findByAuthorIdOrderByCreationTimeDesc(Long authorId);

    @Query(value = "SELECT p.* FROM Post_Entity p join Follow_Entity f on p.author_id=f.author_id WHERE f.follower_id = :followerId order by p.creation_time DESC",
            nativeQuery = true)
    List<PostEntity> findTimeLine(@Param("followerId") Long followerId);
}
