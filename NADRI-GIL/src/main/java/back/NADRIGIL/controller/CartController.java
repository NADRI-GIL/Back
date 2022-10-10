package back.NADRIGIL.controller;

import back.NADRIGIL.domain.BaseResponseBody;
import back.NADRIGIL.domain.CustomResponseBody;
import back.NADRIGIL.domain.User;
import back.NADRIGIL.dto.AllTravelListDTO;
import back.NADRIGIL.dto.CartInfoDTO;
import back.NADRIGIL.dto.MyCartListDTO;
import back.NADRIGIL.service.CartService;
import back.NADRIGIL.vo.UserInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * 장바구니 추가
     * @param userInfo
     * @param travelId
     * @return
     */
    @GetMapping("/carts/add/{travelId}")
    public ResponseEntity<BaseResponseBody> addCart(
            @SessionAttribute(name = "userInfo", required = false) UserInfoVO userInfo
            ,@PathVariable(name = "travelId") Long travelId){
        BaseResponseBody responseBody = new BaseResponseBody("장바구니 담기 성공");
        try{
            if(userInfo == null){
                responseBody.setResultCode(-10000);
                responseBody.setResultMsg("로그인이 필요한 기능입니다.");
                return ResponseEntity.badRequest().body(responseBody);
            }
            CartInfoDTO cartInfoDTO = new CartInfoDTO();
            cartInfoDTO.setUserId(userInfo.getId());
            cartInfoDTO.setTravelId(travelId);
            cartService.addCart(cartInfoDTO);
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
     * @param userInfo
     * @return
     */
    @GetMapping("/carts/myList")
    public ResponseEntity<CustomResponseBody<MyCartListDTO>> MyCartList(
            @SessionAttribute(name = "userInfo", required = false) UserInfoVO userInfo) {
        CustomResponseBody<MyCartListDTO> responseBody = new CustomResponseBody<>("내 장바구니 리스트 불러오기 성공");
        try {
            if(userInfo == null){
                responseBody.setResultCode(-10000);
                responseBody.setResultMsg("로그인이 필요한 기능입니다.");
                return ResponseEntity.badRequest().body(responseBody);
            }
            List<MyCartListDTO> myCarts = cartService.findMyCartList(userInfo.getId());
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
