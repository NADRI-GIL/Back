package back.NADRIGIL.service;

import back.NADRIGIL.DTO.DetailTravelDto;
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

    public DetailTravelDto findDetailTravel(Long travelId) {
        DetailTravelDto detailTravelDto = new DetailTravelDto();
        Travel travel = travelRepository.findOne(travelId);

        detailTravelDto.setId(travel.getId());
        detailTravelDto.setImage(travel.getImage());
        detailTravelDto.setName(travel.getName());
        detailTravelDto.setLocation(travel.getLocation());
        detailTravelDto.setAddress(travel.getAddress());
        detailTravelDto.setLike_count(travel.getLike_count());
        detailTravelDto.setInfo(travel.getInfo());
        detailTravelDto.setLatitude(travel.getLatitude());
        detailTravelDto.setLongitude(travel.getLongitude());
        detailTravelDto.setCategory(travel.getCategory());
        detailTravelDto.setReviews(travel.getReviews());

        return detailTravelDto;
    }
}
