package back.NADRIGIL.service;

import back.NADRIGIL.dto.DetailTravelDTO;
import back.NADRIGIL.dto.RandomTravelDTO;
import back.NADRIGIL.dto.UpdateTravelDTO;
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

    public List<RandomTravelDTO> findRandomTravels() {
        List<RandomTravelDTO> result = new ArrayList<>();
        List<Travel> randomTravels = travelRepository.findRandom();
        for(Travel randomTravel : randomTravels){
            RandomTravelDTO travel = new RandomTravelDTO();
            travel.setId(randomTravel.getId());
            travel.setName(randomTravel.getName());
            travel.setImage(randomTravel.getImage());
            result.add(travel);
        }
        return result;
    }

    public DetailTravelDTO findDetailTravel(Long travelId) {
        DetailTravelDTO detailTravelDto = new DetailTravelDTO();
        Travel travel = travelRepository.findOne(travelId);
        detailTravelDto.setId(travel.getId());
        detailTravelDto.setImage(travel.getImage());
        detailTravelDto.setName(travel.getName());
        detailTravelDto.setLocation(travel.getLocation());
        detailTravelDto.setAddress(travel.getAddress());
        detailTravelDto.setLikeCount(travel.getLikeCount());
        detailTravelDto.setInfo(travel.getInfo());
        detailTravelDto.setLatitude(travel.getLatitude());
        detailTravelDto.setLongitude(travel.getLongitude());
        detailTravelDto.setCategory(travel.getCategory());
        detailTravelDto.setReviews(travel.getReviews());

        return detailTravelDto;
    }

    @Transactional
    public void updateTravel(Long travelId, UpdateTravelDTO updateTravelDto) {
        Travel findTravel = travelRepository.findOne(travelId);

        findTravel.setImage(updateTravelDto.getImage());
        findTravel.setName(updateTravelDto.getName());
        findTravel.setLocation(updateTravelDto.getLocation());
        findTravel.setAddress(updateTravelDto.getAddress());
        findTravel.setInfo(updateTravelDto.getInfo());
        findTravel.setLatitude(updateTravelDto.getLatitude());
        findTravel.setLongitude(updateTravelDto.getLongitude());
        findTravel.setCategory(updateTravelDto.getCategory());

    }

    @Transactional
    public void deleteTravel(Long travelId){
        Travel findTravel = travelRepository.findOne(travelId);

        findTravel.setDeleted(true);
    }
}
