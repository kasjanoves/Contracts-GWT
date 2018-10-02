package contracts.client;

import java.util.List;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import contracts.shared.PersonDTO;

public class SelectPersonForm extends Composite {

	private static SelectPersonFormUiBinder uiBinder = GWT.create(SelectPersonFormUiBinder.class);
	private ContractsConstants constants = GWT.create(ContractsConstants.class);
	private RESTservice restService = GWT.create(RESTservice.class);

	interface SelectPersonFormUiBinder extends UiBinder<Widget, SelectPersonForm> {
	}
		
	private ContractForm owner;
	private PersonDTO selected;
	
	public SelectPersonForm(ContractForm owner) {
		this.owner=owner;
		initWidget(uiBinder.createAndBindUi(this));
		initTable();
		Resource resource = new Resource(Defaults.getServiceRoot());
		((RestServiceProxy) restService).setResource(resource);
	}

	@UiField
	TextBox name;
	
	@UiField
	TextBox lastName;
	
	@UiField
	TextBox fathersName;
	
	@UiField
	CellTable<PersonDTO> personTable;
	
	@UiField
	Button selectButton;
	
	@UiField
	Button newButton;
	
	@UiField
	Button closeButton;
	
	@UiField
	Button searchButton;
	
	@UiHandler("selectButton")
	void onClickSelBtn(ClickEvent e) {
		if(selected!=null) {
			owner.selectPerson(selected);
		}
	}
	
	@UiHandler("newButton")
	void onClickNewBtn(ClickEvent e) {
		owner.editPerson(true);
	}
	
	@UiHandler("closeButton")
	void onClickCloseBtn(ClickEvent e) {
		RootPanel.get().clear();
		RootPanel.get().add(owner);
	}
	
	@UiHandler("searchButton")
	void onClickSrBtn(ClickEvent e) {
		restService.getPerson(name.getSelectedText(),
				fathersName.getSelectedText(),
				lastName.getSelectedText(),
				new MethodCallback<List<PersonDTO>>() {
					@Override
					public void onSuccess(Method method, List<PersonDTO> response) {
						updateTable(response);
					}
					
					@Override
					public void onFailure(Method method, Throwable exception) {
						Window.alert(exception.toString()+" "+
								method.getResponse().getStatusCode());					
					}
				});
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		updateTable();
	}
	
	private void initTable() {
		
		final DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MM.yyyy");
		
		TextColumn<PersonDTO> nameColumn = new TextColumn<PersonDTO>() {
			@Override
			public String getValue(PersonDTO object) {
				return String.valueOf(object.getName());
			}
		}; 
		personTable.addColumn(nameColumn, constants.personInitials());
	
		TextColumn<PersonDTO> birthDateColumn = new TextColumn<PersonDTO>() {
			@Override
			public String getValue(PersonDTO object) {
				return dtf.format(object.getBirthDate());
			}
		}; 
		personTable.addColumn(birthDateColumn, constants.birthDate());
		
		TextColumn<PersonDTO> passColumn = new TextColumn<PersonDTO>() {
			@Override
			public String getValue(PersonDTO object) {
				return String.valueOf(object.getPassSeries() + " "+ object.getPassNum());
			}
		}; 
		personTable.addColumn(passColumn, constants.passData());
		
		personTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

	    // Add a selection model to handle user selection.
	    final SingleSelectionModel<PersonDTO> selectionModel = new SingleSelectionModel<PersonDTO>();
	    personTable.setSelectionModel(selectionModel);
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
	      public void onSelectionChange(SelectionChangeEvent event) {
	    	selected = selectionModel.getSelectedObject();
	      }
	    });
		
	}
	
	private void updateTable() {
		restService.getAllPersons(new MethodCallback<List<PersonDTO>>() {
			@Override
			public void onSuccess(Method method, List<PersonDTO> response) {
				personTable.setRowCount(response.size(), true);
				personTable.setRowData(response);
			}
			@Override
			public void onFailure(Method method, Throwable exception) {
				Window.alert("get all Persons "+
						exception.toString()+" "+
						method.getResponse().getStatusCode()+" "+
						method.getResponse().getText());
				
			}
		});
		
	}
	
	private void updateTable(List<PersonDTO> response) {
		personTable.setRowCount(response.size(), true);
		personTable.setRowData(response);
		
	}
}
