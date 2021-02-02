package io.peruvianit.monitor.dto;

public class SommarioDto {
	
	private String message;

	private SommarioDto(String message) {
		super();
		this.message = message;
	}
	
	public static SommarioDto crea(String message) {
		return new SommarioDto(message);
	}

	public String getMessage() {
		return message;
	}
	
}
