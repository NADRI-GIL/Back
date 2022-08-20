package back.NADRIGIL.controller;

import back.NADRIGIL.domain.CustomResponseBody;
import back.NADRIGIL.domain.Travel;
import back.NADRIGIL.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TravelController {

    private final TravelService travelService;

    @PostMapping("/travels")
    public ResponseEntity saveTravel(@RequestBody Travel travel) {
        CustomResponseBody<Travel> responseBody = new CustomResponseBody<>("여행지 저장 성공");
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
}
