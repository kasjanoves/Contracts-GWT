package contracts.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.FailedResponseException;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;
import org.fusesource.restygwt.client.TextCallback;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
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
		
		RESTservice restService = GWT.create(RESTservice.class);
		Resource resource = new Resource(Defaults.getServiceRoot());
		((RestServiceProxy) restService).setResource(resource);
		restService.getHello(new TextCallback() {
			@Override
			public void onSuccess(Method method, String response) {
				Window.alert(response);				
			}
			@Override
			public void onFailure(Method method, Throwable exception) {
				Window.alert(exception.toString()+" "+
						method.getResponse().getStatusCode());				
			}
		});
		
		RootPanel.get().add(new ContractsListForm());
				
	}

}
