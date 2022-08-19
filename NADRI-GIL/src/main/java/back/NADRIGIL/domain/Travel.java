package back.NADRIGIL.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Travel extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "travel_id")
    private Long id;

    private String image;

    private String name;

    private String location;

    private String address;

    @ColumnDefault("0")
    private String like_count;

    private String info;

    private double latitude;

    private double longitude;

    private String category;

    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL)      //한번에 저장한게 cascade all 해줌
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL)      // cascade all
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "travel")
    private List<CourseOrder> courseOrders = new ArrayList<>();

    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL)      // cascade all
    private List<Cart> carts = new ArrayList<>();
}
