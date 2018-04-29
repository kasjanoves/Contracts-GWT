package contracts.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.validation.client.AbstractValidationMessageResolver;
import com.google.gwt.validation.client.UserValidationMessagesResolver;

import contracts.shared.ValidationMessages;

public class ValidationMessagesResolver extends AbstractValidationMessageResolver
		implements UserValidationMessagesResolver {
	
	protected ValidationMessagesResolver() {
		super((ConstantsWithLookup) GWT.create(ValidationMessages.class));
	}

	protected ValidationMessagesResolver(ConstantsWithLookup messages) {
		super((ConstantsWithLookup) GWT.create(ValidationMessages.class));
	}

}
