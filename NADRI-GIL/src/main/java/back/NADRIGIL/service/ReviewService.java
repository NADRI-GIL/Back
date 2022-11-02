package back.NADRIGIL.service;

import back.NADRIGIL.domain.Cart;
import back.NADRIGIL.domain.Review;
import back.NADRIGIL.domain.Travel;
import back.NADRIGIL.domain.User;
import back.NADRIGIL.dto.cart.AddCartDTO;
import back.NADRIGIL.dto.review.SaveReviewDTO;
import back.NADRIGIL.repository.CartRepository;
import back.NADRIGIL.repository.ReviewRepository;
import back.NADRIGIL.repository.TravelRepository;
import back.NADRIGIL.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
