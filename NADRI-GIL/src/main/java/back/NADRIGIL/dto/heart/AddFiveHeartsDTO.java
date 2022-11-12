package back.NADRIGIL.dto.heart;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class AddFiveHeartsDTO {

    private String loginId;
    private List<Long> travelIds;
}
