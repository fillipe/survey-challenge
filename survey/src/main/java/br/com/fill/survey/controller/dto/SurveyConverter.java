package br.com.fill.survey.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import br.com.fill.survey.entity.Survey;
import br.com.fill.survey.entity.SurveyOption;

public final class SurveyConverter {

	public static SurveyDTO toDto(Survey s, boolean showVotes) {
		SurveyDTO dto = new SurveyDTO();
		dto.setId(s.getId());
		dto.setTitle(s.getTitle());
		dto.setEnd(s.getEnd());
		
		List<SurveyOptionDTO> options = s.getOptions().stream().map(o -> {
			SurveyOptionDTO optDto = new SurveyOptionDTO();
			optDto.setDescription(o.getDescription());
			if (showVotes) {
				optDto.setVotes(o.getVotes());
			}
			return optDto;
		}).collect(Collectors.toList());

		dto.setOptions(options);
		return dto;
	}

	public static Survey toEntity(SurveyDTO dto) {
		Survey entity = new Survey();
		BeanUtils.copyProperties(dto, entity);

		List<SurveyOption> options = dto.getOptions().stream().map(o -> {
			SurveyOption opt = new SurveyOption();
			o.setVotes(0L);
			BeanUtils.copyProperties(o, opt);
			return opt;
		}).collect(Collectors.toList());

		entity.setOptions(options);

		return entity;
	}
	
}
