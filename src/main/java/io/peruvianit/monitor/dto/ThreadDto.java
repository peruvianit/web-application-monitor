/**
 * 
 */
package io.peruvianit.monitor.dto;

/**
 * @author Sergio Arellano {PeruViANit}
 *
 */
public class ThreadDto {
	
	private String name;
	private Integer count;
	private String classColor;
	
	private ThreadDto(String name, String classColor) {
		super();
		this.name = name;
		this.count = 1;
		this.classColor = classColor;
	}
	
	public static ThreadDto crea(String name, String classColor) {
		return new ThreadDto(name, classColor);
	}

	public String getName() {
		return name;
	}

	public Integer getCount() {
		return count;
	}

	public String getClassColor() {
		return classColor;
	}
	
	public void incrementCount() {
		this.count++;
	}
	
}
