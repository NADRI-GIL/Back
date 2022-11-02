package back.NADRIGIL.repository;

import back.NADRIGIL.domain.Cart;
import back.NADRIGIL.domain.Heart;
import back.NADRIGIL.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {

    private final EntityManager em;

    /**
     * 리뷰 저장
     * @param review
     */
    public void save(Review review) {
        em.persist(review);
    }

    /**
     * 해당 여행지에 쓴 리뷰 개수 불러오기
     * @param travelId
     * @return
     */
    public int findReviewCount(Long travelId) {
        List<Review> reviews = new ArrayList<>();
        reviews = em.createQuery("select r from Review r where r.travel.id = :travelId", Review.class)
                .setParameter("travelId", travelId)
                .getResultList();
        return reviews.size();
    }
}
