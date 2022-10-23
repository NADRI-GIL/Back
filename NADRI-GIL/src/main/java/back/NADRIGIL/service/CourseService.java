package back.NADRIGIL.service;

import back.NADRIGIL.domain.*;
import back.NADRIGIL.dto.cart.GetMyCartListDTO;
import back.NADRIGIL.dto.course.*;
import back.NADRIGIL.repository.CourseRepository;
import back.NADRIGIL.repository.TravelRepository;
import back.NADRIGIL.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public GetCourseDetailDTO getCourseDetail(Long courseId) {
        GetCourseDetailDTO getCourseDetailDTO = new GetCourseDetailDTO();
        List<GetCourseTravelDetailDTO> getCourseTravelDetailDTOS = new ArrayList<>();

        Course course = courseRepository.findOne(courseId);
        List<CourseOrder> courseOrders = courseRepository.findCourseOrderList(courseId);

        getCourseDetailDTO.setCourseName(course.getName());

        for (CourseOrder courseOrder : courseOrders) {
            GetCourseTravelDetailDTO getCourseTravelDetailDTO = new GetCourseTravelDetailDTO();
            Travel travel = courseOrder.getTravel();
            getCourseTravelDetailDTO.setTravelId(travel.getId());
            getCourseTravelDetailDTO.setName(travel.getName());
            getCourseTravelDetailDTO.setImage(travel.getImage());
            getCourseTravelDetailDTO.setLatitude(travel.getLatitude());
            getCourseTravelDetailDTO.setLongitude(travel.getLongitude());
            getCourseTravelDetailDTOS.add(getCourseTravelDetailDTO);
        }
        getCourseDetailDTO.setCourseTravels(getCourseTravelDetailDTOS);

        return getCourseDetailDTO;

    }

    public List<GetMyCourseListDTO> getMyCourseList(String loginId) {
        List<GetMyCourseListDTO> result = new ArrayList<>();
        List<User> user = userRepository.findByLoginId(loginId);
        if (user.isEmpty()) {
            throw new IllegalStateException("로그인이 필요한 기능입니다.");
        }
        List<Course> myCourses = courseRepository.findMyCourseList(user.get(0).getId());
        for(Course myCourse : myCourses){
            GetMyCourseListDTO course = new GetMyCourseListDTO();
            course.setId(myCourse.getId());
            course.setName(myCourse.getName());
            List<GetMyCourseTravelsDTO> travels = new ArrayList<>();
            List<CourseOrder> courseOrders = courseRepository.findCourseOrderList(myCourse.getId());
            for (CourseOrder courseOrder : courseOrders) {
                GetMyCourseTravelsDTO travel = new GetMyCourseTravelsDTO();
                travel.setTravelId(courseOrder.getTravel().getId());
                travel.setTravelName(courseOrder.getTravel().getName());
                travels.add(travel);
            }
            course.setCourseTravels(travels);
            result.add(course);
        }
        return result;
    }
}
