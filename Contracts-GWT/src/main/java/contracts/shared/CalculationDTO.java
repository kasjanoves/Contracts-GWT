package contracts.shared;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CalculationDTO {

	private long id;
	@Min(value=1, message="{calc.sum}")
	private int sum;
	@NotNull(message="{calc.beginDate}")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date beginDate;
	@NotNull(message="{calc.endDate}")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	@NotNull(message="{calc.propertyType}")
	private EstateTypeDTO propertyType;
	@Length(min=4, max=4, message="{calc.buildYear}")
	private String buildYear;
	@NotNull(message="{calc.area}")
	private String area;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date calculationDate;
	private String price;
	
	public CalculationDTO() {
		//beginDate=new Date();
	}
	
	public CalculationDTO(Date beginDate, Date endDate, EstateTypeDTO propertyType, String buildYear,
			String area) {
		super();
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.propertyType = propertyType;
		this.buildYear = buildYear;
		this.area = area;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public EstateTypeDTO getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(EstateTypeDTO propertyType) {
		this.propertyType = propertyType;
	}

	public String getBuildYear() {
		return buildYear;
	}

	public void setBuildYear(String buildYear) {
		this.buildYear = buildYear;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Date getCalculationDate() {
		return calculationDate;
	}

	public void setCalculationDate(Date calculationDate) {
		this.calculationDate = calculationDate;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "CalculationDTO [id=" + id + ", sum=" + sum + ", beginDate=" + beginDate + ", endDate=" + endDate
				+ ", propertyType=" + propertyType + ", buildYear=" + buildYear + ", area=" + area
				+ ", calculationDate=" + calculationDate + ", price=" + price + "]";
	}
	
	
}
