package back.NADRIGIL.repository;

import back.NADRIGIL.domain.Cart;
import back.NADRIGIL.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartRepository {

    private final EntityManager em;

    /**
     * 장바구니 추가
     * @param cart
     */
    public void add(Cart cart) {
        em.persist(cart);
    }

    /**
     * 해당 loginId, travelId와 같은 장바구니 반환
     * @param loginId
     * @param travelId
     * @return
     */
    public List<Cart> findCart(String loginId, Long travelId) {
        return em.createQuery("select c from Cart c where c.user.loginId = :loginId and c.travel.id = :travelId", Cart.class)
                .setParameter("loginId", loginId)
                .setParameter("travelId", travelId)
                .getResultList();
    }

    /**
     * 해당 userId의 장바구니 리스트 반환
     * @param userId
     * @return
     */
    public List<Cart> getMyCartList(Long userId) {
        return em.createQuery("select c from Cart c where c.user.id = :userId", Cart.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
