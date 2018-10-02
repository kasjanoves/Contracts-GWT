package contracts.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

import contracts.shared.PersonDTO;

public class PersonForm extends Composite implements Editor<PersonDTO> {

	private static PersonFormUiBinder uiBinder = GWT.create(PersonFormUiBinder.class);
	private ContractsConstants constants = GWT.create(ContractsConstants.class);

	interface PersonFormUiBinder extends UiBinder<Widget, PersonForm> {
	}
	
	private ContractForm owner;
	private boolean isNew;
		
	public PersonForm(ContractForm owner, boolean isNew) {
		this.owner=owner;
		this.isNew=isNew;
		initWidget(uiBinder.createAndBindUi(this));
		initWidgetsProps();
	}

	@UiField
	@Ignore
	Label messages;
	
	@UiField
	TextBox name;
	
	@UiField
	TextBox lastName;
	
	@UiField
	TextBox fathersName;
	
	@UiField
	DateBox birthDate;
	
	@UiField
	IntegerBox passSeries;
	
	@UiField
	IntegerBox passNum;
	
	@UiField
	Button saveButton;
	
	@UiField
	Button cancelButton;
	
	@UiHandler("saveButton")
	void onClickSaveBtn(ClickEvent e) {
		owner.savePerson(this);
	}
	
	@UiHandler("cancelButton")
	void onClickCnBtn(ClickEvent e) {
		RootPanel.get().clear();
		RootPanel.get().add(owner);
	}
	
	public void setErrors(String text) {
		this.messages.setText(text);
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	private void initWidgetsProps() {
		name.getElement().setPropertyString("placeholder", "("+constants.name()+")");
		lastName.getElement().setPropertyString("placeholder", "("+constants.lastName()+")");
		fathersName.getElement().setPropertyString("placeholder", "("+constants.fathersName()+")");
	}
	
}
