package back.NADRIGIL.vo;

import lombok.Getter;

@Getter
public class UserInfoVO {
    private Long id;
    private String loginId;
    private String name;

    public UserInfoVO(Long id, String loginId, String name) {
        this.id = id;
        this.loginId = loginId;
        this.name = name;
    }
}
