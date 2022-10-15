package back.NADRIGIL.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDTO {
    private Long id;
    private  String loginId;
    private String password;
}
