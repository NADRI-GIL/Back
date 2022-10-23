package back.NADRIGIL.service;

import back.NADRIGIL.domain.Cart;
import back.NADRIGIL.domain.Heart;
import back.NADRIGIL.domain.Travel;
import back.NADRIGIL.domain.User;
import back.NADRIGIL.dto.cart.AddCartDTO;
import back.NADRIGIL.dto.cart.GetMyCartListDTO;
import back.NADRIGIL.dto.heart.AddHeartDTO;
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

        List<User> user = userRepository.findByLoginId(addHeartDTO.getLoginId());
        if (user.isEmpty()) {
            throw new IllegalStateException("로그인이 필요한 기능입니다.");
        }
        Travel travel = travelRepository.findOne(addHeartDTO.getTravelId());

        Boolean is_delete = deleteDuplicateHeart(addHeartDTO.getLoginId(), addHeartDTO.getTravelId());  // 찜 삭제
        if (!is_delete) {
            Heart heart = new Heart();
            heart.setUser(user.get(0));
            heart.setTravel(travel);
            heartRepository.add(heart);
        }

        return is_delete;
    }

    public boolean deleteDuplicateHeart(String loginId, Long travelId ) {
        List<Heart> findHearts = heartRepository.findHeart(loginId, travelId);
        if (!findHearts.isEmpty()) {
            heartRepository.delete(findHearts.get(0));
            return true;
        }
        return false;
    }

}
