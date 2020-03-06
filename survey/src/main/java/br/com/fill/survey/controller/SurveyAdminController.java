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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fill.survey.controller.dto.SurveyConverter;
import br.com.fill.survey.controller.dto.SurveyDTO;
import br.com.fill.survey.entity.Survey;
import br.com.fill.survey.service.SurveyService;

@RestController
@RequestMapping("/survey/admin/v1")
public class SurveyAdminController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SurveyController.class);

	private SurveyService service;

	public SurveyAdminController(SurveyService repository) {
		this.service = repository;
	}

	@PostMapping
	public ResponseEntity<String> create(@RequestBody SurveyDTO request) {
		try {
			service.create(SurveyConverter.toEntity(request));
			return new ResponseEntity<String>("Survey created", HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error while creating survey. {}", e.getMessage());
			return new ResponseEntity<String>("Something's wrong, sorry...", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<SurveyDTO>> getAll(@RequestParam String email) {
		try {
			List<Survey> surveys = service.getAllByOwner(email);

			List<SurveyDTO> response = surveys.stream().map(s -> SurveyConverter.toDto(s, true)).collect(Collectors.toList());

			return new ResponseEntity<List<SurveyDTO>>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error while creating survey. {}", e.getMessage());
			return new ResponseEntity<List<SurveyDTO>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
