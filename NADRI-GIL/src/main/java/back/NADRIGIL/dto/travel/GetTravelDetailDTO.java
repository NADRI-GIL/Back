package back.NADRIGIL.dto.travel;

import back.NADRIGIL.domain.Review;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class GetTravelDetailDTO {

    private Long id;

    private String image;

    private String name;

    private String location;

    private String address;

    private int likeCount;

    private String info;

    private double latitude;

    private double longitude;

    private double reviewTotal;

//    private List<Review> reviews = new ArrayList<>();
}
