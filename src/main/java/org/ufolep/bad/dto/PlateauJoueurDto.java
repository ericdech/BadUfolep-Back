package org.ufolep.bad.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PlateauJoueurDto {
	private Integer idPlateauJoueur;
	private Integer idPlateau;
	private Integer idJoueur;
	private Integer idPlateauJoueurPaire;
	private Integer idPoule;
	private Character presence;
	private Integer point;
	private Integer classement;
}