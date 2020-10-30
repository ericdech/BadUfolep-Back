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
@Table(name = "MATCHSIMPLE")
public class MatchSimple {

	@Id
	@GeneratedValue
	@Column(name = "IDMATCHSIMPLE", nullable = false)
	private final Integer idMatchSimple;

	@Column(name = "IDPLATEAUJOUEUR1", nullable = false)
	private Integer idPlateauJoueur1;

	@Column(name = "IDPLATEAUJOUEUR2", nullable = false)
	private Integer idPlateauJoueur2;

	@Column(name = "IDPOULE", nullable = false)
	private Integer idPoule;

	@Column(name = "SCOREJOUEUR1", nullable = false)
	private Integer scoreJoueur1 = 0;

	@Column(name = "SCOREJOUEUR2", nullable = false)
	private Integer scoreJoueur2 = 0;

	@Column(name = "BONUSJOUEUR1", nullable = false)
	private Integer bonusJoueur1 = 0;

	@Column(name = "BONUSJOUEUR2", nullable = false)
	private Integer bonusJoueur2 = 0;

	@Column(name = "ANNOTATION", nullable = true)
	private String annotation;

	/**
	 * Empty construction for JSON deserialization
	 */
	public MatchSimple() {
		this(null);
	}
}