package back.NADRIGIL.service;

import back.NADRIGIL.domain.Heart;
import back.NADRIGIL.domain.Recommend;
import back.NADRIGIL.domain.Travel;
import back.NADRIGIL.domain.User;
import back.NADRIGIL.dto.travel.GetAllTravelListDTO;
import back.NADRIGIL.dto.travel.GetRecommendTravelDTO;
import back.NADRIGIL.repository.HeartRepository;
import back.NADRIGIL.repository.TravelRepository;
import back.NADRIGIL.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendService {
    private final TravelRepository travelRepository;
    private final UserRepository userRepository;
    private final HeartRepository heartRepository;

//    public List<String> execPython(String[] command) throws IOException, InterruptedException {
//        List<String> travel_ids = new ArrayList<>();
//        CommandLine commandLine = CommandLine.parse(command[0]);
//        for (int i = 1, n = command.length; i < n; i++) {
//            commandLine.addArgument(command[i]);
//        }
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream);
//        DefaultExecutor executor = new DefaultExecutor();
//        executor.setStreamHandler(pumpStreamHandler);
//        int result = executor.execute(commandLine);
//
//        travel_ids.add(outputStream.toString());
//        return travel_ids;
//
//    }

    public List<GetRecommendTravelDTO> getRecommendTravels(String loginId) throws IOException, InterruptedException {
        User user = userRepository.findByLoginId(loginId).get(0);
        List<Heart> hearts = heartRepository.findMyHeartList(user.getId());
        if (hearts.isEmpty()) {
            throw new IllegalStateException("찜 목록이 비어있어 추천을 받으실 수 없습니다.");
        }
        List<Long> travelIdList = new ArrayList<>();
        for (Heart heart : hearts) {
            travelIdList.add(heart.getTravel().getId());
        }

        List<Long> similarTravelList = new ArrayList<>();
        for (Long heartTravel : travelIdList) {
            List<Recommend> recommendList = travelRepository.findSimilarity(heartTravel);
            for (int i = 0; i < 10; i++) {
                similarTravelList.add(recommendList.get(i).getRecommendTravel());
            }
        }

        //만약 50개 넘으면 랜덤으로 뽑아서 줌
        if (similarTravelList.size() > 50) {
            Collections.shuffle(similarTravelList);
            similarTravelList.subList(0, 50);
        }

        Collections.shuffle(similarTravelList);

        List<GetRecommendTravelDTO> result = new ArrayList<>();
        for (Long i:similarTravelList) {      // 찜 목록 기반으로 여행지는 50개 미만으로 추천해줌

            Travel travel = travelRepository.findOne(i);
            GetRecommendTravelDTO getRecommendTravelDTO = new GetRecommendTravelDTO();
            getRecommendTravelDTO.setId(travel.getId());
            getRecommendTravelDTO.setName(travel.getName());
            getRecommendTravelDTO.setImage(travel.getImage());
            getRecommendTravelDTO.setLocation(travel.getLocation());
            getRecommendTravelDTO.setLikeCount(travel.getLikeCount());
            getRecommendTravelDTO.setReviewTotal(travel.getReviewTotal());
            result.add(getRecommendTravelDTO);
        }
        return result;
    }
}
