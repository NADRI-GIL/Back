package back.NADRIGIL.repository;

import back.NADRIGIL.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Member;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    //@PersistenceContext
    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }   //회원 저장

    public User findOne(String id) {
        return em.find(User.class, id);
    }   //회원 단건 조회

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)   //jpql
                .getResultList();
    }   //회원 리스트 조회

    public List<User> findByID(Long id) {
        return em.createQuery("select u from User u where u.id = :id", User.class)
                .setParameter("id", id)
                .getResultList();
    }   //아이디로 조회할 때

    public List<User> findByEmail(String email) {
        return em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
    }   //이메일로 조회할 때
}
