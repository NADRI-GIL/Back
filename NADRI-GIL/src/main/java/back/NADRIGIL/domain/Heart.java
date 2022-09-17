package back.NADRIGIL.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "HEART_SEQ_GENERATOR",
        initialValue = 1, allocationSize = 1)
@Setter @Getter
public class Heart {

    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HEART_SEQ_GENERATOR")
    @Column(name = "heart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private Travel travel;

    private Boolean isDeleted;
}
