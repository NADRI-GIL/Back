package back.NADRIGIL.service;

import back.NADRIGIL.domain.Survey;
import back.NADRIGIL.domain.Travel;
import back.NADRIGIL.dto.survey.AddSurveyTravelDTO;
import back.NADRIGIL.dto.survey.GetAllSurveyListDTO;
import back.NADRIGIL.dto.travel.GetAllTravelListDTO;
import back.NADRIGIL.repository.SurveyRepository;
import back.NADRIGIL.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final TravelRepository travelRepository;

    @Transactional
    public void addSurveyTravel(AddSurveyTravelDTO addSurveyTravelDTO) {

        Survey survey = new Survey();
        survey.setTravel(travelRepository.findOne(addSurveyTravelDTO.getTravelId()));
        surveyRepository.add(survey);
    }

    public List<GetAllSurveyListDTO> getAllSurveys() {
        List<GetAllSurveyListDTO> result = new ArrayList<>();
        List<Survey> allSurveys = surveyRepository.findAll();
        for (Survey survey : allSurveys) {
            GetAllSurveyListDTO getAllSurveyListDTO = new GetAllSurveyListDTO();
            getAllSurveyListDTO.setTravelId(survey.getTravel().getId());
            getAllSurveyListDTO.setTravelName(survey.getTravel().getName());
            getAllSurveyListDTO.setTravelImage(survey.getTravel().getImage());
            result.add(getAllSurveyListDTO);
        }
        return result;
    }
}
