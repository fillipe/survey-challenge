package br.com.fill.surveyscheduler.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fill.surveyscheduler.entity.Survey;
import br.com.fill.surveyscheduler.entity.SurveyStatus;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

	public List<Survey> findByStatusAndEndBefore(SurveyStatus status, Date date);
	
}
