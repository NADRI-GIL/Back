package back.NADRIGIL.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateTravelDto {
    private Long id;

    private String image;

    private String name;

    private String location;

    private String address;

    private String info;

    private double latitude;

    private double longitude;

    private String category;
}
