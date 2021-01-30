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
	private Float percentual;
	
	private DonutGraphic(String label, Integer value, Float percentual) {
		super();
		this.label = label;
		this.value = value;
		this.percentual = percentual;
	}
	
	public static DonutGraphic crea(String label, Integer value, Float percentual) {
		return new DonutGraphic(label, value, percentual);
	}

	public String getLabel() {
		return label;
	}

	public Integer getValue() {
		return value;
	}

	public Float getPercentual() {
		return percentual;
	}
}
