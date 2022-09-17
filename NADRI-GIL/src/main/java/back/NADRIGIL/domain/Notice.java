package back.NADRIGIL.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@SequenceGenerator(
        name = "NOTICE_SEQ_GENERATOR",
        initialValue = 1, allocationSize = 1)
@Getter @Setter
public class Notice {

    @Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NOTICE_SEQ_GENERATOR")
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate registerDate;

    private String content;

    private String tag;
}
