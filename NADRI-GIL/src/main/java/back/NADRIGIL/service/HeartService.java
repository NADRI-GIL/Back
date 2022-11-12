package back.NADRIGIL.service;

import back.NADRIGIL.domain.Cart;
import back.NADRIGIL.domain.Heart;
import back.NADRIGIL.domain.Travel;
import back.NADRIGIL.domain.User;
import back.NADRIGIL.dto.cart.AddCartDTO;
import back.NADRIGIL.dto.cart.GetMyCartListDTO;
import back.NADRIGIL.dto.heart.AddFiveHeartsDTO;
import back.NADRIGIL.dto.heart.AddHeartDTO;
import back.NADRIGIL.dto.heart.GetMyHeartListDTO;
import back.NADRIGIL.repository.HeartRepository;
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
public class HeartService {

    private final HeartRepository heartRepository;
    private final UserRepository userRepository;
    private final TravelRepository travelRepository;

    @Transactional
    public boolean addHeart(AddHeartDTO addHeartDTO) {
//        if (addHeartDTO.getLoginId().isBlank()) {
//            throw new IllegalStateException("올바르지 않은 형식입니다.(loginId가 없음)");
//        }
//        if (addHeartDTO.getTravelId()==null) {
//            throw new IllegalStateException("올바르지 않은 형식입니다.(travelId가 없음");
//        }
        List<User> user = userRepository.findByLoginId(addHeartDTO.getLoginId());
        if (user.isEmpty()) {
            throw new IllegalStateException("로그인이 필요한 기능입니다.");
        }
        Travel travel = travelRepository.findOne(addHeartDTO.getTravelId());

        Boolean is_delete = deleteDuplicateHeart(addHeartDTO.getLoginId(), addHeartDTO.getTravelId());  // 찜 삭제
        if (is_delete) {
            travel.setLikeCount(travel.getLikeCount()-1);
            return is_delete;
        } else {
            Heart heart = new Heart();
            heart.setUser(user.get(0));
            heart.setTravel(travel);
            heartRepository.add(heart);
            travel.setLikeCount(travel.getLikeCount()+1);
            return is_delete;
        }

    }

    public boolean deleteDuplicateHeart(String loginId, Long travelId ) {
        List<Heart> findHearts = heartRepository.findHeart(loginId, travelId);
        if (!findHearts.isEmpty()) {
            heartRepository.delete(findHearts.get(0));
            return true;
        }
        return false;
    }

    public List<GetMyHeartListDTO> getMyHeartList(String loginId) {
        List<GetMyHeartListDTO> result = new ArrayList<>();
        List<User> user = userRepository.findByLoginId(loginId);
        if (user.isEmpty()) {
            throw new IllegalStateException("로그인이 필요한 기능입니다.");
        }
        List<Heart> myHearts = heartRepository.findMyHeartList(user.get(0).getId());
        for(Heart myHeart : myHearts){
            GetMyHeartListDTO travel = new GetMyHeartListDTO();
            travel.setId(myHeart.getId());
            travel.setTravelId(myHeart.getTravel().getId());
            travel.setName(myHeart.getTravel().getName());
            travel.setLocation(myHeart.getTravel().getLocation());
            travel.setImage(myHeart.getTravel().getImage());
            result.add(travel);
        }
        return result;
    }

    @Transactional
    public void addFiveHearts(AddFiveHeartsDTO addFiveHeartsDTO) {
        List<User> user = userRepository.findByLoginId(addFiveHeartsDTO.getLoginId());

        for (Long travelId : addFiveHeartsDTO.getTravelIds()) {
            validateDuplicateHeart(addFiveHeartsDTO.getLoginId(), travelId);
            Travel travel = travelRepository.findOne(travelId);
            Heart heart = new Heart();
            heart.setUser(user.get(0));
            heart.setTravel(travel);
            heartRepository.add(heart);
            travel.setLikeCount(travel.getLikeCount()+1);
        }
    }

    public void validateDuplicateHeart(String loginId, Long travelId ) {
        List<Heart> findHeart = heartRepository.findHeart(loginId, travelId);
        if (!findHeart.isEmpty()) {
            throw new IllegalStateException("이미 담은 여행지 입니다.");
        }
    }
}
