package back.NADRIGIL.service;

import back.NADRIGIL.domain.Course;
import back.NADRIGIL.domain.CourseOrder;
import back.NADRIGIL.domain.User;
import back.NADRIGIL.dto.course.AddCourseDTO;
import back.NADRIGIL.dto.course.AddCourseOrderDTO;
import back.NADRIGIL.repository.CourseRepository;
import back.NADRIGIL.repository.TravelRepository;
import back.NADRIGIL.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final TravelRepository travelRepository;

    @Transactional
    public void addCourse(AddCourseDTO addCourseDTO) {
        List<User> user = userRepository.findByLoginId(addCourseDTO.getLoginId());
        if (user.isEmpty()) {
            throw new IllegalStateException("로그인이 필요한 기능입니다.");
        }

        Course course = new Course();
        //코스 정보 저장
        course.setUser(user.get(0));
        course.setName(addCourseDTO.getName());
        courseRepository.save(course);

        //코스 순서 저장
        for (AddCourseOrderDTO addCourseOrderDTO : addCourseDTO.getCourseOrders()) {
            CourseOrder courseOrder = new CourseOrder();
            courseOrder.setCourse(course);
            courseOrder.setTravel(travelRepository.findOne(addCourseOrderDTO.getTravelId()));
            courseOrder.setOrderNo(addCourseOrderDTO.getOrderNo());
            courseRepository.saveOrder(courseOrder);

        }


    }
}
