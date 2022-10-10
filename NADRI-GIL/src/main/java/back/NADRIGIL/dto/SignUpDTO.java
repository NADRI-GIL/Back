package back.NADRIGIL.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignUpDTO {
    private Long id;

    private String loginId;

    private String password;

    private String name;

    private String email;

    private String nickname;
}
