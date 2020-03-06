package br.com.fill.survey.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fill.survey.controller.dto.SurveyConverter;
import br.com.fill.survey.controller.dto.SurveyDTO;
import br.com.fill.survey.entity.Survey;
import br.com.fill.survey.exception.InvalidOptionException;
import br.com.fill.survey.service.SurveyService;

@RestController
@RequestMapping("/survey/v1")
public class SurveyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SurveyController.class);

	private SurveyService service;

	public SurveyController(SurveyService repository) {
		this.service = repository;
	}

	@GetMapping
	public ResponseEntity<List<SurveyDTO>> getAll() {
		try {
			List<Survey> surveys = service.getAllOpened();

			List<SurveyDTO> response = surveys.stream().map(s -> SurveyConverter.toDto(s, false)).collect(Collectors.toList());

			return new ResponseEntity<List<SurveyDTO>>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error while creating survey. {}", e.getMessage());
			return new ResponseEntity<List<SurveyDTO>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/vote")
	public ResponseEntity<String> vote(@RequestParam("surveyId") Long surveyId, @RequestParam("optionNumber") Integer optionNumber) {
		try {
			boolean voted = service.vote(surveyId, optionNumber);
			
			if (voted) {
				return new ResponseEntity<String>("Voted!", HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Survey does not exists or expired.", HttpStatus.OK);
			}
			
		} catch(InvalidOptionException e) {
			LOGGER.warn("Invalid option {} for survey {}", optionNumber, surveyId);
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		} 
		catch (Exception e) {
			LOGGER.error("Error while creating survey. {}", e.getMessage());
			return new ResponseEntity<String>("Something's wrong, sorry...", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
