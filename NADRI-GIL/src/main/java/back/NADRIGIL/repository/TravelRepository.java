package back.NADRIGIL.repository;

import back.NADRIGIL.domain.Cart;
import back.NADRIGIL.domain.Recommend;
import back.NADRIGIL.domain.Travel;
import back.NADRIGIL.domain.User;
import back.NADRIGIL.dto.travel.GetSearchWordDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TravelRepository {

    private final EntityManager em;

    /**
     * 여행지 저장 (Insert & Update)
     */
    public void save(Travel travel){
        em.persist(travel);
    }

    /**
     * 여행지 인덱스로 검색
     *
     * @parm id 인덱스
     * @return Travel 클래스
     */
    public Travel findOne(Long id){
        return em.find(Travel.class, id);
    }

    public List<Travel> findWord(String word) {
        return em.createQuery("select t from Travel t where t.name like concat('%',:word,'%')", Travel.class)
                .setParameter("word", word)
                .getResultList();
    }

    /**
     * 상세페이지 여행지 id로 유사한 여행지들 가져오기
     * @param originTravel
     * @return
     */
    public List<Recommend> findSimilarity(Long originTravel) {
        return em.createQuery("select r from Recommend r where r.originTravel = :originTravel", Recommend.class)
                .setParameter("originTravel", originTravel)
                .getResultList();
    }

    /**
     * 랜덤 여행지 정보 가져오기
     *
     * @return 랜덤 여행지 리스트
     */
    public List<Travel> findRandom() {
        return em.createNativeQuery("select * from travel order by rand() limit 20", Travel.class)
                .getResultList();       //native sql
    }

    /**
     * 모든 여행지 정보 가져오기
     * @return 모든 여행지 리스트
     */
    public List<Travel> findAll() {
        return em.createQuery("select t from Travel t", Travel.class)
                .getResultList();
    }

    /**
     * 찜 수 많은 여행지 50개 가져오기
     * @return
     */
    public List<Travel> findHot() {
        return em.createQuery("select t from Travel t order by t.likeCount desc ", Travel.class)
                .setMaxResults(50)
                .getResultList();       //native sql
    }

    /**
     * 리뷰 별점 높은 여행지 50개 가져오기
     * @return
     */
    public List<Travel> findGoodReview() {
        return em.createQuery("select t from Travel t order by t.reviewTotal desc ", Travel.class)
                .setMaxResults(50)
                .getResultList();       //native sql
    }
}
