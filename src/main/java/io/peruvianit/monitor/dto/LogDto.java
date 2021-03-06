/**
 * 
 */
package io.peruvianit.monitor.dto;

import java.time.LocalDateTime;

import io.peruvianit.monitor.util.DateUtils;
import io.peruvianit.monitor.util.FileUtils;

/**
 * @author Sergio Arellano {PeruViANit}
 *
 */
public class LogDto {

	private String pathNameFile;
	private String size;
	private String lastDateModified;
	
	private LogDto(String pathNameFile, String size, String lastDateModified) {
		super();
		this.pathNameFile = pathNameFile;
		this.size = size;
		this.lastDateModified = lastDateModified;
	}
	
	public static LogDto crea(String pathNameFile, Long size, LocalDateTime lastDateModified) {
		return new LogDto(pathNameFile, 
				FileUtils.getStringSizeLengthFile(size), 
				DateUtils.convertLocalDateTimeToString.apply(lastDateModified));
	}

	public String getPathNameFile() {
		return pathNameFile;
	}

	public String getSize() {
		return size;
	}

	public String getLastDateModified() {
		return lastDateModified;
	}
}
