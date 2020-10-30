package org.ufolep.bad.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class MatchDoubleDto {
	private Integer idMatchDouble;
	private Integer idPlateauJoueur1Paire1;
	private Integer idPlateauJoueur1Paire2;
	private Integer idPlateauJoueur2Paire1;
	private Integer idPlateauJoueur2Paire2;
	private Integer idPoule;
	private Integer scorePaire1;
	private Integer scorePaire2;
	private Integer bonusPaire1;
	private Integer bonusPaire2;
	private String annotation;
}