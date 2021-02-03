/**
 * 
 */
package io.peruvianit.monitor.service.impl;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.peruvianit.monitor.agent.ThreadSystemMonitor;
import io.peruvianit.monitor.agent.bean.ThreadInfoBean;
import io.peruvianit.monitor.agent.bean.ThreadInfoFullBean;
import io.peruvianit.monitor.dto.DonutGraphic;
import io.peruvianit.monitor.dto.LogDto;
import io.peruvianit.monitor.dto.ThreadDto;
import io.peruvianit.monitor.dto.ThreadMxDto;
import io.peruvianit.monitor.enums.StateThread;
import io.peruvianit.monitor.error.exception.FileUtilsException;
import io.peruvianit.monitor.service.MonitorService;
import io.peruvianit.monitor.util.DateUtils;
import io.peruvianit.monitor.util.FileUtils;

/**
 * @author Sergio Arellano {PeruViANit}
 *
 */
@Service
public class MonitorServiceImpl implements MonitorService {

	private static String LABEL_DAEMON = "DAEMON";
	private static String LABEL_NOT_DAEMON = "NOT_DAEMON";
	
	@Override
	public ThreadMxDto loadThreadsMx() {
		List<ThreadInfoFullBean> theadInfoFullBeans = ThreadSystemMonitor.dumpStack();
		
		int totalThread = theadInfoFullBeans.size();
		
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
		
		ThreadMxDto threadMxDto = new ThreadMxDto();
		
		threadMxDto.setTotalThread(totalThread);
		threadMxDto.setSnapshotTime(DateUtils.convertLocalDateTimeToString.apply(LocalDateTime.now()));
		threadMxDto.getThreadsInfoFull().addAll(theadInfoFullBeans);
		for(ThreadDto threadDto : threadDtos.values()) {
			threadMxDto.getThreads().add(threadDto);
			
			// create info ThreadMx
			int countThread = threadDto.getCount();
			threadMxDto.getThreadMx().add(
					DonutGraphic.crea(threadDto.getName().toUpperCase(), countThread));
		}
		
		// create info Daemon
		
		int countNotDeamon = totalThread - countDaemon;
		
		threadMxDto.getDaemons().add(
				DonutGraphic.crea(LABEL_DAEMON, countDaemon));
		threadMxDto.getDaemons().add(
				DonutGraphic.crea(LABEL_NOT_DAEMON, countNotDeamon));
		
		return threadMxDto;
		
	}

	@Override
	public List<LogDto> loadLogs(String webServerPathLog) throws FileUtilsException {
		
		List<LogDto> logDtos = new ArrayList<>();
		
		List<File> logFiles = FileUtils.listFile(webServerPathLog);
		
		for (File file : logFiles) {
			LocalDateTime lastModified =
				    LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault());
			logDtos.add(LogDto.crea(file.getName(), file.length(), lastModified ));
		}
		
		return logDtos;
	}

	@Override
	public List<ThreadInfoBean> detectDeadLock() {
		return ThreadSystemMonitor.detectDeadLock();
	}

}
