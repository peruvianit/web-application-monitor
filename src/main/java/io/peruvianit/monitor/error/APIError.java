package io.peruvianit.monitor.error;

import io.peruvianit.monitor.error.enums.TypeError;

public interface APIError {

	/**
	 * Ritorna il tipo di Errore
	 * 
	 *  @see TypeError
	 */
	TypeError getTypeError();
	
	/**
	 * Ritorna le cause del problema
	 * 
	 * @return
	 */
	String getDetailsError();
	
}
