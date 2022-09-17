package back.NADRIGIL.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(
        name = "USER_SEQ_GENERATOR",
        initialValue = 1, allocationSize = 1)
@Getter @Setter
public class User extends BaseTimeEntity{

    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_SEQ_GENERATOR")
    @Column(name="user_id")
    private Long id;

    private String loginId;

    private String password;

    private String name;

    private String email;

    @ColumnDefault("0")
    private boolean isDeleted;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Notice> notices = new ArrayList<>();
}
