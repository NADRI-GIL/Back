package back.NADRIGIL.service;

import back.NADRIGIL.domain.User;
import back.NADRIGIL.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;
    @Autowired EntityManager em;

    @Test
    @Rollback(false)    //눈으로 보기위해
    public void 회원가입() throws Exception {
        //given
        User user = new User();
        user.setName("kim");
        user.setBirth("000119");
        user.setEmail("kim@naver.com");
        user.setId("kim123");
        user.setPassword("1234");

        //when
        String saveId = userService.join(user);

        //then
        assertEquals(user, userRepository.findOne(saveId));
        System.out.println("save done!");
    }
     
     @Test
     public void 중복_회원_예외() throws Exception {
         //given
         User user1 = new User();
         user1.setId("kim123");

         User user2 = new User();
         user2.setId("kim123");

         //when
         userService.join(user1);
         try {
             userService.join(user2);   //예외 발생해야함!
         } catch (IllegalStateException e) {
             System.out.println("good test!");
             return;
         }

         //then
         fail("예외가 발생해야 한다.");
      }

      @Test
      @Rollback(false)    //눈으로 보기위해
      public void 비밀번호_찾기() throws Exception {

          //given
          // 찾고 싶은 회원정보
          User user = new User();
          user.setName("kim");
          user.setBirth("000119");
          user.setEmail("kim@naver.com");
          user.setId("kim123");
          user.setPassword("1234");
          String saveId = userService.join(user);



          //when
          // 유저가 입력한 아이디
          String findUserId = "kim123";

          //then
          String findPassword = userService.findPassword(findUserId);
          assertEquals(findPassword, user.getPassword());
          System.out.println(findPassword);
          return;
      }

}