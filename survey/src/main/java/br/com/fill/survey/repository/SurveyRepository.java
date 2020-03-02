package br.com.fill.survey.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fill.survey.entity.Survey;
import br.com.fill.survey.entity.SurveyStatus;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
	
	public Survey findByIdAndStatus(Long id, SurveyStatus status);
	
	public List<Survey> findByStatus(SurveyStatus status);
	
}
