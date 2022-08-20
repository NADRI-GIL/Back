package back.NADRIGIL.service;

import back.NADRIGIL.domain.Travel;
import back.NADRIGIL.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TravelService {

    private final TravelRepository travelRepository;

    public void saveTravel(Travel travel){
        travelRepository.save(travel);
    }
}
