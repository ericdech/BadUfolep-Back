package org.ufolep.bad.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class SessionDto {
	private AdherentDto adherent;
	private ClubDto club;
}