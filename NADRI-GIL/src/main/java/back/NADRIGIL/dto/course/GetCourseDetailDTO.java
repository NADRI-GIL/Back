package back.NADRIGIL.dto.course;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GetCourseDetailDTO {

    private String courseName;
    private String courseContent;
    private String nickName;
    private boolean is_shared;
    private List<GetCourseTravelDetailDTO> courseTravels;
}
