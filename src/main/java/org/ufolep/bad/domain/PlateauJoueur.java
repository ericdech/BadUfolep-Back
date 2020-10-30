package org.ufolep.bad.domain;


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
@Table(name = "PLATEAUJOUEUR")
public class PlateauJoueur {

	@Id
	@GeneratedValue
	@Column(name = "IDPLATEAUJOUEUR", nullable = false)
	private final Integer idPlateauJoueur;

	@Column(name = "IDPLATEAU", nullable = false)
	private Integer idPlateau;

	@Column(name = "IDJOUEUR", nullable = false)
	private Integer idJoueur;

	@Column(name = "IDPLATEAUJOUEURPAIRE", nullable = true)
	private Integer idPlateauJoueurPaire;

	@Column(name = "IDPOULE", nullable = true)
	private Integer idPoule;

	@Column(name = "PRESENCE", nullable = false)
	private Character presence = 'C';

	@Column(name = "POINT", nullable = false)
	private Integer point = 0;

	@Column(name = "CLASSEMENT", nullable = true)
	private Integer classement;

	/**
	 * Empty construction for JSON deserialization
	 */
	public PlateauJoueur() {
		this(null);
	}
}