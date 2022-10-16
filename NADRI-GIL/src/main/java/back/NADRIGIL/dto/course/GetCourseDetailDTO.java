package back.NADRIGIL.dto.course;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GetCourseDetailDTO {

    private String courseName;
    private List<GetCourseTravelDetailDTO> courseTravels;
}
