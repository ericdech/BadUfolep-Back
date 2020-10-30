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
@Table(name = "MATCHDOUBLE")
public class MatchDouble {

	@Id
	@GeneratedValue
	@Column(name = "IDMATCHDOUBLE", nullable = false)
	private final Integer idMatchDouble;

	@Column(name = "IDPLATEAUJOUEUR1PAIRE1", nullable = false)
	private Integer idPlateauJoueur1Paire1;

	@Column(name = "IDPLATEAUJOUEUR1PAIRE2", nullable = false)
	private Integer idPlateauJoueur1Paire2;

	@Column(name = "IDPLATEAUJOUEUR2PAIRE1", nullable = false)
	private Integer idPlateauJoueur2Paire1;

	@Column(name = "IDPLATEAUJOUEUR2PAIRE2", nullable = false)
	private Integer idPlateauJoueur2Paire2;

	@Column(name = "IDPOULE", nullable = false)
	private Integer idPoule;

	@Column(name = "SCOREPAIRE1", nullable = false)
	private Integer scorePaire1 = 0;

	@Column(name = "SCOREPAIRE2", nullable = false)
	private Integer scorePaire2 = 0;

	@Column(name = "BONUSPAIRE1", nullable = false)
	private Integer bonusPaire1 = 0;

	@Column(name = "BONUSPAIRE2", nullable = false)
	private Integer bonusPaire2 = 0;

	@Column(name = "ANNOTATION", nullable = true)
	private String annotation;

	/**
	 * Empty construction for JSON deserialization
	 */
	public MatchDouble() {
		this(null);
	}
}