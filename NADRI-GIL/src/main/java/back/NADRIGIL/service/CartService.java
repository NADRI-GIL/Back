package back.NADRIGIL.service;

import back.NADRIGIL.domain.Cart;
import back.NADRIGIL.domain.Travel;
import back.NADRIGIL.domain.User;
import back.NADRIGIL.dto.cart.CartAddDTO;
import back.NADRIGIL.dto.cart.MyCartListDTO;
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
    public void addCart(CartAddDTO cartAddDTO) {

        List<User> user = userRepository.findByLoginId(cartAddDTO.getLoginId());
        if (user.isEmpty()) {
            throw new IllegalStateException("로그인이 필요한 기능입니다.");
        }
        Travel travel = travelRepository.findOne(cartAddDTO.getTravelId());

        validateDuplicateCart(cartAddDTO.getLoginId(), cartAddDTO.getTravelId());  // 장바구니 중복 검증

        Cart cart = new Cart();
        cart.setUser(user.get(0));
        cart.setTravel(travel);
        cartRepository.add(cart);
    }

    public void validateDuplicateCart(String loginId, Long travelId ) {
        List<Cart> findCarts = cartRepository.findCart(loginId, travelId);
        if (!findCarts.isEmpty()) {
            throw new IllegalStateException("이미 담은 여행지 입니다.");
        }
    }

    public List<MyCartListDTO> findMyCartList(String loginId) {
        List<MyCartListDTO> result = new ArrayList<>();
        List<User> user = userRepository.findByLoginId(loginId);
        if (user.isEmpty()) {
            throw new IllegalStateException("로그인이 필요한 기능입니다.");
        }
        List<Cart> myCarts = cartRepository.findMyCartList(user.get(0).getId());
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
