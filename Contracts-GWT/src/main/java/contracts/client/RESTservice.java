package contracts.client;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import org.fusesource.restygwt.client.TextCallback;

import contracts.shared.CalculationDTO;
import contracts.shared.ContractDTO;
import contracts.shared.EstateTypeDTO;
import contracts.shared.PersonDTO;

public interface RESTservice extends RestService{
	
	@GET
	@Path("estatetypes")
	void getEstateTypes(MethodCallback<List<EstateTypeDTO>> callback);
	
	@GET
	@Path("contracts")
	void getAllContracts(MethodCallback<List<ContractDTO>> callback);
	
	@GET
	@Path("contracts/{Num}")
	void getContract(@PathParam("Num") long num, 
				MethodCallback<ContractDTO> callback);
	
	@POST
	@Path("contracts/add")
	void addContract(ContractDTO contract,
				MethodCallback<ContractDTO> callback);
	
	@POST
	@Path("contracts/save")
	void saveContract(ContractDTO contract,
				MethodCallback<Void> callback);
	
	@POST
	@Path("contracts/calculate")
	void calculate(CalculationDTO calc,
				MethodCallback<CalculationDTO> callback);
	
	@GET
	@Path("contracts/hello")
	void getHello(TextCallback callback);
	
	@GET
	@Path("persons")
	void getAllPersons(MethodCallback<List<PersonDTO>> callback);
	
	@GET
	@Path("persons/getone")
	void getPerson(@FormParam("name") String name,
				   @FormParam("fathersName") String fathersName,
				   @FormParam("lastName") String lastName,
				   MethodCallback<List<PersonDTO>> callback);
	
	@POST
	@Path("persons/add")
	void addPerson(PersonDTO person,
				MethodCallback<Long> callback);
	
	@POST
	@Path("persons/save")
	void savePerson(PersonDTO person,
				MethodCallback<Void> callback);
}
