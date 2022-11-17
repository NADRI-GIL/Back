package back.NADRIGIL.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "RECOMMEND_SEQ_GENERATOR",
        initialValue = 1, allocationSize = 1)
@Setter @Getter
public class Recommend {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="RECOMMEND_SEQ_GENERATOR")
    @Column(name = "recommend_id")
    private Long id;
    private Long originTravel;
    private Long recommendTravel;
    private int similarity;

}
