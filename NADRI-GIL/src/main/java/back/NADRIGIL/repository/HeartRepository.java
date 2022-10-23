package back.NADRIGIL.repository;

import back.NADRIGIL.domain.Cart;
import back.NADRIGIL.domain.Heart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HeartRepository {

    private final EntityManager em;

    /**
     * 찜 추가
     * @param heart
     */
    public void add(Heart heart) {
        em.persist(heart);
    }

    /**
     * 찜 삭제
     * @param heart
     */
    public void delete(Heart heart) {
        em.remove(heart);
    }

    /**
     * 해당 loginId, travelId와 같은 찜 반환
     * @param loginId
     * @param travelId
     * @return
     */
    public List<Heart> findHeart(String loginId, Long travelId) {
        return em.createQuery("select h from Heart h where h.user.loginId = :loginId and h.travel.id = :travelId", Heart.class)
                .setParameter("loginId", loginId)
                .setParameter("travelId", travelId)
                .getResultList();
    }

}
