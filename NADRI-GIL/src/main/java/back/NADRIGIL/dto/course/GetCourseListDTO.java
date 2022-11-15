package back.NADRIGIL.dto.course;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GetCourseListDTO {

    private Long id;
    private String name;
    private boolean is_shared;
    private String writerLoginId;
    private String writerNickname;
    private List<GetCourseTravelsDTO> courseTravels;

}
