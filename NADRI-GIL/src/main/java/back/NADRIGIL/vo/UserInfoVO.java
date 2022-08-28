package back.NADRIGIL.vo;

import lombok.Getter;

@Getter
public class UserInfoVO {
    private Long id;
    private String name;

    public UserInfoVO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
