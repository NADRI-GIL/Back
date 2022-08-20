package back.NADRIGIL.repository;

import back.NADRIGIL.domain.Travel;
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


}
