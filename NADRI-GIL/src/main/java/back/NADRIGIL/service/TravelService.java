package back.NADRIGIL.service;

import back.NADRIGIL.domain.Review;
import back.NADRIGIL.dto.review.GetReviewListDTO;
import back.NADRIGIL.dto.travel.GetTravelDetailDTO;
import back.NADRIGIL.dto.travel.GetRandomTravelDTO;
import back.NADRIGIL.dto.travel.GetAllTravelListDTO;
import back.NADRIGIL.dto.travel.UpdateTravelDTO;
import back.NADRIGIL.domain.Travel;
import back.NADRIGIL.repository.TravelRepository;
import back.NADRIGIL.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TravelService {

    private final TravelRepository travelRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveTravel(Travel travel){
        travelRepository.save(travel);
    }

//    @Modifying(clearAutomatically = true)
    public List<GetRandomTravelDTO> getRandomTravels() {
        List<GetRandomTravelDTO> result = new ArrayList<>();
        List<Travel> randomTravels = travelRepository.findRandom();
        for(Travel randomTravel : randomTravels){
            GetRandomTravelDTO travel = new GetRandomTravelDTO();
            travel.setId(randomTravel.getId());
            travel.setName(randomTravel.getName());
            travel.setImage(randomTravel.getImage());
            result.add(travel);
        }
        return result;
    }

    public List<GetAllTravelListDTO> getAllTravels() {
        List<GetAllTravelListDTO> result = new ArrayList<>();
        List<Travel> allTravels = travelRepository.findAll();
        for(Travel allTravel : allTravels){
            GetAllTravelListDTO travel = new GetAllTravelListDTO();
            travel.setId(allTravel.getId());
            travel.setName(allTravel.getName());
            travel.setLocation(allTravel.getLocation());
            travel.setImage(allTravel.getImage());
            result.add(travel);
        }
        return result;
    }

    public List<GetAllTravelListDTO> getHotTravels() {
        List<GetAllTravelListDTO> result = new ArrayList<>();
        List<Travel> hotTravels = travelRepository.findHot();
        for(Travel hotTravel : hotTravels){
            GetAllTravelListDTO travel = new GetAllTravelListDTO();
            travel.setId(hotTravel.getId());
            travel.setName(hotTravel.getName());
            travel.setLocation(hotTravel.getLocation());
            travel.setImage(hotTravel.getImage());
            result.add(travel);
        }
        return result;
    }

    public GetTravelDetailDTO getTravelDetail(Long travelId) {
        GetTravelDetailDTO getTravelDetailDto = new GetTravelDetailDTO();
        List<GetReviewListDTO> getReviewListDTOS = new ArrayList<>();

        Travel travel = travelRepository.findOne(travelId);
        getTravelDetailDto.setId(travel.getId());
        getTravelDetailDto.setImage(travel.getImage());
        getTravelDetailDto.setName(travel.getName());
        getTravelDetailDto.setLocation(travel.getLocation());
        getTravelDetailDto.setAddress(travel.getAddress());
        getTravelDetailDto.setLikeCount(travel.getLikeCount());
        getTravelDetailDto.setInfo(travel.getInfo());
        getTravelDetailDto.setLatitude(travel.getLatitude());
        getTravelDetailDto.setLongitude(travel.getLongitude());
        getTravelDetailDto.setReviewTotal(travel.getReviewTotal());

        //상세페이지 리뷰 정보 가져오기
        for (Review review : travel.getReviews()) {
            GetReviewListDTO getReviewListDTO = new GetReviewListDTO();
            getReviewListDTO.setId(review.getId());
            getReviewListDTO.setStar(review.getStar());
            getReviewListDTO.setContent(review.getContent());
            getReviewListDTO.setImage(review.getImage());
            getReviewListDTO.setCreatedDate(getReviewListDTO.changeLocalDaeTime(review.getCreatedDate()));
            getReviewListDTO.setNickname(userRepository.findOne(review.getUser().getId()).getLoginId());
            getReviewListDTOS.add(getReviewListDTO);
        }
        getTravelDetailDto.setReviews(getReviewListDTOS);

        return getTravelDetailDto;
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
//        findTravel.setCategory(updateTravelDto.getCategory());

    }

    @Transactional
    public void deleteTravel(Long travelId){
        Travel findTravel = travelRepository.findOne(travelId);

        findTravel.setDeleted(true);
    }
}
