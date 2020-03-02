package br.com.fill.surveyscheduler;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.fill.surveyscheduler.entity.Survey;
import br.com.fill.surveyscheduler.entity.SurveyStatus;
import br.com.fill.surveyscheduler.repository.SurveyRepository;

@Component
public class SurveyScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(SurveyScheduler.class);
	
	private SurveyRepository repository;
	
	public SurveyScheduler(SurveyRepository repository) {
		this.repository = repository;
	}
	
	@Scheduled(cron = "0 0/1 * 1/1 * ?")
	public void finishSurveys() {
		LOGGER.info("Doing schedule...");
		List<Survey> surveys = repository.findByStatusAndEndBefore(SurveyStatus.OPENED, new Date());
		surveys.forEach(s -> closeAndSendNotification(s));
	}

	private void closeAndSendNotification(Survey survey) {
		survey.setStatus(SurveyStatus.FINISHED);
		repository.save(survey);
		LOGGER.info("Sending result of survey {} to {}", survey.getTitle(), survey.getOwnerEmail());
	}
	
}
