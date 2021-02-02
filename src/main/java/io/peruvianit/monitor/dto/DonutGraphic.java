/**
 * 
 */
package io.peruvianit.monitor.dto;

/**
 * @author Sergio Arellano {PeruViANit}
 *
 */
public class DonutGraphic {
	private String label;
	private Integer value;
	
	private DonutGraphic(String label, Integer value) {
		super();
		this.label = label;
		this.value = value;
	}
	
	public static DonutGraphic crea(String label, Integer value) {
		return new DonutGraphic(label, value);
	}

	public String getLabel() {
		return label;
	}

	public Integer getValue() {
		return value;
	}

}
