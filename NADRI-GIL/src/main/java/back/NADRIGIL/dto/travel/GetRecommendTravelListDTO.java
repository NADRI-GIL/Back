package back.NADRIGIL.dto.travel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetRecommendTravelListDTO {

    private Long id;
    private String name;
    private String location;
    private String image;
    private int similarity;
}
