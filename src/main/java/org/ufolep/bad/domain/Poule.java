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
@Table(name = "POULE")
public class Poule {

	@Id
	@GeneratedValue
	@Column(name = "IDPOULE", nullable = false)
	private final Integer idPoule;

	@Column(name = "IDPLATEAU", nullable = false)
	private Integer idPlateau;

	@Column(name = "IDCATEGORIE", nullable = false)
	private Integer idCategorie;

	@Column(name = "NUMERO", nullable = false)
	private Integer numero;

	@Column(name = "FEUILLEMATCH", nullable = true)
	private byte[] feuilleMatch;

	/**
	 * Empty construction for JSON deserialization
	 */
	public Poule() {
		this(null);
	}
}