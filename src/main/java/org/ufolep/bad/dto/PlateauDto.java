package org.ufolep.bad.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PlateauDto {
	private Integer idPlateau;
	private Integer idChampionnat;
	private Integer idClub;
	private Integer numeroJournee;
	private LocalDate datePlateau;
	private LocalTime heureDebut;
	private LocalTime heureFin;
	private String nomSalle;
	private String adresse1Salle;
	private String adresse2Salle;
	private String villeSalle;
	private Character statut;
	private List<CategorieDto> categories;
}