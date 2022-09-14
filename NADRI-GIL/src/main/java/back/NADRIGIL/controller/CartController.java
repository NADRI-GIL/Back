package back.NADRIGIL.controller;

import back.NADRIGIL.domain.BaseResponseBody;
import back.NADRIGIL.dto.CartInfoDTO;
import back.NADRIGIL.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * 장바구니 추가
     * @param cartInfoDTO
     * @return
     */
    @PostMapping("/carts/add")
    public ResponseEntity<BaseResponseBody> addCart(@RequestBody CartInfoDTO cartInfoDTO){
        BaseResponseBody responseBody = new BaseResponseBody("장바구니 담기 성공");
        try{
            cartService.addCart(cartInfoDTO);

        } catch (RuntimeException re){
            responseBody.setResultCode(-1);
            responseBody.setResultMsg(re.getMessage());
            return ResponseEntity.badRequest().body(responseBody);
        } catch (Exception e){
            responseBody.setResultCode(-2);
            responseBody.setResultMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }

        return ResponseEntity.ok().body(responseBody);
    }
}
