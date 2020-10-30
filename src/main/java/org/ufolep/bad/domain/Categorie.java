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
@Table(name = "CATEGORIE")
public class Categorie {

	@Id
	@GeneratedValue
	@Column(name = "IDCATEGORIE", nullable = false)
	private final Integer idCategorie;

	@Column(name = "IDCHAMPIONNAT", nullable = false)
	private Integer idChampionnat;

	@Column(name = "CATEGORIEAGE", nullable = false)
	private String categorieAge;

	@Column(name = "SEXE", nullable = false)
	private Character sexe;

	@Column(name = "ANNEENAISSANCEMIN", nullable = false)
	private Integer anneeNaissanceMin;

	/**
	 * Empty construction for JSON deserialization
	 */
	public Categorie() {
		this(null);
	}
}