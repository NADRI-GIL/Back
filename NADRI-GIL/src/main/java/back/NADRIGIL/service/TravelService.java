package back.NADRIGIL.service;

import back.NADRIGIL.DTO.RandomTravelDto;
import back.NADRIGIL.domain.Travel;
import back.NADRIGIL.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TravelService {

    private final TravelRepository travelRepository;

    @Transactional
    public void saveTravel(Travel travel){
        travelRepository.save(travel);
    }

    public List<RandomTravelDto> findRandomTravels() {
        List<RandomTravelDto> result = new ArrayList<>();
        List<Travel> randomTravels = travelRepository.findRandom();
        for(Travel randomTravel : randomTravels){
            RandomTravelDto travel = new RandomTravelDto();
            travel.setId(randomTravel.getId());
            travel.setName(randomTravel.getName());
            travel.setImage(randomTravel.getImage());
            result.add(travel);
        }
        return result;
    }
}
