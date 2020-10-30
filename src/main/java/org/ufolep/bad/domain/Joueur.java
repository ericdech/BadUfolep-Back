package org.ufolep.bad.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "JOUEUR")
public class Joueur {

	@Id
	@GeneratedValue
	@Column(name = "IDJOUEUR", nullable = false)
	private final Integer idJoueur;

	@Column(name = "IDCHAMPIONNAT", nullable = false)
	private Integer idChampionnat;

	@Column(name = "IDADHERENT", nullable = false)
	private Integer idAdherent;

	@Column(name = "IDCATEGORIE", nullable = false)
	private Integer idCategorie;

	@Column(name = "IDCLUB", nullable = true)
	private Integer idClub;

	@Column(name = "IDJOUEURPAIRE", nullable = true)
	private Integer idJoueurPaire;

	@Column(name = "POINTMEILLEURSPLATEAUX", nullable = false)
	private Integer pointMeilleursPlateaux = 0;

	@Column(name = "POINTTOUSPLATEAUX", nullable = false)
	private Integer pointTousPlateaux = 0;

	@Column(name = "NIVEAU", nullable = false)
	private BigDecimal niveau = BigDecimal.ZERO;

	@Column(name = "CLASSEMENT", nullable = true)
	private Integer classement;

	/**
	 * Empty construction for JSON deserialization
	 */
	public Joueur() {
		this(null);
	}
}