package back.NADRIGIL.controller;

import back.NADRIGIL.DTO.SignUpDTO;
import back.NADRIGIL.domain.BaseResponseBody;
import back.NADRIGIL.domain.CustomResponseBody;
import back.NADRIGIL.domain.User;
import back.NADRIGIL.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 유저 저장
     * @param signupDTO
     * @return
     */
    @PostMapping("/users/signUp")
    public ResponseEntity<BaseResponseBody> signup (@RequestBody SignUpDTO signupDTO){
        BaseResponseBody responseBody = new BaseResponseBody("회원가입 성공");
        try {
            userService.signUp(signupDTO);
        } catch (IllegalStateException e){
            responseBody.setResultCode(-1);
            responseBody.setResultMsg(e.getMessage());
            return ResponseEntity.ok().body(responseBody);
        } catch (Exception e){
            responseBody.setResultCode(-2);
            responseBody.setResultMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
        return ResponseEntity.ok().body(responseBody);
    }

    /**
     * 로그인 아이디 중복 검사
     * @param loginId
     * @return
     */
    @GetMapping("/user/loginId")
    public ResponseEntity<CustomResponseBody<User>> validateDuplicateLoginId (@RequestParam(value = "loginId") String loginId){
        CustomResponseBody<User> responseBody = new CustomResponseBody<>("사용가능한 닉네임입니다.");
        try{
            userService.validateDuplicateUser(loginId);
        } catch (IllegalStateException e){
            responseBody.setResultCode(-1);
            responseBody.setResultMsg(e.getMessage());
            return ResponseEntity.ok().body(responseBody);
        } catch (Exception e){
            responseBody.setResultCode(-2);
            responseBody.setResultMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
        return ResponseEntity.ok().body(responseBody);
    }
}
