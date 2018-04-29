package contracts.shared;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PersonDTO {

	private long id;
	@NotNull(message="{person.name}")
	private String name;
	@NotNull(message="{person.lastName}")
	private String lastName;
	private String fathersName;
	@NotNull(message="{person.birthDate}")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;
	@Min(value=1, message="{person.passSeries}")
	@Max(value=9999, message="{person.passSeries}")
	private int passSeries;
	@Min(value=1, message="{person.passNum}")
	@Max(value=999999, message="{person.passNum}")
	private int passNum;
	
	public PersonDTO() {}
	
	public PersonDTO(String name, String lastName, String fathersName, Date birthDate, int passSeries,
			int passNum) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.fathersName = fathersName;
		this.birthDate = birthDate;
		this.passSeries = passSeries;
		this.passNum = passNum;
	}
	
	public PersonDTO(long id, String name, String lastName, String fathersName, Date birthDate, int passSeries,
			int passNum) {
		this(name, lastName, fathersName, birthDate, passSeries, passNum);
		this.id=id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFathersName() {
		return fathersName;
	}

	public void setFathersName(String fatherName) {
		this.fathersName = fatherName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public int getPassSeries() {
		return passSeries;
	}

	public void setPassSeries(int passSeries) {
		this.passSeries = passSeries;
	}

	public int getPassNum() {
		return passNum;
	}

	public void setPassNum(int passNum) {
		this.passNum = passNum;
	}
	
	public String getInitials() {
		return lastName+" "+name+" "+fathersName;
	}

	@Override
	public String toString() {
		return "PersonDTO [id=" + id + ", name=" + name + ", lastName=" + lastName + ", fathersName=" + fathersName
				+ ", birthDate=" + birthDate + ", passSeries=" + passSeries + ", passNum=" + passNum + "]";
	}
		
	
}
