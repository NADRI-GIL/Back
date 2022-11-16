package back.NADRIGIL.repository;

import back.NADRIGIL.domain.Cart;
import back.NADRIGIL.domain.Survey;
import back.NADRIGIL.domain.Travel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SurveyRepository {

    private final EntityManager em;

    /**
     * 선호도조사 여행지 추가
     * @param survey
     */
    public void add(Survey survey) {
        em.persist(survey);
    }

    /**
     * 모든 선호도조사 여행지 정보 가져오기
     * @return 모든 여행지 리스트
     */
    public List<Survey> findAll() {
        return em.createQuery("select s from Survey s", Survey.class)
                .getResultList();
    }
}
