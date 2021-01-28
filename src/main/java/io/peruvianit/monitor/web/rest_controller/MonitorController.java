package io.peruvianit.monitor.web.rest_controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.peruvianit.monitor.agent.ThreadSystemMonitor;
import io.peruvianit.monitor.agent.bean.ThreadInfoBean;
import io.peruvianit.monitor.agent.bean.ThreadInfoFullBean;
import io.peruvianit.monitor.error.exception.FileUtilsException;
import io.peruvianit.monitor.util.FileUtils;

@RestController
@RequestMapping(MonitorController.URL_BASE)
@Validated
public class MonitorController{
	
	public static final String URL_BASE = "/io-peruvianit/monitor/";
	
	private final String webServerPathLog;
	
	@Autowired
	public MonitorController(Environment environment) {
		this.webServerPathLog = environment.getProperty("web-server.path.log");
	}

	@GetMapping(path = {"logs/"})
	public List<File> listFilesLog() throws FileUtilsException {
		return  FileUtils.listFile(this.webServerPathLog);
	}
	
	@GetMapping(path = {"logs/{filename:.+}"})
	public FileSystemResource fileLog(@PathVariable String filename, HttpServletResponse response) throws FileUtilsException {
	    response.setHeader("Content-Disposition", "attachment; filename=" + filename);
	    return  FileUtils.file(this.webServerPathLog + "/" + filename);
	}
	
	@GetMapping(path = {"/thread-dump"}, produces = "application/json; charset=UTF-8")
	public ResponseEntity<List<ThreadInfoBean>> getThreadDump() {
	    return ResponseEntity.ok(ThreadSystemMonitor.threadInfo());
	}

	@GetMapping(path = {"/thread-dump-fully"}, produces = "application/json; charset=UTF-8")
	public ResponseEntity<List<ThreadInfoFullBean>> getThreadDumpsFull(HttpServletResponse response) throws IOException {
		return ResponseEntity.ok(ThreadSystemMonitor.dumpStack());
	}
}
