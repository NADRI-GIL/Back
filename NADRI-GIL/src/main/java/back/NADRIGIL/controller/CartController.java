package back.NADRIGIL.controller;

import back.NADRIGIL.domain.BaseResponseBody;
import back.NADRIGIL.domain.CustomResponseBody;
import back.NADRIGIL.dto.cart.AddCartDTO;
import back.NADRIGIL.dto.cart.GetMyCartListDTO;
import back.NADRIGIL.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * 장바구니 추가
     * @param addCartDTO
     * @return
     */
    @PostMapping("/carts/add")
    public ResponseEntity<BaseResponseBody> addCart(@RequestBody AddCartDTO addCartDTO){
        BaseResponseBody responseBody = new BaseResponseBody("장바구니 담기 성공");
        try{
            cartService.addCart(addCartDTO);
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

    /**
     * 로그인된 사용자의 장바구니 리스트 불러오기
     * @param loginIdMap
     * @return
     */
    @PostMapping("/carts/myList")
    public ResponseEntity<CustomResponseBody<GetMyCartListDTO>> MyCartList(@RequestBody Map<String, String> loginIdMap) {
        CustomResponseBody<GetMyCartListDTO> responseBody = new CustomResponseBody<>("내 장바구니 리스트 불러오기 성공");
        try {
            List<GetMyCartListDTO> myCarts = cartService.getMyCartList(loginIdMap.get("loginId"));
            responseBody.setList(myCarts);

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
