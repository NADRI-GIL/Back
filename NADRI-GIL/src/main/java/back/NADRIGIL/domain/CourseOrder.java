package back.NADRIGIL.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CollectionId;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "COURSE_ORDER_SEQ_GENERATOR",
        initialValue = 1, allocationSize = 1)
@Getter @Setter
public class CourseOrder {

    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COURSE_ORDER_SEQ_GENERATOR")
    @Column(name = "course_order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private Travel travel;

    private int orderNo;
}
