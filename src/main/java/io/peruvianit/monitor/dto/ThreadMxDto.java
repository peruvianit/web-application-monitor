/**
 * 
 */
package io.peruvianit.monitor.dto;

import java.util.ArrayList;
import java.util.List;

import io.peruvianit.monitor.agent.bean.ThreadInfoFullBean;
import lombok.Data;

/**
 * @author Sergio Arellano {PeruViANit}
 *
 */
@Data
public class ThreadMxDto {
	private String snapshotTime;
	private Integer totalThread;
	
	private List<ThreadDto> threads = new ArrayList<>();
	
	private List<DonutGraphic> threadMx = new ArrayList<>();
	private List<DonutGraphic> daemons = new ArrayList<>();
	
	private List<ThreadInfoFullBean> threadsInfoFull = new ArrayList<>();
}
