package org.ufolep.bad.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class InscriptionDto {
	private Integer idPlateau;
	private Integer idJoueur;
	private boolean inscrit;
}