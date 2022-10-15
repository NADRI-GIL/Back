package back.NADRIGIL.controller;

import back.NADRIGIL.domain.User;
import back.NADRIGIL.dto.user.LoginDTO;
import back.NADRIGIL.dto.user.SignUpDTO;
import back.NADRIGIL.domain.BaseResponseBody;
import back.NADRIGIL.service.UserService;
import back.NADRIGIL.vo.UserInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    @GetMapping("/users/loginId")
    public ResponseEntity<BaseResponseBody> validateDuplicateLoginId (@RequestParam(value = "loginId") String loginId){
        BaseResponseBody responseBody = new BaseResponseBody("사용가능한 아이디입니다.");
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

    /**
     * 닉네임 중복 검사
     * @param nickname
     * @return
     */
    @GetMapping("/users/nickname")
    public ResponseEntity<BaseResponseBody> validateDuplicateNickname(@RequestParam(value = "nickname") String nickname) {
        BaseResponseBody responseBody = new BaseResponseBody("사용가능한 닉네임입니다.");
        try{
            userService.validateDuplicateNickname(nickname);
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
     * 로그인
     * @param loginDto
     * @param request
     * @return
     */
    @PostMapping("/users/login")
    public ResponseEntity<BaseResponseBody> login(@RequestBody LoginDTO loginDto, HttpServletRequest request, HttpServletResponse response) {
        BaseResponseBody responseBody = new BaseResponseBody("로그인 성공");
        try{
            User loginUser = userService.login(loginDto);
            if (loginUser == null) {
                throw new IllegalStateException("로그인 실패");
            }
            HttpSession session = request.getSession();
            UserInfoVO userInfo = new UserInfoVO(loginUser.getId(), loginUser.getLoginId(), loginUser.getName(), loginUser.getNickname());
            session.setAttribute("userInfo",userInfo);

            Cookie idCookie = new Cookie("userId", String.valueOf(userInfo.getLoginId()));
            idCookie.setHttpOnly(false);
            idCookie.setPath("/");
            response.addCookie(idCookie);

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
