package back.NADRIGIL.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Travel {

    @Id @GeneratedValue
    @Column(name = "travel_id")
    private Long id;

    private String name;

    private String info;

    private String location;

    @ManyToMany(mappedBy = "travels")
    private List<Category> categories = new ArrayList<>();

    //private float[] coordinate;

    @OneToMany(mappedBy = "travel")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "travel")
    private List<Like> likes = new ArrayList<>();
}
