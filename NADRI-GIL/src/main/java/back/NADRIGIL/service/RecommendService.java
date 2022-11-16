package back.NADRIGIL.service;

import back.NADRIGIL.domain.Heart;
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
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendService {
    private final TravelRepository travelRepository;
    private final UserRepository userRepository;
    private final HeartRepository heartRepository;

    public List<String> execPython(String[] command) throws IOException, InterruptedException {
        List<String> travel_ids = new ArrayList<>();
        CommandLine commandLine = CommandLine.parse(command[0]);
        for (int i = 1, n = command.length; i < n; i++) {
            commandLine.addArgument(command[i]);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        int result = executor.execute(commandLine);

        travel_ids.add(outputStream.toString());
        return travel_ids;

    }

    public List<GetRecommendTravelDTO> getRecommendTravels(String loginId) throws IOException, InterruptedException {
        User user = userRepository.findByLoginId(loginId).get(0);
        List<Heart> hearts = heartRepository.findMyHeartList(user.getId());
        if (hearts.isEmpty()) {
            throw new IllegalStateException("찜 목록이 비어있어 추천을 받으실 수 없습니다.");
        }
        List<String> travelIdList = new ArrayList<>();
        for (Heart heart : hearts) {
            travelIdList.add(heart.getTravel().getId().toString());
        }

        System.out.println("Python Call");
        String[] command = new String[52];      // 찜 목록은 50개 이상 받지 않음
        command[0] = "python";
        //command[1] = "\\workspace\\java-call-python\\src\\main\\resources\\test.py";
        command[1] = "/Users/eunseo/Desktop/recommend/recommendCode.py";
        for (int i = 0; i < travelIdList.size(); i++) {
            command[i+2] = travelIdList.get(i);
        }

        List<GetRecommendTravelDTO> result = new ArrayList<>();
        List<String> a = execPython(command);
        String c = a.get(0).substring(1);
        String d[] = c.split(", |]");
        for (int j = 0; j<50;j++) {      // 찜 목록 기반으로 여행지는 50개 미만으로 추천해줌
            String b = d[j];
            if (b.equals("\r\n")) {
                break;
            }
            Long i = Long.parseLong(b);
            Travel travel = travelRepository.findOne(i);
            GetRecommendTravelDTO getRecommendTravelDTO = new GetRecommendTravelDTO();
            getRecommendTravelDTO.setId(travel.getId());
            getRecommendTravelDTO.setName(travel.getName());
            getRecommendTravelDTO.setImage(travel.getImage());
            result.add(getRecommendTravelDTO);
        }
        return result;
    }
}
