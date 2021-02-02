/**
 * 
 */
package io.peruvianit.monitor.agent.bean;

import java.util.ArrayList;
import java.util.List;

import io.peruvianit.monitor.enums.StateThread;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Sergio Arellano {PeruViANit}
 *
 */
@Data
public class ThreadInfoBean {
	
	//public enum StateThread{NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED}
	
	private String name;
	private Long id;
	private String lockName;
	private String lockOwnerName;
	private Long lockOwnerId;
	
	private Boolean suspended;
	private Boolean inNative;
	
	private StateThread stateThread;
	
	List<StackTraceElementInfoBean> stackTraceElements = new ArrayList<>();
	List<LockerMonitorInfoBean> lockerMonitors = new ArrayList<>();
	List<LockInfoBean> lockInfos = new ArrayList<>();
	
	@Data
	@AllArgsConstructor
	public static class StackTraceElementInfoBean{
		private String declaringClass;
	    private String methodName;
	    private String fileName;
	    private Integer lineNumber;
	}
	
	@Data
	public static class LockerMonitorInfoBean{
		private Integer stackDepth;
	    private StackTraceElementInfoBean stackFrame; // TODO da controllare
		private String className;
	    private Integer identityHashCode;
	    
	}
	
	@Data
	@AllArgsConstructor
	public static class LockInfoBean {
	    private String className;
	    private Integer identityHashCode;
	}
	
}
