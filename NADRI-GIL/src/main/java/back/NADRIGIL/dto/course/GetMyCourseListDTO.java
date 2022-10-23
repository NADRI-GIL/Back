package back.NADRIGIL.dto.course;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GetMyCourseListDTO {

    private Long id;
    private String name;
    private List<GetMyCourseTravelsDTO> courseTravels;

}
