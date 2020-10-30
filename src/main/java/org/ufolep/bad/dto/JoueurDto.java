package org.ufolep.bad.dto;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class JoueurDto {
	private Integer idJoueur;
	private Integer idChampionnat;
	private Integer idAdherent;
	private Integer idCategorie;
	private Integer idClub;
	private Integer idJoueurPaire;
	private Integer pointMeilleursPlateaux;
	private Integer pointTousPlateaux;
	private BigDecimal niveau;
	private Integer classement;
}