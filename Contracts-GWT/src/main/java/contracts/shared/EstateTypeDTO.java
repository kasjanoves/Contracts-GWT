package contracts.shared;


public class EstateTypeDTO {
	
	private String type;
	private float value;
	
	public EstateTypeDTO() {
		super();
	}
			
	public EstateTypeDTO(String type, float value) {
		super();
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return type;
	}
	
}
