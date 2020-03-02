package br.com.fill.survey.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.fill.survey.entity.Survey;
import br.com.fill.survey.entity.SurveyOption;
import br.com.fill.survey.entity.SurveyStatus;
import br.com.fill.survey.exception.InvalidOptionException;
import br.com.fill.survey.repository.SurveyRepository;

@Service
public class SurveyService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SurveyService.class);
	
	private SurveyRepository repository;
	
	public SurveyService(SurveyRepository repository) {
		this.repository = repository;
	}
	
	public void create(Survey survey) {
		LOGGER.info("Creating survey {}", survey);
		
		survey.setStart(new Date());
		survey.setStatus(SurveyStatus.OPENED);
		
		repository.save(survey);		
	}
	
	public List<Survey> getAllOpened() {
		return repository.findByStatus(SurveyStatus.OPENED);
	}
	
	public boolean vote(Long surveyId, Integer optionNumber) throws InvalidOptionException {
		Survey survey = repository.findByIdAndStatus(surveyId, SurveyStatus.OPENED);
		
		boolean validOption = false;
		if (survey != null) {
			for (SurveyOption o : survey.getOptions()) {
				if (o.getNumber() == optionNumber) {
					o.setVotes(o.getVotes() + 1);
					validOption = true;
				}
			}

			if (!validOption) {
				throw new InvalidOptionException("Not a valid voted option: " + optionNumber);
			}
			
			repository.save(survey);
			return true;
		}
		return false;
	}

}
