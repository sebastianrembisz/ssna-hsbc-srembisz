package app.data;


import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;

    public List<UserEntity> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UserEntity> followers) {
        this.followers = followers;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "FollowEntity",
            joinColumns = {@JoinColumn(name = "authorId")},
            inverseJoinColumns = {@JoinColumn(name="followerId")})
    private List<UserEntity> followers;

    public UserEntity() {}

    public UserEntity(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "UserEntity[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

}
