package back.NADRIGIL.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDTO {
    private Long id;
    private  String loginId;
    private String password;
}
