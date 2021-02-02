/**
 * 
 */
package io.peruvianit.monitor.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

/**
 * Classe per supporto per LocalDate
 * 
 * @author Sergio Arellano {PeruViANit}
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class DateUtils {

	static DateTimeFormatter dateFormatter =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	static DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	
	static DateTimeFormatter onlyTimeFormatter =  DateTimeFormatter.ofPattern("HH:mm");
	
	
	public static Function<String, LocalDate> convertStringToLocalDate = 
			(String data) -> LocalDate.parse(data, dateFormatter); 
	
	public static Function<String, LocalDateTime> convertStringToLocalDateTimeDxc = 
			(String data) -> {
				return LocalDateTime.parse(data,DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			};
			
	public static Function<LocalDate, String> convertLocalDateToString = 
			(LocalDate data) -> data.format(dateFormatter); 
			
	public static Function<LocalDateTime, String> convertLocalDateTimeToString = 
			(LocalDateTime data) -> data.format(dateTimeFormatter); 
			
	public static Function<LocalDateTime, String> convertLocalDateTimeToTimeString = 
			(LocalDateTime data) -> data.format(onlyTimeFormatter); 
	
 
}
