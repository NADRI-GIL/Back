package back.NADRIGIL.controller;

import back.NADRIGIL.domain.CustomResponseBody;
import back.NADRIGIL.dto.travel.GetRecommendTravelDTO;
import back.NADRIGIL.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    /**
     * 찜 기반 추천 여행지 리스트 불러오기
     * @param loginId
     * @return
     */
    @GetMapping(value = "/recommend/{loginId}")
    public ResponseEntity<CustomResponseBody<GetRecommendTravelDTO>> getRecommendTravels(@PathVariable("loginId") String loginId) {
        CustomResponseBody<GetRecommendTravelDTO> responseBody = new CustomResponseBody<>("추천 여행지 불러오기 성공");
        try{
            List<GetRecommendTravelDTO> recommendTravels = recommendService.getRecommendTravels(loginId);
            responseBody.setList(recommendTravels);

        } catch (IllegalStateException e){
            responseBody.setResultCode(-11);
            responseBody.setResultMsg(e.getMessage());
            return ResponseEntity.badRequest().body(responseBody);
        } catch (Exception e){
            responseBody.setResultCode(-2);
            responseBody.setResultMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }

        return ResponseEntity.ok().body(responseBody);
    }
}
