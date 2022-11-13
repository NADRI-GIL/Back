package back.NADRIGIL.controller;

import back.NADRIGIL.domain.BaseResponseBody;
import back.NADRIGIL.domain.CustomResponseBody;
import back.NADRIGIL.dto.review.GetReviewListDTO;
import back.NADRIGIL.dto.review.SaveReviewDTO;
import back.NADRIGIL.dto.review.UpdateReviewDTO;
import back.NADRIGIL.dto.travel.GetAllTravelListDTO;
import back.NADRIGIL.dto.travel.UpdateTravelDTO;
import back.NADRIGIL.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 저장하기
     * @param saveReviewDTO
     * @return
     */
    @PostMapping("/reviews/save")
    public ResponseEntity<BaseResponseBody> saveReview(@RequestBody SaveReviewDTO saveReviewDTO){
        BaseResponseBody responseBody = new BaseResponseBody("리뷰 저장 성공");
        try{
            reviewService.saveReview(saveReviewDTO);
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
     * 해당 여행지의 리뷰 리스트 불러오기
     * @param travelId
     * @return
     */
    @GetMapping(value = "/reviews/all/{travelId}")
    public ResponseEntity<CustomResponseBody<GetReviewListDTO>> getTravelReviews(@PathVariable("travelId") Long travelId) {
        CustomResponseBody<GetReviewListDTO> responseBody = new CustomResponseBody<>("해당 여행지의 리뷰 리스트 불러오기 성공");
        try{
            List<GetReviewListDTO> getReviewListDTOS = reviewService.getTravelReviews(travelId);
            responseBody.setList(getReviewListDTOS);

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
     * 리뷰 수정하기
     * @param reviewId
     * @param updateReviewDTO
     * @return
     */
    @PostMapping(value = "/reviews/edit/{reviewId}")
    public ResponseEntity<BaseResponseBody> updateReview(@PathVariable Long reviewId, @RequestBody UpdateReviewDTO updateReviewDTO) {
        BaseResponseBody responseBody = new BaseResponseBody("리뷰 내용 수정 성공");
        try{
            reviewService.updateReview(reviewId,updateReviewDTO);

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
     * 리뷰 삭제하기
     * @param reviewId
     * @return
     */
    @PostMapping(value = "/reviews/delete/{reviewId}")
    public ResponseEntity<BaseResponseBody> deleteReview(@PathVariable Long reviewId) {
        BaseResponseBody responseBody = new BaseResponseBody("리뷰 삭제 성공");
        try{
            reviewService.deleteReview(reviewId);

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
