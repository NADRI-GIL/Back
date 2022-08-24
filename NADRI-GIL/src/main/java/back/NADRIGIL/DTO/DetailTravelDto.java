package back.NADRIGIL.DTO;

import back.NADRIGIL.domain.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class DetailTravelDto {

    private Long id;

    private String image;

    private String name;

    private String location;

    private String address;

    private int like_count;

    private String info;

    private double latitude;

    private double longitude;

    private String category;

    private List<Review> reviews = new ArrayList<>();
}
