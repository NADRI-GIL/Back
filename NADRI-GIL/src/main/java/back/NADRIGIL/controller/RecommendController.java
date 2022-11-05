package back.NADRIGIL.controller;

import back.NADRIGIL.domain.CustomResponseBody;
import back.NADRIGIL.domain.Heart;
import back.NADRIGIL.domain.Travel;
import back.NADRIGIL.domain.User;
import back.NADRIGIL.dto.travel.GetRandomTravelDTO;
import back.NADRIGIL.dto.travel.GetRecommendTravelDTO;
import back.NADRIGIL.dto.travel.GetTravelDetailDTO;
import back.NADRIGIL.repository.HeartRepository;
import back.NADRIGIL.repository.TravelRepository;
import back.NADRIGIL.repository.UserRepository;
import back.NADRIGIL.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecommendController {

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

    @GetMapping(value = "/recommend/{loginId}")
    public ResponseEntity<CustomResponseBody<GetRecommendTravelDTO>> getRecommendTravels(@PathVariable("loginId") String loginId) {
        CustomResponseBody<GetRecommendTravelDTO> responseBody = new CustomResponseBody<>("추천 여행지 불러오기 성공");
        try{
            User user = userRepository.findByLoginId(loginId).get(0);
            List<Heart> hearts = heartRepository.findMyHeartList(user.getId());
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

            responseBody.setList(result);

        } catch (RuntimeException re){
            responseBody.setResultCode(-1);
            responseBody.setResultMsg(re.getMessage());
            return ResponseEntity.badRequest().body(responseBody);
        } catch (Exception e){
            responseBody.setResultCode(-2);
            responseBody.setResultMsg(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }

        return ResponseEntity.ok().body(responseBody);
    }
}
