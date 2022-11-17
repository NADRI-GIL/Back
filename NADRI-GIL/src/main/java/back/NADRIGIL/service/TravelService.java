package back.NADRIGIL.service;

import back.NADRIGIL.domain.Recommend;
import back.NADRIGIL.domain.Review;
import back.NADRIGIL.domain.Survey;
import back.NADRIGIL.dto.review.GetReviewListDTO;
import back.NADRIGIL.dto.travel.*;
import back.NADRIGIL.domain.Travel;
import back.NADRIGIL.repository.TravelRepository;
import back.NADRIGIL.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TravelService {

    private final TravelRepository travelRepository;
    private final UserRepository userRepository;
    private final RecommendService recommendService;

    @Transactional
    public void saveTravel(Travel travel){
        travelRepository.save(travel);
    }

    public List<GetAllTravelListDTO> getSearchTravels(GetSearchWordDTO getSearchWordDTO) {
        List<GetAllTravelListDTO> result = new ArrayList<>();
        List<Travel> searchTravels = travelRepository.findWord(getSearchWordDTO.getWord());
        for (Travel searchTravel : searchTravels) {
            GetAllTravelListDTO travel = new GetAllTravelListDTO();
            travel.setId(searchTravel.getId());
            travel.setName(searchTravel.getName());
            travel.setImage(searchTravel.getImage());
            travel.setLocation(searchTravel.getLocation());
            travel.setLikeCount(searchTravel.getLikeCount());
            travel.setReviewTotal(searchTravel.getReviewTotal());
            result.add(travel);
        }
        return result;
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
            travel.setLikeCount(allTravel.getLikeCount());
            travel.setReviewTotal(allTravel.getReviewTotal());
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
            travel.setLikeCount(hotTravel.getLikeCount());
            travel.setReviewTotal(hotTravel.getReviewTotal());
            result.add(travel);
        }
        return result;
    }

    public List<GetAllTravelListDTO> getGoodReviewTravels() {
        List<GetAllTravelListDTO> result = new ArrayList<>();
        List<Travel> goodReviewTravels = travelRepository.findGoodReview();
        for(Travel goodReviewTravel : goodReviewTravels){
            GetAllTravelListDTO travel = new GetAllTravelListDTO();
            travel.setId(goodReviewTravel.getId());
            travel.setName(goodReviewTravel.getName());
            travel.setLocation(goodReviewTravel.getLocation());
            travel.setImage(goodReviewTravel.getImage());
            travel.setLikeCount(goodReviewTravel.getLikeCount());
            travel.setReviewTotal(goodReviewTravel.getReviewTotal());
            result.add(travel);
        }
        return result;
    }

    public GetTravelDetailDTO getTravelDetail(Long travelId) throws IOException, InterruptedException {
        GetTravelDetailDTO getTravelDetailDto = new GetTravelDetailDTO();
        List<GetReviewListDTO> getReviewListDTOS = new ArrayList<>();
        List<GetRecommendTravelListDTO> getRecommendTravelListDTOS = new ArrayList<>();

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
            getReviewListDTO.setNickname(userRepository.findOne(review.getUser().getId()).getNickname());
            getReviewListDTOS.add(getReviewListDTO);
        }
        getTravelDetailDto.setReviews(getReviewListDTOS);

        //상세페이지와 유사한 여행지들 가져오기
        List<Recommend> recommends = travelRepository.findSimilarity(travelId);
        for (int j = 0; j<10;j++) {      // 추천 여행지 10개 가져옴
            Long recommendTravelId = recommends.get(j).getRecommendTravel();
            Travel recommendTravel = travelRepository.findOne(recommendTravelId);
            GetRecommendTravelListDTO getRecommendTravelListDTO = new GetRecommendTravelListDTO();
            getRecommendTravelListDTO.setId(recommendTravel.getId());
            getRecommendTravelListDTO.setName(recommendTravel.getName());
            getRecommendTravelListDTO.setLocation(recommendTravel.getLocation());
            getRecommendTravelListDTO.setImage(recommendTravel.getImage());
            getRecommendTravelListDTO.setSimilarity(recommends.get(j).getSimilarity());
            getRecommendTravelListDTOS.add(getRecommendTravelListDTO);
        }
        getTravelDetailDto.setRecommendTravels(getRecommendTravelListDTOS);

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
