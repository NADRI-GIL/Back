package back.NADRIGIL.controller;

import back.NADRIGIL.domain.BaseResponseBody;
import back.NADRIGIL.domain.CustomResponseBody;
import back.NADRIGIL.dto.cart.AddCartDTO;
import back.NADRIGIL.dto.cart.GetMyCartListDTO;
import back.NADRIGIL.dto.heart.AddHeartDTO;
import back.NADRIGIL.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    /**
     * 찜 추가 or 삭제
     * @param addHeartDTO
     * @return
     */
    @PostMapping("/hearts/add")
    public ResponseEntity<BaseResponseBody> addHeart(@RequestBody AddHeartDTO addHeartDTO){
        BaseResponseBody responseBody = new BaseResponseBody();
        try{
            if (heartService.addHeart(addHeartDTO)) {
                responseBody.setResultMsg("찜 삭제 성공");
            } else {
                responseBody.setResultMsg("찜 추가 성공");
            }

        } catch (IllegalStateException e){
            responseBody.setResultCode(-1);
            responseBody.setResultMsg(e.getMessage());
            return ResponseEntity.badRequest().body(responseBody);
        } catch (Exception e){
            responseBody.setResultCode(-2);
            responseBody.setResultMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
        return ResponseEntity.ok().body(responseBody);
    }

}
