package contracts.client;

import java.util.ArrayList;
import java.util.HashSet;
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
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.validation.client.impl.Validation;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import contracts.shared.AdressDTO;
import contracts.shared.CalculationDTO;
import contracts.shared.ContractDTO;
import contracts.shared.EstateTypeDTO;

public class ContractsListForm extends Composite {

	private ContractsConstants constants = GWT.create(ContractsConstants.class);
	private static ContractsListFormUiBinder uiBinder = GWT.create(ContractsListFormUiBinder.class);
	private RESTservice restService = GWT.create(RESTservice.class);
	
	interface Driver extends SimpleBeanEditorDriver<ContractDTO, ContractForm> {
	}
	
	interface ContractsListFormUiBinder extends UiBinder<Widget, ContractsListForm> {
	}

	private Driver driver = GWT.create(Driver.class);
		
	private ContractDTO selected;
			
	@UiField
	Button createButton;
	
	@UiField
	Button openButton;
	
	@UiField
	CellTable<ContractDTO> contractsTable;
	
	public ContractsListForm() {
		initWidget(uiBinder.createAndBindUi(this));
		contractsTable.addDomHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				if (selected != null) {
					editContract(selected, false);
				}
			}
		}, DoubleClickEvent.getType());
		Resource resource = new Resource(Defaults.getServiceRoot());
		((RestServiceProxy) restService).setResource(resource);
		initTable();
		
	}
	
	@UiHandler("createButton")
	void onClickCrtBtn(ClickEvent e) {
		ContractDTO newContract = new ContractDTO(null,
				  new CalculationDTO(),
				  new AdressDTO()); 
		editContract(newContract, true);
	}
			
	@UiHandler("openButton")
	void onClickOpnBtn(ClickEvent e) {
		if (selected != null) {
			editContract(selected, false);
		}
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		updateTable();
	}

	public void editContract(ContractDTO contractDTO, boolean isNew) {
		final ContractForm editor = new ContractForm(this, isNew);
		driver.initialize(editor);
		restService.getEstateTypes(new MethodCallback<List<EstateTypeDTO>>() {
			@Override
			public void onSuccess(Method method, List<EstateTypeDTO> response) {
				editor.getEstateTypesWidget().setAcceptableValues(response);
			}
			@Override
			public void onFailure(Method method, Throwable exception) {
				Window.alert("get Estate Types "+exception);				
			}
		});
		driver.edit(contractDTO);
		RootPanel.get().clear();
		RootPanel.get().add(editor);
	}
		
	public ContractDTO updateContract() {
		ContractDTO updated = driver.flush();
		return updated;		
	}
	
	public void saveContract(ContractForm form) {
		ContractDTO updated = driver.flush();
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<?>> violations = new HashSet<ConstraintViolation<?>>();
		violations.addAll(validator.validate(updated));
		violations.addAll(validator.validate(updated.getAdress()));
		violations.addAll(validator.validate(updated.getCalculation()));
		if(updated.getPolicyHolder()!=null)
			violations.addAll(validator.validate(updated.getPolicyHolder()));
		if(violations.isEmpty())
		{
			form.setErrors(null);
			if(form.isNew())
				restService.addContract(updated, new MethodCallback<ContractDTO>() {
					@Override
					public void onSuccess(Method method, ContractDTO response) {
						editContract(response, false);					
					}
					@Override
					public void onFailure(Method method, Throwable exception) {
						Window.alert("addContract "+exception.toString()+" "+
								method.getResponse().getStatusCode());
					}
				});
			else
				restService.saveContract(updated, new MethodCallback<Void>() {
					@Override
					public void onSuccess(Method method, Void response) {
										
					}
					@Override
					public void onFailure(Method method, Throwable exception) {
						Window.alert("saveContract "+exception.toString()+" "+
								method.getResponse().getStatusCode());
						
					}
				});
		}	
			else
		{
			String message = "";
			for(ConstraintViolation<?> item : violations) {
				message = message + item.getMessage() + "\r\n";
				
			}	
			form.setErrors(message);
		}
	}
	
	private void updateTable() {
		restService.getAllContracts(new MethodCallback<List<ContractDTO>>() {
			@Override
			public void onSuccess(Method method, List<ContractDTO> response) {
				contractsTable.setRowCount(response.size(), true);
				contractsTable.setRowData(response);			
			}
			@Override
			public void onFailure(Method method, Throwable exception) {
				Window.alert("get all contracts "+exception.toString()+" "+
						method.getResponse().getStatusCode());			
			}
		});
	}
		
	private void initTable() {
		
		final DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MM.yyyy");
		
		TextColumn<ContractDTO> seriesNumColumn = new TextColumn<ContractDTO>() {
			@Override
			public String getValue(ContractDTO object) {
				return String.valueOf(object.getNumber());
			}
		}; 
		contractsTable.addColumn(seriesNumColumn, constants.seriesNum());
		
		TextColumn<ContractDTO> formDateColumn = new TextColumn<ContractDTO>() {
			@Override
			public String getValue(ContractDTO object) {
				return dtf.format(object.getFormationDate());
			}
		}; 
		contractsTable.addColumn(formDateColumn, constants.formDate());
		
		TextColumn<ContractDTO> holderColumn = new TextColumn<ContractDTO>() {
			@Override
			public String getValue(ContractDTO object) {
				return object.getPolicyHolder().getInitials();
			}
		}; 
		contractsTable.addColumn(holderColumn, constants.holder());
		
		TextColumn<ContractDTO> priceColumn = new TextColumn<ContractDTO>() {
			@Override
			public String getValue(ContractDTO object) {
				return object.getCalculation().getPrice();
			}
		}; 
		contractsTable.addColumn(priceColumn, constants.price());
		
		TextColumn<ContractDTO> actsDatesColumn = new TextColumn<ContractDTO>() {
			@Override
			public String getValue(ContractDTO object) {
				return dtf.format(object.getCalculation().getBeginDate())+" - "
						+ dtf.format(object.getCalculation().getEndDate());
			}
		}; 
		contractsTable.addColumn(actsDatesColumn, constants.actsDate());
		
		contractsTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
	    
	    final SingleSelectionModel<ContractDTO> selectionModel = new SingleSelectionModel<ContractDTO>();
	    contractsTable.setSelectionModel(selectionModel);
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
	      public void onSelectionChange(SelectionChangeEvent event) {
	    	selected = selectionModel.getSelectedObject();
	      }
	    });
		
	}
		
}
