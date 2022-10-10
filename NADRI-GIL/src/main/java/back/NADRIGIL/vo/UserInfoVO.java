package back.NADRIGIL.vo;

import lombok.Getter;

@Getter
public class UserInfoVO {
    private Long id;
    private String loginId;
    private String name;
    private String nickname;

    public UserInfoVO(Long id, String loginId, String name, String nickname) {
        this.id = id;
        this.loginId = loginId;
        this.name = name;
        this.nickname = nickname;
    }
}
