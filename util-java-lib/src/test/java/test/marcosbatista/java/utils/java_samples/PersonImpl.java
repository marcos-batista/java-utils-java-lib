package test.marcosbatista.java.utils.java_samples;

import java.beans.JavaBean;

@JavaBean(defaultProperty = "name")
public class PersonImpl implements Person {
	
	private String name;
	private Integer age;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public Integer getAge() {
		return this.age;
	}
	
}
