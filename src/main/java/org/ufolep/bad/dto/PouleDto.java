package org.ufolep.bad.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PouleDto {
	private Integer idPoule;
	private Integer idPlateau;
	private Integer idCategorie;
	private Integer numero;
	private byte[] feuilleMatch;
}