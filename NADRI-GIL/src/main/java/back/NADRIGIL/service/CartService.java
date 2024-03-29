package back.NADRIGIL.service;

import back.NADRIGIL.domain.*;
import back.NADRIGIL.dto.cart.AddCartDTO;
import back.NADRIGIL.dto.cart.GetMyCartListDTO;
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
    public void addCart(AddCartDTO addCartDTO) {

        List<User> user = userRepository.findByLoginId(addCartDTO.getLoginId());
        if (user.isEmpty()) {
            throw new IllegalStateException("로그인이 필요한 기능입니다.");
        }
        Travel travel = travelRepository.findOne(addCartDTO.getTravelId());

        validateDuplicateCart(addCartDTO.getLoginId(), addCartDTO.getTravelId());  // 장바구니 중복 검증

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

    public List<GetMyCartListDTO> getMyCartList(String loginId) {
        List<GetMyCartListDTO> result = new ArrayList<>();
        List<User> user = userRepository.findByLoginId(loginId);
        if (user.isEmpty()) {
            throw new IllegalStateException("로그인이 필요한 기능입니다.");
        }
        List<Cart> myCarts = cartRepository.findMyCartList(user.get(0).getId());
        for(Cart myCart : myCarts){
            GetMyCartListDTO travel = new GetMyCartListDTO();
            travel.setId(myCart.getId());
            travel.setTravelId(myCart.getTravel().getId());
            travel.setName(myCart.getTravel().getName());
            travel.setLocation(myCart.getTravel().getLocation());
            travel.setImage(myCart.getTravel().getImage());
            travel.setAddress(myCart.getTravel().getAddress());
            travel.setLatitude(myCart.getTravel().getLatitude());
            travel.setLongitude(myCart.getTravel().getLongitude());
            result.add(travel);
        }
        return result;
    }

    @Transactional
    public void deleteCart(Long cartId ) {
        Cart cart = cartRepository.findOne(cartId);
        if (cart==null) {
            throw new IllegalStateException("이미 삭제한 장바구니 입니다.");
        }
        cartRepository.delete(cart);
    }
}
