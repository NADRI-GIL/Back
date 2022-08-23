package back.NADRIGIL.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomResponseBody<T> extends BaseResponseBody{
    private List<T> list;

    public CustomResponseBody() {
        this.list = new ArrayList<>();
    }


    public CustomResponseBody(String resultMsg) {
        this.resultCode = 0;
        this.resultMsg = resultMsg;
        this.list = new ArrayList<>();
    }
}