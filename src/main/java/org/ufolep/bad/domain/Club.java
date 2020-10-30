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
@Table(name = "CLUB")
public class Club {

	@Id
	@GeneratedValue
	@Column(name = "IDCLUB", nullable = false)
	private final Integer idClub;

	@Column(name = "UCCLUB", nullable = false)
	private String ucClub;

	@Column(name = "LLCLUB", nullable = true)
	private String llClub;

	@Column(name = "REFFEDERATION", nullable = true)
	private String refFederation;

	@Column(name = "NOMSALLE", nullable = true)
	private String nomSalle;

	@Column(name = "ADRESSE1SALLE", nullable = true)
	private String adresse1Salle;

	@Column(name = "ADRESSE2SALLE", nullable = true)
	private String adresse2Salle;

	@Column(name = "VILLESALLE", nullable = true)
	private String villeSalle;

	@Column(name = "EMAIL", nullable = true)
	private String email;

	@Column(name = "INVITATIONJOUEURAUTOMATIQUE", nullable = false)
	private boolean invitationJoueurAutomatique = false;

	/**
	 * Empty construction for JSON deserialization
	 */
	public Club() {
		this(null);
	}
}