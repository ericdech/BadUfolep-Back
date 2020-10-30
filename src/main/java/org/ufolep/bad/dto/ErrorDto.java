package org.ufolep.bad.dto;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ErrorDto {
	private Integer status;
	private String error;
	private Date timestamp;
	private String path;
	private String message;
	private String origin;
}