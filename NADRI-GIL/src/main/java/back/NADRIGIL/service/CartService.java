package back.NADRIGIL.service;

import back.NADRIGIL.domain.Cart;
import back.NADRIGIL.domain.Travel;
import back.NADRIGIL.domain.User;
import back.NADRIGIL.dto.CartInfoDTO;
import back.NADRIGIL.repository.CartRepository;
import back.NADRIGIL.repository.TravelRepository;
import back.NADRIGIL.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTravel(travel);
        cartRepository.add(cart);
    }
}
