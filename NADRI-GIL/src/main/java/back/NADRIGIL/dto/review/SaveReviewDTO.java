package back.NADRIGIL.dto.review;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaveReviewDTO {

    private String loginId;
    private Long travelId;
    private int star;
    private String content;
    private String image;
}
