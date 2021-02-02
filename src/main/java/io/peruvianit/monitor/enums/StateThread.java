package io.peruvianit.monitor.enums;

/**
 * @author Sergio Arellano {PeruViANit}
 *
 */
public enum StateThread {
	NEW("NEW","New","primary"),
	RUNNABLE("RUNNABLE","Runnable","green"),
	BLOCKED("BLOCKED", "Blocked","red"),
	WAITING("WAITING", "Waiting","yellow"),
	TIMED_WAITING("TIMED_WAITING","Timed waiting","yellow"),
	TERMINATED("TERMINATED","Terminated","gray"),
	WITHOUT_STATE("WITHOUT_STATE","Without state","gray");
	
	private String codice;
	private String description;
	private String color;
	
	private StateThread(String codice, String description, String color) {
		this.codice = codice;
		this.description = description;
		this.color = color;
	}

	public String getCodice() {
		return codice;
	}

	public String getDescription() {
		return description;
	}

	public String getColor() {
		return color;
	}
		
}
