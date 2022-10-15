package back.NADRIGIL.dto.course;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CourseAddDTO {

    private String loginId;
    private String name;
    private List<CourseOrderDTO> courseOrders;
}
