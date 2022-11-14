package back.NADRIGIL.service;

import back.NADRIGIL.domain.Cart;
import back.NADRIGIL.domain.Review;
import back.NADRIGIL.domain.Travel;
import back.NADRIGIL.domain.User;
import back.NADRIGIL.dto.cart.AddCartDTO;
import back.NADRIGIL.dto.cart.GetMyCartListDTO;
import back.NADRIGIL.dto.review.GetMyReviewListDTO;
import back.NADRIGIL.dto.review.GetReviewListDTO;
import back.NADRIGIL.dto.review.SaveReviewDTO;
import back.NADRIGIL.dto.review.UpdateReviewDTO;
import back.NADRIGIL.dto.travel.GetTravelDetailDTO;
import back.NADRIGIL.dto.travel.UpdateTravelDTO;
import back.NADRIGIL.repository.CartRepository;
import back.NADRIGIL.repository.ReviewRepository;
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
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final TravelRepository travelRepository;

    @Transactional
    public void saveReview(SaveReviewDTO saveReviewDTO) {

        List<User> user = userRepository.findByLoginId(saveReviewDTO.getLoginId());
        if (user.isEmpty()) {
            throw new IllegalStateException("로그인이 필요한 기능입니다.");
        }
        Travel travel = travelRepository.findOne(saveReviewDTO.getTravelId());

        Review review = new Review();
        review.setUser(user.get(0));
        review.setTravel(travel);
        review.setStar(saveReviewDTO.getStar());
        review.setContent(saveReviewDTO.getContent());
        review.setImage(saveReviewDTO.getImage());
        reviewRepository.save(review);

        //리뷰 총 평점 계산 후 저장
        double calculateScore = travel.getReviewTotal();
        int totalReviewCount = reviewRepository.findReviewCount(saveReviewDTO.getTravelId())-1;
        calculateScore = calculateScore * totalReviewCount + saveReviewDTO.getStar();
        calculateScore = calculateScore / (totalReviewCount+1);
        travel.setReviewTotal(calculateScore);
    }

    public List<GetMyReviewListDTO> getMyReviewList(String loginId) {
        List<GetMyReviewListDTO> result = new ArrayList<>();
        List<User> user = userRepository.findByLoginId(loginId);
        if (user.isEmpty()) {
            throw new IllegalStateException("로그인이 필요한 기능입니다.");
        }
        List<Review> myReviews = reviewRepository.findMyReviewList(user.get(0).getId());
        for (Review myReview : myReviews) {
            GetMyReviewListDTO review = new GetMyReviewListDTO();
            review.setId(myReview.getId());
            review.setStar(myReview.getStar());
            review.setContent(myReview.getContent());
            review.setImage(myReview.getImage());
            review.setCreatedDate(review.changeLocalDaeTime(myReview.getCreatedDate()));
            review.setTravelId(myReview.getTravel().getId());
            review.setTravelName(myReview.getTravel().getName());
            result.add(review);
        }
        return result;
    }

    public List<GetReviewListDTO> getTravelReviews(Long travelId) {
        List<GetReviewListDTO> getReviewListDTOS = new ArrayList<>();

        Travel travel = travelRepository.findOne(travelId);

        for (Review review : travel.getReviews()) {
            GetReviewListDTO getReviewListDTO = new GetReviewListDTO();
            getReviewListDTO.setId(review.getId());
            getReviewListDTO.setStar(review.getStar());
            getReviewListDTO.setContent(review.getContent());
            getReviewListDTO.setImage(review.getImage());
            getReviewListDTO.setCreatedDate(getReviewListDTO.changeLocalDaeTime(review.getCreatedDate()));
            getReviewListDTO.setNickname(userRepository.findOne(review.getUser().getId()).getNickname());
            getReviewListDTOS.add(getReviewListDTO);
        }

        return getReviewListDTOS;
    }

    @Transactional
    public void updateReview(Long reviewId, UpdateReviewDTO updateReviewDTO) {
        Review findReview = reviewRepository.findOne(reviewId);

        findReview.setContent(updateReviewDTO.getContent());
        findReview.setImage(updateReviewDTO.getImage());
    }

    @Transactional
    public void deleteReview(Long reviewId ) {
        Review review = reviewRepository.findOne(reviewId);
        if (review==null) {
            throw new IllegalStateException("이미 삭제한 리뷰 입니다.");
        }
        reviewRepository.delete(review);
    }
}
