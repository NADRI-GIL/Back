package back.NADRIGIL.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BaseResponseBody {
    protected int resultCode;
    protected String resultMsg;

    public BaseResponseBody(){}
    public BaseResponseBody(String resultMsg) {
        this.resultCode = 0;
        this.resultMsg = resultMsg;
    }
}
