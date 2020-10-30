package org.ufolep.bad.dto;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class JourneeDto {
	private Integer numeroJournee;
	private List<PlateauDto> plateaux;
}