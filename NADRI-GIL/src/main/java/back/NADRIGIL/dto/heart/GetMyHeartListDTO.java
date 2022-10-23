package back.NADRIGIL.dto.heart;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetMyHeartListDTO {

    private Long id;
    private Long travelId;
    private String name;
    private String location;
    private String image;
}
