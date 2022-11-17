package back.NADRIGIL.service;

import back.NADRIGIL.domain.*;
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
        course.setContent(addCourseDTO.getContent());
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
        getCourseDetailDTO.setCourseContent(course.getContent());
        getCourseDetailDTO.setNickName(course.getUser().getNickname());
        getCourseDetailDTO.set_shared(course.isShared());

        for (CourseOrder courseOrder : courseOrders) {
            GetCourseTravelDetailDTO getCourseTravelDetailDTO = new GetCourseTravelDetailDTO();
            Travel travel = courseOrder.getTravel();
            getCourseTravelDetailDTO.setTravelId(travel.getId());
            getCourseTravelDetailDTO.setName(travel.getName());
            getCourseTravelDetailDTO.setImage(travel.getImage());
            getCourseTravelDetailDTO.setLatitude(travel.getLatitude());
            getCourseTravelDetailDTO.setLongitude(travel.getLongitude());
            getCourseTravelDetailDTO.setAddress(travel.getAddress());
            getCourseTravelDetailDTOS.add(getCourseTravelDetailDTO);
        }
        getCourseDetailDTO.setCourseTravels(getCourseTravelDetailDTOS);

        return getCourseDetailDTO;

    }

    public List<GetCourseListDTO> getMyCourseList(String loginId) {
        List<GetCourseListDTO> result = new ArrayList<>();
        List<User> user = userRepository.findByLoginId(loginId);
        if (user.isEmpty()) {
            throw new IllegalStateException("로그인이 필요한 기능입니다.");
        }
        List<Course> myCourses = courseRepository.findMyCourseList(user.get(0).getId());
        for(Course myCourse : myCourses){
            GetCourseListDTO course = new GetCourseListDTO();
            course.setId(myCourse.getId());
            course.setName(myCourse.getName());
            course.set_shared(myCourse.isShared());
            List<GetCourseTravelsDTO> travels = new ArrayList<>();
            List<CourseOrder> courseOrders = courseRepository.findCourseOrderList(myCourse.getId());
            for (CourseOrder courseOrder : courseOrders) {
                GetCourseTravelsDTO travel = new GetCourseTravelsDTO();
                travel.setTravelId(courseOrder.getTravel().getId());
                travel.setTravelName(courseOrder.getTravel().getName());
                travels.add(travel);
            }
            course.setCourseTravels(travels);
            result.add(course);
        }
        return result;
    }

    public List<GetCourseListDTO> getSharedCourseList() {
        List<GetCourseListDTO> result = new ArrayList<>();

        List<Course> sharedCourses = courseRepository.findSharedCourseList();
        for(Course sharedCourse : sharedCourses){
            GetCourseListDTO course = new GetCourseListDTO();
            course.setId(sharedCourse.getId());
            course.setName(sharedCourse.getName());
            course.set_shared(sharedCourse.isShared());
            course.setWriterLoginId(sharedCourse.getUser().getLoginId());
            course.setWriterNickname(sharedCourse.getUser().getNickname());
            List<GetCourseTravelsDTO> travels = new ArrayList<>();
            List<CourseOrder> courseOrders = courseRepository.findCourseOrderList(sharedCourse.getId());
            for (CourseOrder courseOrder : courseOrders) {
                GetCourseTravelsDTO travel = new GetCourseTravelsDTO();
                travel.setTravelId(courseOrder.getTravel().getId());
                travel.setTravelName(courseOrder.getTravel().getName());
                travels.add(travel);
            }
            course.setCourseTravels(travels);
            result.add(course);
        }
        return result;
    }

    @Transactional
    public void shareCourse(Long courseId) {

        Course course = courseRepository.findOne(courseId);

        if (course.isShared() == false) {
            course.setShared(true);
        } else {
            throw new IllegalStateException("이미 공유한 코스 입니다.");
        }
    }

    @Transactional
    public void deleteCourse(Long courseId ) {
        Course findCourse = courseRepository.findOne(courseId);
        if (findCourse==null) {
            throw new IllegalStateException("이미 삭제한 코스 입니다.");
        }
        courseRepository.delete(findCourse);
        List<CourseOrder> findCourseOrderList = courseRepository.findCourseOrderList(courseId);
        for (CourseOrder courseOrder : findCourseOrderList) {
            courseRepository.deleteOrder(courseOrder);
        }
    }
}
