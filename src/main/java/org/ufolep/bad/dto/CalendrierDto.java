package org.ufolep.bad.dto;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class CalendrierDto {
	private Integer idChampionnat;
	private Integer nbJournee;
	private List<JourneeDto> journees;
}