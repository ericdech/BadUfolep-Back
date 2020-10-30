package org.ufolep.bad.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ADHERENT")
public class Adherent {

	@Id
	@GeneratedValue
	@Column(name = "IDADHERENT", nullable = false)
	private final Integer idAdherent;

	@Column(name = "PSEUDO", nullable = false)
	private String pseudo;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "NOM", nullable = false)
	private String nom;

	@Column(name = "PRENOM", nullable = false)
	private String prenom;

	@Column(name = "DATENAISSANCE", nullable = true)
	private LocalDate dateNaissance;

	@Column(name = "SEXE", nullable = true)
	private Character sexe;

	@Column(name = "LICENCE", nullable = true)
	private String licence;

	@Column(name = "TEL", nullable = true)
	private String tel;

	@Column(name = "EMAIL", nullable = true)
	private String email;

	@Column(name = "ADMINISTRATEUR", nullable = false)
	private boolean administrateur = false;

	@Column(name = "RESPONSABLECHAMPIONNAT", nullable = false)
	private boolean responsableChampionnat = false;

	@Column(name = "RESPONSABLECLUB", nullable = false)
	private boolean responsableClub = false;

	@Column(name = "IDCLUB", nullable = true)
	private Integer idClub;

	/**
	 * Empty construction for JSON deserialization
	 */
	public Adherent() {
		this(null);
	}
}