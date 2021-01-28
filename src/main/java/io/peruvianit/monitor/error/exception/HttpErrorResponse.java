package io.peruvianit.monitor.error.exception;

import io.peruvianit.monitor.error.enums.TypeError;

/**
 * Information error of service, error sending with http protocol to a service caller.
 * 
 */
public class HttpErrorResponse {

	private TypeError typeError;
	private String messageError;
	private String detailsError;
	
	private HttpErrorResponse(TypeError typeError, String messageError, String detailsError) {
		super();
		this.typeError = typeError;
		this.messageError = messageError;
		this.detailsError = detailsError;
	}
	
	public static HttpErrorResponse crea(TypeError typeError, String messageError, String detailsError) {
		return new  HttpErrorResponse(typeError, messageError, detailsError);
	}

	public TypeError getTypeError() {
		return typeError;
	}

	public String getMessageError() {
		return messageError;
	}

	public String getDetailsError() {
		return detailsError;
	}
	
}
