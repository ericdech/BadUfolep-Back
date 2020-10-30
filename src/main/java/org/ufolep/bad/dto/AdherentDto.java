package org.ufolep.bad.dto;

import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class AdherentDto {
	private Integer idAdherent;
	private String pseudo;
	private String password;
	private String nom;
	private String prenom;
	private LocalDate dateNaissance;
	private Character sexe;
	private String licence;
	private String tel;
	private String email;
	private boolean administrateur;
	private boolean responsableChampionnat;
	private boolean responsableClub;
	private Integer idClub;
}