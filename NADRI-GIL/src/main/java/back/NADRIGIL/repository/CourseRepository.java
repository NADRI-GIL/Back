package back.NADRIGIL.repository;

import back.NADRIGIL.domain.Course;
import back.NADRIGIL.domain.CourseOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CourseRepository {

    private final EntityManager em;

    /**
     * 코스 저장 (Insert & Update)
     */
    public void save(Course course) {
        em.persist(course);
    }

    /**
     * 코스 순서 저장
     * @param courseOrder
     */
    public void saveOrder(CourseOrder courseOrder) {
        em.persist(courseOrder);
    }

}
