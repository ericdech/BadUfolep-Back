package org.ufolep.bad.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ChampionnatDto {
	private Integer idChampionnat;
	private String ucChampionnat;
	private String llChampionnat;
	private byte[] logoChampionnat;
	private Integer nbJournee;
	private Character statut;
}