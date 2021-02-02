/**
 * 
 */
package io.peruvianit.monitor.error;

import org.apache.commons.lang3.exception.ExceptionUtils;

import io.peruvianit.monitor.error.enums.TypeError;

/**
 * @author Sergio Arellano {PeruViANit}
 *
 */
public class APIException extends Exception implements APIError {

	private static final long serialVersionUID = 4773185552177782207L;

	private TypeError typeError;
	private String detailsError;
	
	public APIException(TypeError typeError, String messageError) {
		super(messageError);
		this.typeError = typeError;
	}

	public APIException(TypeError typeError, Throwable causeError) {
		super(causeError.getMessage(), causeError);
		this.typeError = typeError;
	}
	
	public APIException(TypeError typeError, String messageError, Throwable causeError) {
		super(messageError, causeError);
		this.typeError = typeError;
		this.detailsError = causeError!=null?ExceptionUtils.getRootCauseMessage(causeError):null;
	}

	public APIException(TypeError typeError, String messageError, String detailsError) {
		super(messageError);
		this.typeError = typeError;
		this.detailsError = detailsError;
	}
	
	@Override
	public TypeError getTypeError() {
		return this.typeError;
	}

	@Override
	public String getDetailsError() {
		return this.detailsError;
	}
}
