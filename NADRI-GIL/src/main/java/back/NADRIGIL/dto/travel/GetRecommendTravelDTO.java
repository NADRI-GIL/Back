package back.NADRIGIL.dto.travel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetRecommendTravelDTO {

    private Long id;
    private String name;
    private String image;
    private String location;
    private int likeCount;
    private double reviewTotal;
}
