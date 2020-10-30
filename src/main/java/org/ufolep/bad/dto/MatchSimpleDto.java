package org.ufolep.bad.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class MatchSimpleDto {
	private Integer idMatchSimple;
	private Integer idPlateauJoueur1;
	private Integer idPlateauJoueur2;
	private Integer idPoule;
	private Integer scoreJoueur1;
	private Integer scoreJoueur2;
	private Integer bonusJoueur1;
	private Integer bonusJoueur2;
	private String annotation;
}