package contracts.client;

import javax.validation.Validator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.GwtValidation;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;

import contracts.shared.AdressDTO;
import contracts.shared.CalculationDTO;
import contracts.shared.ContractDTO;
import contracts.shared.PersonDTO;

public final class ContractsValidatorFactory extends AbstractGwtValidatorFactory {

	@GwtValidation(value = {ContractDTO.class,	PersonDTO.class,
			AdressDTO.class, CalculationDTO.class})
	  public interface GwtValidator extends Validator {
	}

	@Override
	public AbstractGwtValidator createValidator() {
		return GWT.create(GwtValidator.class);
	}
	

}
