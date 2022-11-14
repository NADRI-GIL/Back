package back.NADRIGIL.dto.review;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @Setter
public class GetMyReviewListDTO {
    private Long id;
    private int star;
    private String content;
    private String image;
    private Long travelId;
    private String travelName;
    private String createdDate;

    public String changeLocalDaeTime(LocalDateTime localDateTime) {
        String localDateTimeFormat2
                = localDateTime.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
        return localDateTimeFormat2;
    }
}
