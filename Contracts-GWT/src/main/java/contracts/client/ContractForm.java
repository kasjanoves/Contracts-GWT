package contracts.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasConstrainedValue;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LongBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.validation.client.impl.Validation;

import contracts.shared.CalculationDTO;
import contracts.shared.ContractDTO;
import contracts.shared.EstateTypeDTO;
import contracts.shared.PersonDTO;

public class ContractForm extends Composite implements Editor<ContractDTO>{

	private static ContractFormUiBinder uiBinder = GWT.create(ContractFormUiBinder.class);
	private RESTservice restService = GWT.create(RESTservice.class);
	private ContractsConstants constants = GWT.create(ContractsConstants.class);

	interface ContractFormUiBinder extends UiBinder<Widget, ContractForm> {
	}
	interface Driver extends SimpleBeanEditorDriver<PersonDTO, PersonForm> {
	}
	private Driver driver = GWT.create(Driver.class);
	
	private ContractsListForm owner;
	private boolean isNew;
		
	public ContractForm(ContractsListForm owner, boolean isNew) {
		this.owner=owner;
		this.isNew=isNew;
		initWidget(uiBinder.createAndBindUi(this));
		Resource resource = new Resource(Defaults.getServiceRoot());
		((RestServiceProxy) restService).setResource(resource);
		initWidgetsProps();
	}
	
	@UiField
	@Ignore
	Label messages;
	@UiField
	LongBox number; 
	@UiField
	DateBox formationDate;
	@UiField
	TextArea comment;
				
	@UiField
	@Path("calculation.sum")
	IntegerBox sum;
	@UiField
	@Path("calculation.propertyType")
	ValueListBox<EstateTypeDTO> estateType;
	@UiField
	@Path("calculation.buildYear")
	TextBox buildYear;
	@UiField
	@Path("calculation.area")
	TextBox area;
	@UiField
	@Path("calculation.beginDate")
	DateBox dateFrom;
	@UiField
	@Path("calculation.endDate")
	DateBox dateTo;
	@UiField
	@Path("calculation.calculationDate")
	DateBox calcDate;
	@UiField
	@Path("calculation.price")
	TextBox price;
	@UiField
	Button calcButton;
	
	@UiField
	Button selectPersonButton;
	@UiField
	@Path("policyHolder.initials")
	TextBox personInitials;
	@UiField
	Button editPersonButton;
	@UiField
	@Path("policyHolder.birthDate")
	DateBox birghtDate;
	@UiField
	@Path("policyHolder.passSeries")
	IntegerBox passSeries;
	@UiField
	@Path("policyHolder.passNum")
	IntegerBox passNum;
	
	@UiField
	@Path("adress.state")
	ValueListBox<String> state;
	@UiField
	@Path("adress.postIndex")
	TextBox postIndex;
	@UiField
	@Path("adress.region")
	TextBox region;
	@UiField
	@Path("adress.district")
	TextBox district;
	@UiField
	@Path("adress.locality")
	TextBox locality;
	@UiField
	@Path("adress.street")
	TextBox street;
	@UiField
	@Path("adress.house")
	IntegerBox house;
	@UiField
	@Path("adress.housing")
	TextBox housing;
	@UiField
	@Path("adress.building")
	TextBox building;
	@UiField
	@Path("adress.apartment")
	IntegerBox apartment;
		
	@UiField
	Button saveButton;
	@UiField
	Button returnButton;
	
	@UiHandler("saveButton")
	void onClickCrtBtn(ClickEvent e) {
		owner.saveContract(this);
	}
	
	@UiHandler("returnButton")
	void onClickRetBtn(ClickEvent e) {
		RootPanel.get().clear();
		RootPanel.get().add(owner);
	}
	
	@UiHandler("editPersonButton")
	void onClickEdpBtn(ClickEvent e) {
		ContractDTO updatedContract = owner.updateContract();
		if(updatedContract.getPolicyHolder()!=null)
			editPerson(false);
	}
	
	@UiHandler("selectPersonButton")
	void onClickSelpBtn(ClickEvent e) {
		SelectPersonForm spForm = new SelectPersonForm(this);
		RootPanel.get().clear();
		RootPanel.get().add(spForm);
	}
	
