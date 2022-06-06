package back.NADRIGIL.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {

    @Id
    @Column(name="user_id")
    private String id;

    //private String makeId;

    private String password;

    private String name;

    private String birth;

    private String email;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Notice> notices = new ArrayList<>();
}
