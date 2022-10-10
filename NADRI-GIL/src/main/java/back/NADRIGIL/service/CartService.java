package back.NADRIGIL.service;

import back.NADRIGIL.domain.Cart;
import back.NADRIGIL.domain.Travel;
import back.NADRIGIL.domain.User;
import back.NADRIGIL.dto.AllTravelListDTO;
import back.NADRIGIL.dto.CartInfoDTO;
import back.NADRIGIL.dto.MyCartListDTO;
import back.NADRIGIL.repository.CartRepository;
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
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final TravelRepository travelRepository;

    @Transactional
    public void addCart(CartInfoDTO cartInfoDTO) {

        User user = userRepository.findOne(cartInfoDTO.getUserId());
        Travel travel = travelRepository.findOne(cartInfoDTO.getTravelId());

        validateDuplicateCart(cartInfoDTO.getUserId(), cartInfoDTO.getTravelId());  // 장바구니 중복 검증

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTravel(travel);
        cartRepository.add(cart);
    }

    public void validateDuplicateCart(Long userId, Long travelId ) {
        List<Cart> findCarts = cartRepository.findOne(userId, travelId);
        if (!findCarts.isEmpty()) {
            throw new IllegalStateException("이미 담은 여행지 입니다.");
        }
    }

    public List<MyCartListDTO> findMyCartList(Long userId) {
        List<MyCartListDTO> result = new ArrayList<>();
        List<Cart> myCarts = cartRepository.findMyCartList(userId);
        for(Cart myCart : myCarts){
            MyCartListDTO travel = new MyCartListDTO();
            travel.setTravelId(myCart.getTravel().getId());
            travel.setName(myCart.getTravel().getName());
            travel.setLocation(myCart.getTravel().getLocation());
            travel.setImage(myCart.getTravel().getImage());
            result.add(travel);
        }
        return result;
    }
}