	@UiHandler("calcButton")
	void onClickClpBtn(ClickEvent e) {
		final ContractDTO updatedContract = owner.updateContract();
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<CalculationDTO>> violations =
				validator.validate(updatedContract.getCalculation());
		if(violations.isEmpty())
		{
			restService.calculate(updatedContract.getCalculation(),
					new MethodCallback<CalculationDTO>() {
						@Override
						public void onSuccess(Method method, CalculationDTO response) {
							updatedContract.setCalculation(response);
							owner.editContract(updatedContract, isNew);
						}
						@Override
						public void onFailure(Method method, Throwable exception) {
							Window.alert("calculation "+
									exception.toString()+" "+
									method.getResponse().getStatusCode() +" "+
									method.getResponse().getText());						
						}
					});
		}
			else
		{
			String message = "";
			for(ConstraintViolation<CalculationDTO> item : violations) {
				message = message + item.getMessage() + "\r\n";
				
			}	
			setErrors(message);
		}
	}
	
	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	public void setErrors(String text) {
		this.messages.setText(text);
	}

	public HasConstrainedValue<EstateTypeDTO> getEstateTypesWidget() {
		return estateType;
	}
	
	public void editPerson(boolean isNewPerson) {
		PersonDTO person;
		if(isNewPerson)
			person = new PersonDTO();
		else {
			ContractDTO updated = owner.updateContract();
			person = updated.getPolicyHolder();
		}
		PersonForm editor = new PersonForm(this, isNewPerson);
		driver.initialize(editor);
		driver.edit(person);
		RootPanel.get().clear();
		RootPanel.get().add(editor);
	}

	public void savePerson(PersonForm form) {
		final PersonDTO updatedPerson = driver.flush();
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<PersonDTO>> violations =
				validator.validate(updatedPerson);
		if(violations.isEmpty()) {
			if(form.isNew())
				restService.addPerson(updatedPerson, new MethodCallback<Long>() {
					@Override
					public void onSuccess(Method method, Long response) {
						updatedPerson.setId(response);
						ContractDTO updatedContract = owner.updateContract(); 
						updatedContract.setPolicyHolder(updatedPerson);
						owner.editContract(updatedContract, isNew);
					}
					@Override
					public void onFailure(Method method, Throwable exception) {
						Window.alert("add Person "+exception.toString()+" "+
								method.getResponse().getStatusCode());				
					}
				});
			else {
				restService.savePerson(updatedPerson, new MethodCallback<Void>() {
					@Override
					public void onSuccess(Method method, Void response) {
										
					}
					@Override
					public void onFailure(Method method, Throwable exception) {
						Window.alert("save Person "+exception.toString()+" "+
								method.getResponse().getStatusCode());					
					}
				});
				ContractDTO updatedContract = owner.updateContract(); 
				updatedContract.setPolicyHolder(updatedPerson);
				owner.editContract(updatedContract, isNew);
			}	
		}
			else
		{
			String message = "";
			for(ConstraintViolation<PersonDTO> item : violations) {
				message = message + item.getMessage() + "\r\n";
				
			}	
			form.setErrors(message);
		}
	}

	public void selectPerson(PersonDTO selected) {
		ContractDTO updatedContract = owner.updateContract(); 
		updatedContract.setPolicyHolder(selected);
		owner.editContract(updatedContract, isNew);
	}
	
	private void initWidgetsProps() {
		List<String> states = new ArrayList<String>();
		states.add(constants.state());
 		state.setAcceptableValues(states);
		postIndex.getElement().setPropertyString("placeholder", constants.postIndex());
		region.getElement().setPropertyString("placeholder", constants.region());
		district.getElement().setPropertyString("placeholder", constants.district());
		locality.getElement().setPropertyString("placeholder", constants.locality());
		street.getElement().setPropertyString("placeholder", constants.street());
		house.getElement().setPropertyString("placeholder", constants.house());
		housing.getElement().setPropertyString("placeholder", constants.housing());
		building.getElement().setPropertyString("placeholder", constants.building());
		apartment.getElement().setPropertyString("placeholder", constants.apartment());
		
	}
	
}
