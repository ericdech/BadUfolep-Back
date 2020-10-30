package org.ufolep.bad.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.ufolep.bad.dto.ErrorDto;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorController extends AbstractErrorController {
	
	// Error Attributes
	final private ErrorAttributes errorAttributes;

	public ErrorController(final ErrorAttributes errorAttributes) {
		super(errorAttributes);
		this.errorAttributes = errorAttributes;
	}

	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ErrorDto handleError(final HttpServletRequest request) {
		Map<String, Object> errorAttributesMap = 
			getErrorAttributes(
				request, 
				ErrorAttributeOptions.of(Include.MESSAGE));
		ErrorDto errorDto = new ErrorDto();
		errorDto.setStatus((int) errorAttributesMap.get("status"));
		errorDto.setError((String) errorAttributesMap.get("error"));
		errorDto.setTimestamp((Date) errorAttributesMap.get("timestamp"));
		errorDto.setPath((String) errorAttributesMap.get("path"));
		errorDto.setMessage((String) errorAttributesMap.get("message"));

		// Origine (première ligne de la stack trace
		Throwable error = errorAttributes.getError(new ServletWebRequest(request));
		if (error != null 
			&& error.getStackTrace() != null
			&& error.getStackTrace().length > 0) {
			errorDto.setOrigin((String) error.getStackTrace()[0].toString());
		}
		return errorDto;
	}

	@Override
	public String getErrorPath() {
		return null;
	}
}