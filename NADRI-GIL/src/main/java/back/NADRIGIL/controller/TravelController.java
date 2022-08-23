package back.NADRIGIL.controller;

import back.NADRIGIL.DTO.RandomTravelDto;
import back.NADRIGIL.domain.BaseResponseBody;
import back.NADRIGIL.domain.CustomResponseBody;
import back.NADRIGIL.domain.Travel;
import back.NADRIGIL.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TravelController {

    private final TravelService travelService;

    /**
     * 여행지 저장
     * @param travel
     * @return
     */
    @PostMapping("/travels")
    public ResponseEntity<BaseResponseBody> saveTravel(@RequestBody Travel travel) {
        BaseResponseBody responseBody = new BaseResponseBody("여행지 저장 성공");
        try{
            travelService.saveTravel(travel);

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
     * 선호도 조사 랜덤 여행지 추출
     */
    @GetMapping(value = "/travels")
    public ResponseEntity<CustomResponseBody<RandomTravelDto>> randomTravel() {
        CustomResponseBody<RandomTravelDto> responseBody = new CustomResponseBody<>("랜덤 여행지 불러오기 성공");
        try{
            List<RandomTravelDto> randomTravels = travelService.findRandomTravels();
            responseBody.setList(randomTravels);

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