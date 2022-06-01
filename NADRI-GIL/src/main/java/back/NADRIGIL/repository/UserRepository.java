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

    public User findOne(Long id) {
        return em.find(User.class, id);
    }   //회원 단건 조회

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)   //jpql
                .getResultList();
    }   //회원 리스트 조회

    public List<User> findByName(String name) {
        return em.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }   //이름으로 조회할 때
}
