/**
 * 
 */
package io.peruvianit.monitor.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author Sergio Arellano {PeruViANit}
 *
 */
@Data
public class DashboardDto {
	
	private ThreadMxDashboard threadMxDashboard;
	
	private Integer totalThread;
	
	private List<LogDto> logs = new ArrayList<>();
	
	private List<SommarioDto> summary = new ArrayList<>();
	
	@Data
	public static class HostName{
		private String ipAddress;
		private String hostname;
	}
	
	@Data
	public static class ThreadMxDashboard{
		private List<ThreadDto> threads = new ArrayList<>();
		
		private List<DonutGraphic> threadMx = new ArrayList<>();
		private List<DonutGraphic> daemons = new ArrayList<>();
		
	}
	
}
