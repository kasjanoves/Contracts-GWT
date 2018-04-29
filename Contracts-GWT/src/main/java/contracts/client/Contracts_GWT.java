package contracts.client;

import org.fusesource.restygwt.client.Defaults;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Contracts_GWT implements EntryPoint {
					
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		Defaults.setServiceRoot("http://localhost:8080/"); 
		RootPanel.get().add(new ContractsListForm());
				
	}

}
