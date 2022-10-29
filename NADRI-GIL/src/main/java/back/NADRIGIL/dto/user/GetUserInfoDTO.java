package back.NADRIGIL.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetUserInfoDTO {

    private Long id;
    private String loginId;
    private String email;
    private String nickname;
}
