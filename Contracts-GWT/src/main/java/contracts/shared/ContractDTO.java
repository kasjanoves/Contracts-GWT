package contracts.shared;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ContractDTO {

	@Min(value=1, message="{contract.number.empty}")
	@Max(value=999999, message="{contract.number.toobig}")
	private long number;
	@NotNull(message="{contract.date}")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date formationDate;
	@NotNull(message="{contract.person}")
	private PersonDTO policyHolder;
	private String comment;
	@NotNull(message="{contract.calc}")
	private CalculationDTO calculation;
	@NotNull(message="{contract.adress}")
	private AdressDTO adress;
	
	public ContractDTO() {
		formationDate=new Date();
	}
	
	public ContractDTO(long number, Date formationDate, PersonDTO policyHolder, String comment, CalculationDTO calculation,
			AdressDTO adress) {
		this.number = number;
		this.formationDate = formationDate;
		this.policyHolder = policyHolder;
		this.comment = comment;
		this.adress = adress;
		setCalculation(calculation);
	}

	public ContractDTO(PersonDTO policyHolder, CalculationDTO calculation, AdressDTO adress) {
		this();
		this.policyHolder = policyHolder;
		this.calculation = calculation;
		this.adress = adress;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public Date getFormationDate() {
		return formationDate;
	}

	public void setFormationDate(Date formationDate) {
		this.formationDate = formationDate;
	}

	public PersonDTO getPolicyHolder() {
		return policyHolder;
	}

	public void setPolicyHolder(PersonDTO policyHolder) {
		this.policyHolder = policyHolder;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public CalculationDTO getCalculation() {
		return calculation;
	}

	public void setCalculation(CalculationDTO calculation) {
		this.calculation = calculation;
	}

	public AdressDTO getAdress() {
		return adress;
	}

	public void setAdress(AdressDTO adress) {
		this.adress = adress;
	}

	@Override
	public String toString() {
		return "ContractDTO [number=" + number + ", formationDate=" + formationDate + ", policyHolder=" + policyHolder
				+ ", adress=" + adress + "]";
	}
		

}
