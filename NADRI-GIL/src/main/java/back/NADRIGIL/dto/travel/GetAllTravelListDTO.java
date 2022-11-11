package back.NADRIGIL.dto.travel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetAllTravelListDTO {

    private Long id;
    private String name;
    private String location;
    private String image;
    private int likeCount;
    private double reviewTotal;
}
