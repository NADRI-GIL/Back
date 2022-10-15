package back.NADRIGIL.dto.course;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class AddCourseDTO {

    private String loginId;
    private String name;
    private List<AddCourseOrderDTO> courseOrders;
}
