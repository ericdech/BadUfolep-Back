package org.ufolep.bad.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class CategorieDto {
	private Integer idCategorie;
	private Integer idChampionnat;
	private String categorieAge;
	private Character sexe;
	private Integer anneeNaissanceMin;
}