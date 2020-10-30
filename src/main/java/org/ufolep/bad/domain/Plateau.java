package org.ufolep.bad.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PLATEAU")
public class Plateau {

	@Id
	@GeneratedValue
	@Column(name = "IDPLATEAU", nullable = false)
	private final Integer idPlateau;

	@Column(name = "IDCHAMPIONNAT", nullable = false)
	private Integer idChampionnat;

	@Column(name = "IDCLUB", nullable = false)
	private Integer idClub;

	@Column(name = "NUMEROJOURNEE", nullable = false)
	private Integer numeroJournee;

	@Column(name = "DATEPLATEAU", nullable = true)
	private LocalDate datePlateau;

	@Column(name = "HEUREDEBUT", nullable = true)
	private LocalTime heureDebut;

	@Column(name = "HEUREFIN", nullable = true)
	private LocalTime heureFin;

	@Column(name = "NOMSALLE", nullable = true)
	private String nomSalle;

	@Column(name = "ADRESSE1SALLE", nullable = true)
	private String adresse1Salle;

	@Column(name = "ADRESSE2SALLE", nullable = true)
	private String adresse2Salle;

	@Column(name = "VILLESALLE", nullable = true)
	private String villeSalle;

	@Column(name = "STATUT", nullable = false)
	private Character statut = 'I';

	@ManyToMany() 
	@JoinTable(
		name = "PLATEAUCATEGORIE", 
		joinColumns = @JoinColumn(name = "IDPLATEAU", nullable = false, updatable = false),
		inverseJoinColumns = @JoinColumn(name = "IDCATEGORIE", nullable = false, updatable = false))
	private List<Categorie> categories;

	/**
	 * Empty construction for JSON deserialization
	 */
	public Plateau() {
		this(null);
	}
}