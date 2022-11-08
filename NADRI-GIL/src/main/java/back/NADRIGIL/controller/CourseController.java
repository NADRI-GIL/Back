package back.NADRIGIL.controller;

import back.NADRIGIL.domain.BaseResponseBody;
import back.NADRIGIL.domain.CustomResponseBody;
import back.NADRIGIL.dto.course.AddCourseDTO;
import back.NADRIGIL.dto.course.GetCourseDetailDTO;
import back.NADRIGIL.dto.course.GetCourseListDTO;
import back.NADRIGIL.dto.travel.GetAllTravelListDTO;
import back.NADRIGIL.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    /**
     * 코스 추가
     * @param addCourseDTO
     * @return
     */
    @PostMapping("/courses/add")
    public ResponseEntity<BaseResponseBody> addCourse(@RequestBody AddCourseDTO addCourseDTO) {
        BaseResponseBody responseBody = new BaseResponseBody("코스 저장하기 성공");
        try {
            courseService.addCourse(addCourseDTO);

        } catch (IllegalStateException e){
            responseBody.setResultCode(-1);
            responseBody.setResultMsg(e.getMessage());
            return ResponseEntity.badRequest().body(responseBody);
        } catch (Exception e){
            responseBody.setResultCode(-2);
            responseBody.setResultMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
        return ResponseEntity.ok().body(responseBody);

    }

    /**
     * 로그인된 사용자의 코스 리스트 불러오기
     * @param loginIdMap
     * @return
     */
    @PostMapping("/courses/myList")
    public ResponseEntity<CustomResponseBody<GetCourseListDTO>> getMyCourseList(@RequestBody Map<String, String> loginIdMap) {
        CustomResponseBody<GetCourseListDTO> responseBody = new CustomResponseBody<>("내 코스 리스트 불러오기 성공");
        try {
            List<GetCourseListDTO> myCourses = courseService.getMyCourseList(loginIdMap.get("loginId"));
            responseBody.setList(myCourses);

        } catch (RuntimeException re){
            responseBody.setResultCode(-1);
            responseBody.setResultMsg(re.getMessage());
            return ResponseEntity.badRequest().body(responseBody);
        } catch (Exception e){
            responseBody.setResultCode(-2);
            responseBody.setResultMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }

        return ResponseEntity.ok().body(responseBody);
    }

    /**
     * 코스 상세 보기
     * @param courseId
     * @return
     */
    @GetMapping("/courses/detail/{courseId}")
    public ResponseEntity<CustomResponseBody<GetCourseDetailDTO>> getCourseDetail(@PathVariable("courseId") Long courseId) {
        CustomResponseBody<GetCourseDetailDTO> responseBody = new CustomResponseBody<>("코스 상세 보기 성공");
        try {
            GetCourseDetailDTO courseDetail = courseService.getCourseDetail(courseId);
            responseBody.getList().add(courseDetail);
        } catch (IllegalStateException e){
            responseBody.setResultCode(-1);
            responseBody.setResultMsg(e.getMessage());
            return ResponseEntity.badRequest().body(responseBody);
        } catch (Exception e){
            responseBody.setResultCode(-2);
            responseBody.setResultMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }

        return ResponseEntity.ok().body(responseBody);
    }

    /**
     * 코스 공유하기
     * @param courseId
     * @return
     */
    @PostMapping("/courses/share/{courseId}")
    public ResponseEntity<BaseResponseBody> shareCourse(@PathVariable("courseId") Long courseId){
        BaseResponseBody responseBody = new BaseResponseBody("코스 공유 성공");
        try{
            courseService.shareCourse(courseId);
        } catch (IllegalStateException e){
            responseBody.setResultCode(-1);
            responseBody.setResultMsg(e.getMessage());
            return ResponseEntity.badRequest().body(responseBody);
        } catch (Exception e){
            responseBody.setResultCode(-2);
            responseBody.setResultMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
        return ResponseEntity.ok().body(responseBody);
    }

    /**
     * 공유된 코스들 불러오기
     * @return
     */
    @GetMapping(value = "/courses/getShared")
    public ResponseEntity<CustomResponseBody<GetCourseListDTO>> getSharedCourses() {
        CustomResponseBody<GetCourseListDTO> responseBody = new CustomResponseBody<>("공유된 코스들 불러오기 성공");
        try{
            List<GetCourseListDTO> sharedTravels = courseService.getSharedCourseList();;
            responseBody.setList(sharedTravels);

        } catch (RuntimeException re){
            responseBody.setResultCode(-1);
            responseBody.setResultMsg(re.getMessage());
            return ResponseEntity.badRequest().body(responseBody);
        } catch (Exception e){
            responseBody.setResultCode(-2);
            responseBody.setResultMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }

        return ResponseEntity.ok().body(responseBody);
    }

}
