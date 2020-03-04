package br.com.fill.survey.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fill.survey.controller.dto.SurveyDTO;
import br.com.fill.survey.controller.dto.SurveyOptionDTO;
import br.com.fill.survey.entity.Survey;
import br.com.fill.survey.entity.SurveyOption;
import br.com.fill.survey.exception.InvalidOptionException;
import br.com.fill.survey.service.SurveyService;

@RestController
@RequestMapping("/v1/survey")
public class SurveyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SurveyController.class);

	private SurveyService service;

	public SurveyController(SurveyService repository) {
		this.service = repository;
	}

	@PostMapping
	public ResponseEntity<String> create(@RequestBody SurveyDTO request) {
		try {
			service.create(toEntity(request));
			return new ResponseEntity<String>("Survey created", HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error while creating survey. {}", e.getMessage());
			return new ResponseEntity<String>("Something's wrong, sorry...", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<List<SurveyDTO>> getAll() {
		try {
			List<Survey> surveys = service.getAllOpened();

			List<SurveyDTO> response = surveys.stream().map(s -> toDto(s)).collect(Collectors.toList());

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

	private SurveyDTO toDto(Survey s) {
		SurveyDTO dto = new SurveyDTO();
		dto.setId(s.getId());
		dto.setTitle(s.getTitle());
		dto.setEnd(s.getEnd());

		List<SurveyOptionDTO> options = s.getOptions().stream().map(o -> {
			SurveyOptionDTO optDto = new SurveyOptionDTO();
			BeanUtils.copyProperties(o, optDto);
			return optDto;
		}).collect(Collectors.toList());

		dto.setOptions(options);
		return dto;
	}

	private Survey toEntity(SurveyDTO dto) {
		Survey entity = new Survey();
		BeanUtils.copyProperties(dto, entity);

		List<SurveyOption> options = dto.getOptions().stream().map(o -> {
			SurveyOption opt = new SurveyOption();
			BeanUtils.copyProperties(o, opt);
			return opt;
		}).collect(Collectors.toList());

		entity.setOptions(options);

		return entity;
	}

}
