package back.NADRIGIL.service;

import back.NADRIGIL.dto.user.LoginDTO;
import back.NADRIGIL.dto.user.SignUpDTO;
import back.NADRIGIL.domain.User;
import back.NADRIGIL.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원가입
     * @param signUpDTO
     */
    @Transactional  //쓰기도 가능함 readonly 안해서
    public void signUp(SignUpDTO signUpDTO) {
        User user = new User();
        if (signUpDTO.getLoginId().isBlank()) {
            throw new IllegalStateException("아이디를 입력해주세요.");
        }
        validateDuplicateUser(signUpDTO.getLoginId());  //중복 회원 검증
        if (signUpDTO.getPassword().isBlank()) {
            throw new IllegalStateException("비밀번호를 입력해주세요.");
        }
        if (signUpDTO.getName().isBlank()) {
            throw new IllegalStateException("이름을 입력해주세요.");
        }
        if (signUpDTO.getNickname().isBlank()) {
            throw new IllegalStateException("닉네임을 입력해주세요.");
        }
        validateDuplicateNickname(signUpDTO.getNickname());  //중복 닉네임 검증
        if (!isValidEmailAddress(signUpDTO.getEmail())) {
            throw new IllegalStateException("이메일 형식이 유효하지 않습니다.");
        }
        // 유저 정보 저장
        user.setLoginId(signUpDTO.getLoginId());
        user.setPassword(getEncryptPassword(signUpDTO.getPassword()));
        user.setName(signUpDTO.getName());
        user.setEmail(signUpDTO.getEmail());
        user.setNickname(signUpDTO.getNickname());

        userRepository.save(user);
    }

    /**
     * 로그인 아이디 중복검사
     * @param loginId
     */
    public void validateDuplicateUser(String loginId) {
        List<User> findUsers = userRepository.findByLoginId(loginId);
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 닉네임 중복검사
     * @param nickname
     */
    public void validateDuplicateNickname(String nickname) {
        List<User> findUsers = userRepository.findByNickname(nickname);
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }

    /**
     * 이메일 형식 검사
     *
     * @param email
     * @return
     */
    public static boolean isValidEmailAddress(String email) {
        boolean result = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            result = true;
        }
        return result;
    }

    /**
     * 비밀번호 암호화
     *
     * @param password
     * @return 암호화된 비밀번호
     */
    private String getEncryptPassword(String password) {
        MessageDigest md = null;
        String result = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] encryptedPassword = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : encryptedPassword) {
                sb.append(String.format("%02x", b));
            }
            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (result == null) {
            throw new IllegalStateException("비밀번호 암호화 오류입니다.");
        }
        return result;
    }

    /**
     * 로그인
     * @param loginDto
     * @return 로그인 된 유저 클래스
     */
    public User login(LoginDTO loginDto) {
        List<User> findUsers = userRepository.findByLoginId(loginDto.getLoginId());
        if (findUsers.isEmpty()){
            throw new IllegalStateException("존재하지 않는 아이디 입니다.");
        }
        String inputPassword = getEncryptPassword(loginDto.getPassword());
        String findPassword = findUsers.get(0).getPassword();
        if (!inputPassword.equals(findPassword)) {
            throw new IllegalStateException("비밀번호가 틀렸습니다.");
        }
        return findUsers.get(0);
    }

//    //회원 전체 조회
//    public List<User> findUsers() {
//        return userRepository.findAll();
//    }
//
//    //단건 조회
//    public User findOne(String userId) {
//        return userRepository.findOne(userId);
//    }
//
//    //비밀번호 찾기
//    public String findPassword(String userId) {
//        User user = userRepository.findOne(userId);
//        if (user==null) {
//            throw new IllegalStateException("해당 아이디는 존재하지 않습니다.");
//        }
//
//        return user.getPassword();
//    }
}
