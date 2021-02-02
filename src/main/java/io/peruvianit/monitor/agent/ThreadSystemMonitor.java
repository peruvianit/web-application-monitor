/**
 * 
 */
package io.peruvianit.monitor.agent;

import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.peruvianit.monitor.agent.bean.ThreadInfoBean;
import io.peruvianit.monitor.agent.bean.ThreadInfoBean.LockInfoBean;
import io.peruvianit.monitor.agent.bean.ThreadInfoBean.LockerMonitorInfoBean;
import io.peruvianit.monitor.agent.bean.ThreadInfoBean.StackTraceElementInfoBean;
import io.peruvianit.monitor.agent.bean.ThreadInfoFullBean;
import io.peruvianit.monitor.enums.StateThread;

/**
 * @author Sergio Arellano {PeruViANit}
 *
 */


public class ThreadSystemMonitor {
	public static List<ThreadInfoBean> threadInfo() {

		ThreadInfo[] threadInfos = ManagementFactory.getThreadMXBean().dumpAllThreads(true, true);

		List<ThreadInfoBean> theadInfoBeans = new ArrayList<>();

		for (ThreadInfo ti : threadInfos) {
			ThreadInfoBean theadInfoBean = new ThreadInfoBean();
			theadInfoBean.setName(ti.getThreadName());
			theadInfoBean.setId(ti.getThreadId());
			theadInfoBean.setLockName(ti.getLockName());
			theadInfoBean.setLockOwnerName(ti.getLockOwnerName());
			theadInfoBean.setLockOwnerId(ti.getLockOwnerId());

			theadInfoBean.setSuspended(ti.isSuspended());
			theadInfoBean.setInNative(ti.isInNative());

			int i = 0;
			StackTraceElement[] stackTrace = ti.getStackTrace();
			for (; i < stackTrace.length; i++) {
				StackTraceElement ste = stackTrace[i];

				StackTraceElementInfoBean stackTraceElementInfoBean = new StackTraceElementInfoBean(ste.getClassName(),
						ste.getMethodName(), ste.getFileName(), Integer.valueOf(ste.getLineNumber()));

				theadInfoBean.getStackTraceElements().add(stackTraceElementInfoBean);

				Thread.State ts = ti.getThreadState();
				switch (ts) {
				case NEW:
					theadInfoBean.setStateThread(StateThread.NEW);
					break;
				case RUNNABLE:
					theadInfoBean.setStateThread(StateThread.RUNNABLE);
					break;
				case BLOCKED:
					theadInfoBean.setStateThread(StateThread.BLOCKED);
					break;
				case WAITING:
					theadInfoBean.setStateThread(StateThread.WAITING);
					break;
				case TIMED_WAITING:
					theadInfoBean.setStateThread(StateThread.TIMED_WAITING);
					break;
				case TERMINATED:
					theadInfoBean.setStateThread(StateThread.TERMINATED);
					break;
				default:
					theadInfoBean.setStateThread(StateThread.WITHOUT_STATE);
					break;
				}
				for (MonitorInfo mi : ti.getLockedMonitors()) {
					if (mi.getLockedStackDepth() == i) {
						LockerMonitorInfoBean lockerMonitorInfoBean = new LockerMonitorInfoBean();

						lockerMonitorInfoBean.setStackDepth(mi.getLockedStackDepth());
						lockerMonitorInfoBean.setStackFrame(new StackTraceElementInfoBean(
								mi.getLockedStackFrame().getClassName(), mi.getLockedStackFrame().getMethodName(),
								mi.getLockedStackFrame().getFileName(), mi.getLockedStackFrame().getLineNumber()));

						lockerMonitorInfoBean.setClassName(mi.getClassName());
						lockerMonitorInfoBean.setIdentityHashCode(mi.getIdentityHashCode());

						theadInfoBean.getLockerMonitors().add(lockerMonitorInfoBean);

					}
				}
			}

			LockInfo[] locks = ti.getLockedSynchronizers();
			if (locks.length > 0) {
				for (LockInfo li : locks) {
					LockInfoBean lockInfoBean = new LockInfoBean(li.getClassName(),
							Integer.valueOf(li.getIdentityHashCode()));
					theadInfoBean.getLockInfos().add(lockInfoBean);
				}
			}

			theadInfoBeans.add(theadInfoBean);
		}

		return theadInfoBeans;
	}
  
