package back.NADRIGIL.dto.course;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetCourseTravelDetailDTO {

    private Long travelId;
    private String name;
    private String image;
    private double latitude;
    private double longitude;
    private String address;
}
