package contracts.shared;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AdressDTO {

	@NotNull(message="{adress.state}")
	private String state;
	private String postIndex;
	@NotNull(message="{adress.region}")
	private String region;
	private String district;
	@NotNull(message="{adress.loc}")
	private String locality;
	@NotNull(message="{adress.str}")
	private String street;
	private int house;
	private String housing;
	private String building;
	@Min(value=1, message="{adress.apartm}")
	private int apartment;
		
	public AdressDTO() {
		super();
	}
		
	public AdressDTO(String state, String postIndex, String region, String district, String locality, String street,
			int house, String housing, String building, int apartment) {
		super();
		this.state = state;
		this.postIndex = postIndex;
		this.region = region;
		this.district = district;
		this.locality = locality;
		this.street = street;
		this.house = house;
		this.housing = housing;
		this.building = building;
		this.apartment = apartment;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostIndex() {
		return postIndex;
	}

	public void setPostIndex(String postIndex) {
		this.postIndex = postIndex;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getHouse() {
		return house;
	}

	public void setHouse(int house) {
		this.house = house;
	}

	public String getHousing() {
		return housing;
	}

	public void setHousing(String housing) {
		this.housing = housing;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public int getApartment() {
		return apartment;
	}

	public void setApartment(int apartment) {
		this.apartment = apartment;
	}

	@Override
	public String toString() {
		return "AdressDTO [state=" + state + ", postIndex=" + postIndex + ", region=" + region + ", district="
				+ district + ", locality=" + locality + ", street=" + street + ", house=" + house + ", housing="
				+ housing + ", building=" + building + ", apartment=" + apartment + "]";
	}
	
	
}
