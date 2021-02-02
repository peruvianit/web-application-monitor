/**
 * 
 */
package io.peruvianit.monitor.agent.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Sergio Arellano {PeruViANit}
 *
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class ThreadInfoFullBean extends ThreadInfoBean{

	private Integer priority;
	private Boolean daemon;
	private Long blockCount;
	private Long waitCount;
	private Long cpuTime;
	private Long userTime;

}