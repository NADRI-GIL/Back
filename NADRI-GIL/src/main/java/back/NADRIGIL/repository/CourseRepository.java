package back.NADRIGIL.repository;

import back.NADRIGIL.domain.Cart;
import back.NADRIGIL.domain.Course;
import back.NADRIGIL.domain.CourseOrder;
import back.NADRIGIL.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    /**
     * 코스 id로 조회
     * @param id
     * @return
     */
    public Course findOne(Long id) {
        return em.find(Course.class, id);
    }

    /**
     * 코스 id로 해당 CourseOrder들 리턴
     * @param courseId
     * @return
     */
    public List<CourseOrder> findCourseOrderList(Long courseId) {
        return em.createQuery("select co from CourseOrder co where co.course.id = :courseId order by co.orderNo", CourseOrder.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }

    /**
     * 해당 userId의 코스 리스트 반환
     * @param userId
     * @return
     */
    public List<Course> findMyCourseList(Long userId) {
        return em.createQuery("select c from Course c where c.user.id = :userId", Course.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public List<Course> findSharedCourseList() {
        return em.createQuery("select c from Course c where c.isShared = :true", Course.class)
                .setParameter("true", true)
                .getResultList();
    }

}
