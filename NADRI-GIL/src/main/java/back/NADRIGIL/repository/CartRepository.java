package back.NADRIGIL.repository;

import back.NADRIGIL.domain.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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
}
