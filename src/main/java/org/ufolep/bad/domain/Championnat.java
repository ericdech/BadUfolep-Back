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
@Table(name = "CHAMPIONNAT")
public class Championnat {

	@Id
	@GeneratedValue
	@Column(name = "IDCHAMPIONNAT", nullable = false)
	private final Integer idChampionnat;

	@Column(name = "UCCHAMPIONNAT", nullable = false)
	private String ucChampionnat;

	@Column(name = "LLCHAMPIONNAT", nullable = true)
	private String llChampionnat;

	@Column(name = "NBJOURNEE", nullable = true)
	private Integer nbJournee;

	@Column(name = "LOGOCHAMPIONNAT", nullable = true)
	private byte[] logoChampionnat;

	@Column(name = "STATUT", nullable = false)
	private Character statut = 'I';

	/**
	 * Empty construction for JSON deserialization
	 */
	public Championnat() {
		this(null);
	}
}