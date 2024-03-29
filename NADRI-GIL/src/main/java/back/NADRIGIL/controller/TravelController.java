package back.NADRIGIL.controller;

import back.NADRIGIL.dto.travel.*;
import back.NADRIGIL.domain.BaseResponseBody;
import back.NADRIGIL.domain.CustomResponseBody;
import back.NADRIGIL.domain.Travel;
import back.NADRIGIL.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/travels/new")
    public ResponseEntity<BaseResponseBody> addTravel(@RequestBody Travel travel) {
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
     * 여행지 검색
     * @param getSearchWordDTO
     * @return
     */
    @PostMapping("/travels/search")
    public ResponseEntity<CustomResponseBody<GetAllTravelListDTO>> searchTravel(@RequestBody GetSearchWordDTO getSearchWordDTO) {
        CustomResponseBody<GetAllTravelListDTO> responseBody = new CustomResponseBody<>("여행지 검색 결과 불러오기 성공");
        try{
            List<GetAllTravelListDTO> searchTravels = travelService.getSearchTravels(getSearchWordDTO);
            responseBody.setList(searchTravels);

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
     * 여행지 상세페이지 불러오기
     * @param travelId
     * @return
     */
    @GetMapping(value = "travels/{travelId}/detail")
    public ResponseEntity<CustomResponseBody<GetTravelDetailDTO>> getTravelDetail(@PathVariable("travelId") Long travelId) {
        CustomResponseBody<GetTravelDetailDTO> responseBody = new CustomResponseBody<>("상세 여행지 불러오기");
        try{
            GetTravelDetailDTO detailTravel = travelService.getTravelDetail(travelId);
            responseBody.getList().add(detailTravel);
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
    @GetMapping(value = "/travels/random")
    public ResponseEntity<CustomResponseBody<GetRandomTravelDTO>> getRandomTravels() {
        CustomResponseBody<GetRandomTravelDTO> responseBody = new CustomResponseBody<>("랜덤 여행지 불러오기 성공");
        try{
            List<GetRandomTravelDTO> randomTravels = travelService.getRandomTravels();
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

    /**
     * 모든 여행지 리스트 불러오기
     * @return
     */
    @GetMapping(value = "/travels/all")
    public ResponseEntity<CustomResponseBody<GetAllTravelListDTO>> getAllTravels() {
        CustomResponseBody<GetAllTravelListDTO> responseBody = new CustomResponseBody<>("모든 여행지 리스트 불러오기 성공");
        try{
            List<GetAllTravelListDTO> allTravels = travelService.getAllTravels();
            responseBody.setList(allTravels);

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
     * 찜 개수 기반 여행지 50개 불러오기
     * @return
     */
    @GetMapping(value = "/travels/heart")
    public ResponseEntity<CustomResponseBody<GetAllTravelListDTO>> getHotTravels() {
        CustomResponseBody<GetAllTravelListDTO> responseBody = new CustomResponseBody<>("찜 개수 기반 여행지 50개 불러오기 성공");
        try{
            List<GetAllTravelListDTO> hotTravels = travelService.getHotTravels();
            responseBody.setList(hotTravels);

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
     * 리뷰 총 별점 높은 여행지 50개 불러오기
     * @return
     */
    @GetMapping(value = "/travels/review")
    public ResponseEntity<CustomResponseBody<GetAllTravelListDTO>> getGoodReviewTravels() {
        CustomResponseBody<GetAllTravelListDTO> responseBody = new CustomResponseBody<>("리뷰 총 별점 높은 여행지 50개 불러오기 성공");
        try{
            List<GetAllTravelListDTO> goodReviewTravels = travelService.getGoodReviewTravels();
            responseBody.setList(goodReviewTravels);

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
     * 여행지 수정하기
     * @param travelId
     * @param updateTravelDto
     * @return
     */
    @PostMapping(value = "/travels/{travelId}/edit")
    public ResponseEntity<BaseResponseBody> updateTravel(@PathVariable Long travelId, @RequestBody UpdateTravelDTO updateTravelDto) {
        BaseResponseBody responseBody = new BaseResponseBody("여행지 수정 성공");
        try{
            travelService.updateTravel(travelId, updateTravelDto);

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
     * 여행지 삭제하기
     * @param travelId
     * @return
     */
    @PostMapping(value = "/travels/{travelId}/delete")
    public ResponseEntity<BaseResponseBody> deleteTravel(@PathVariable Long travelId) {
        BaseResponseBody responseBody = new BaseResponseBody("여행지 삭제 성공");
        try{
            travelService.deleteTravel(travelId);

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