package back.NADRIGIL.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetMyCartListDTO {

    private Long id;
    private Long travelId;
    private String name;
    private String location;
    private String image;
}
