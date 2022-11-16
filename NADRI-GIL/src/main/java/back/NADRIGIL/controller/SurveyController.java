package back.NADRIGIL.controller;

import back.NADRIGIL.domain.BaseResponseBody;
import back.NADRIGIL.domain.CustomResponseBody;
import back.NADRIGIL.dto.survey.AddSurveyTravelDTO;
import back.NADRIGIL.dto.survey.GetAllSurveyListDTO;
import back.NADRIGIL.dto.travel.GetAllTravelListDTO;
import back.NADRIGIL.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.hibernate.tool.schema.internal.exec.GenerationTargetToScript;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    /**
     * 선호도 조사 여행지들 추가
     * @param addSurveyTravelDTO
     * @return
     */
    @PostMapping("/surveys/add")
    public ResponseEntity<BaseResponseBody> addSurvey(@RequestBody AddSurveyTravelDTO addSurveyTravelDTO){
        BaseResponseBody responseBody = new BaseResponseBody("선호도 조사 추가 성공");
        try{
            surveyService.addSurveyTravel(addSurveyTravelDTO);
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
     * 선호도조사 여행지 리스트 불러오기
     * @return
     */
    @GetMapping(value = "/surveys/all")
    public ResponseEntity<CustomResponseBody<GetAllSurveyListDTO>> getAllSurveys() {
        CustomResponseBody<GetAllSurveyListDTO> responseBody = new CustomResponseBody<>("모든 선호도조사 여행지 리스트 불러오기 성공");
        try{
            List<GetAllSurveyListDTO> allSurveys = surveyService.getAllSurveys();
            responseBody.setList(allSurveys);

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
