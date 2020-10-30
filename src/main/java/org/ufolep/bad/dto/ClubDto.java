package org.ufolep.bad.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ClubDto {
	private Integer idClub;
	private String ucClub;
	private String llClub;
	private String refFederation;
	private String nomSalle;
	private String adresse1Salle;
	private String adresse2Salle;
	private String villeSalle;
	private String email;
	private boolean invitationJoueurAutomatique;
}