  public static List<ThreadInfoFullBean> dumpStack() {
    ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
    ThreadInfo[] threadInfos = mxBean.getThreadInfo(mxBean.getAllThreadIds(), 0);
    Map<Long, ThreadInfo> threadInfoMap = new HashMap<>();
    
    List<ThreadInfoFullBean> theadInfoFullBeans = new ArrayList<>();
    
    for (ThreadInfo threadInfo : threadInfos) {
      threadInfoMap.put(Long.valueOf(threadInfo.getThreadId()), threadInfo);
    }
   
    Map<Thread, StackTraceElement[]> stacks = Thread.getAllStackTraces();
	for (Map.Entry<Thread, StackTraceElement[]> entry : stacks.entrySet()) {
		Thread thread = entry.getKey();
	    
		ThreadInfoFullBean threadInfoFullBean = new ThreadInfoFullBean();
	    
	    threadInfoFullBean.setName(thread.getName());
	    threadInfoFullBean.setPriority(thread.getPriority());
	    threadInfoFullBean.setId(thread.getId());
	    
	    Thread.State ts = thread.getState();
	    switch (ts) {
	      case NEW:
	    	threadInfoFullBean.setStateThread(StateThread.NEW);
	    	break;
	      case RUNNABLE:
	    	threadInfoFullBean.setStateThread(StateThread.RUNNABLE);
	        break;
	      case BLOCKED:
	    	threadInfoFullBean.setStateThread(StateThread.BLOCKED);
	        break;
	      case WAITING:
	    	threadInfoFullBean.setStateThread(StateThread.WAITING);
	        break;
	      case TIMED_WAITING:
	    	threadInfoFullBean.setStateThread(StateThread.TIMED_WAITING);
	    	break;
	      case TERMINATED:
	    	threadInfoFullBean.setStateThread(StateThread.TERMINATED);
	    	break;
		  default:
			threadInfoFullBean.setStateThread(StateThread.WITHOUT_STATE);
			break;
	    } 
	    
	    threadInfoFullBean.setDaemon(thread.isDaemon());
	    
	    ThreadInfo threadInfo = threadInfoMap.get(Long.valueOf(thread.getId()));
	    
	    if (threadInfo != null) {
	        threadInfoFullBean.setInNative(threadInfo.isInNative());
	        threadInfoFullBean.setSuspended(threadInfo.isSuspended());
	        threadInfoFullBean.setBlockCount(threadInfo.getBlockedCount());
	        threadInfoFullBean.setWaitCount(threadInfo.getWaitedCount());
	        
	        threadInfoFullBean.setLockName(threadInfo.getLockName());
	        threadInfoFullBean.setLockOwnerName(threadInfo.getLockOwnerName());
	        threadInfoFullBean.setLockOwnerId(threadInfo.getLockOwnerId());
	        threadInfoFullBean.setCpuTime(
	        		mxBean.getThreadCpuTime(threadInfo.getThreadId()) / 1000000L);
	        threadInfoFullBean.setUserTime(
	        		mxBean.getThreadUserTime(threadInfo.getThreadId()) / 1000000L);
	  	}
	
	    for (StackTraceElement element : (StackTraceElement[])entry.getValue()) {
	    	StackTraceElementInfoBean stackTraceElementInfoBean = 
	    		  new StackTraceElementInfoBean(element.getClassName(), element.getMethodName(), element.getFileName(), Integer.valueOf(element.getLineNumber()));
	      
	    	threadInfoFullBean.getStackTraceElements().add(stackTraceElementInfoBean);
	    }
	  
	    theadInfoFullBeans.add(threadInfoFullBean);
	} 
	
	return theadInfoFullBeans;
  }
  
}
