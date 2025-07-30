package test.marcosbatista.java.utils.samples;

public class CountryImpl implements Country {

	private String name;
	private Province province;
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setProvince(Province province) {
		this.province = province;
	}
	
	public Province getProvince() {
		return province;
	}

}
