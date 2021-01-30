/**
 * 
 */
package io.peruvianit.monitor.service.impl;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.peruvianit.monitor.agent.ThreadSystemMonitor;
import io.peruvianit.monitor.agent.bean.ThreadInfoFullBean;
import io.peruvianit.monitor.dto.DashboardDto;
import io.peruvianit.monitor.dto.DashboardDto.ThreadMxDashboard;
import io.peruvianit.monitor.dto.DonutGraphic;
import io.peruvianit.monitor.dto.LogDto;
import io.peruvianit.monitor.dto.SommarioDto;
import io.peruvianit.monitor.dto.ThreadDto;
import io.peruvianit.monitor.enums.StateThread;
import io.peruvianit.monitor.error.exception.FileUtilsException;
import io.peruvianit.monitor.service.MonitorService;
import io.peruvianit.monitor.util.FileUtils;

/**
 * @author Sergio Arellano {PeruViANit}
 *
 */
@Service
public class MonitorServiceImpl implements MonitorService {

	private static String LABEL_DAEMON = "Daemon";
	private static String LABEL_NOT_DAEMON = "Not Daemon";
	
	@Override
	public DashboardDto loadDashboard(String webServerPathLog) throws FileUtilsException {
		List<ThreadInfoFullBean> theadInfoFullBeans = ThreadSystemMonitor.dumpStack();
		
		DashboardDto dashboardDto = new DashboardDto();
		
		int totalThread = theadInfoFullBeans.size();
		
		dashboardDto.setTotalThread(totalThread);
		
		// Analyzer Threads
		
		Map<String,ThreadDto> threadDtos = new HashMap<>();
		Integer countDaemon = 0;
		
		for (ThreadInfoFullBean threadInfoFullBean : theadInfoFullBeans) {
			StateThread stateThread = threadInfoFullBean.getStateThread();
			
			if (stateThread != null && 
					threadDtos.containsKey(stateThread.getCodice())) {
				threadDtos.get(stateThread.getCodice()).incrementCount();
			}else {
				String codice = stateThread != null ? 
						stateThread.getCodice() :
							StateThread.WITHOUT_STATE.getCodice();
				String color = stateThread != null ? 
						stateThread.getColor() :
							StateThread.WITHOUT_STATE.getColor();
						
				ThreadDto threadDto = ThreadDto.crea(
						codice, color);
				
				threadDtos.put(codice, threadDto);
			}
			
			if (threadInfoFullBean.getDaemon()) {
				countDaemon++;
			}
		}

		// create list threads 
		
		ThreadMxDashboard threadMxDashboard = new ThreadMxDashboard();
		
		for(ThreadDto threadDto : threadDtos.values()) {
			threadMxDashboard.getThreads().add(threadDto);
			
			// create info ThreadMx
			int countThread = threadDto.getCount();
			threadMxDashboard.getThreadMx().add(
					DonutGraphic.crea(threadDto.getName(), countThread, new Float(countThread*100/totalThread)));
		}
		
		// create info Daemon
		
		int countNotDeamon = totalThread - countDaemon;
		Float percentualeDeamon = new Float(countDaemon*100/totalThread);
		
		threadMxDashboard.getDaemons().add(
				DonutGraphic.crea(LABEL_DAEMON, countDaemon, percentualeDeamon));
		threadMxDashboard.getDaemons().add(
				DonutGraphic.crea(LABEL_NOT_DAEMON, countNotDeamon, 100L - percentualeDeamon ));
		
		dashboardDto.setThreadMxDashboard(threadMxDashboard);
		
		// Load files log
		
		List<File> logFiles = FileUtils.listFile(webServerPathLog);
		
		for (File file : logFiles) {
			LocalDateTime lastModified =
				    LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault());
			dashboardDto.getLogs().add(
					LogDto.crea(file.getName(), file.length(), lastModified ));
		}
		
		// Load Summary
		
		dashboardDto.getSummary().add(SommarioDto.crea(totalThread + " threadMx trovati"));
		dashboardDto.getSummary().add(SommarioDto.crea(logFiles.size() + " files logs trovati"));
				
		return dashboardDto;
		
	}

}
