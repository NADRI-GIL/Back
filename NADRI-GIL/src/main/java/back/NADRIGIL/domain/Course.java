package back.NADRIGIL.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@SequenceGenerator(
        name = "COURSE_SEQ_GENERATOR",
        initialValue = 1, allocationSize = 1)
@Getter @Setter
public class Course extends BaseTimeEntity {

    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COURSE_SEQ_GENERATOR")
    @Column(name = "course_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    private String content;

    @ColumnDefault("0")
    private boolean isShared;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)      // cascade all
    private List<CourseOrder> courseOrders = new ArrayList<>();
}
