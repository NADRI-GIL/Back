package back.NADRIGIL.repository;

import back.NADRIGIL.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    /**
     * 유저 저장
     * @param user
     */
    public void save(User user) {
        em.persist(user);
    }

    /**
     * 유저 id로 조회
     * @param id
     * @return User 클래스
     */
    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    /**
     * 모든 유저 정보 가져오기
     * @return 모든 유저 리스트
     */
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)   //jpql
                .getResultList();
    }

    /**
     * 로그인 아이디로 유저 조회
     * @param loginId
     * @return User 리스트
     */
    public List<User> findByLoginId(String loginId) {
        return em.createQuery("select u from User u where u.loginId = :loginId", User.class)
                .setParameter("loginId", loginId)
                .getResultList();
    }

    /**
     * 닉네임으로 유저 조회
     * @param nickname
     * @return User 리스트
     */
    public List<User> findByNickname(String nickname) {
        return em.createQuery("select u from User u where u.nickname = :nickname", User.class)
                .setParameter("nickname", nickname)
                .getResultList();
    }
}
