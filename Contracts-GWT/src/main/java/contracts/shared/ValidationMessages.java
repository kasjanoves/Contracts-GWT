package contracts.shared;

public interface ValidationMessages extends org.hibernate.validator.ValidationMessages {

	@DefaultStringValue("contract number must not be 0")
	@Key("contract.number.empty")
    String contract_number_empty();
	
	@DefaultStringValue("contract number is too big")
	@Key("contract.number.toobig")
    String contract_number_toobig();
	
	@DefaultStringValue("empty contract date")
	@Key("contract.date")
    String contract_date();
	
	@DefaultStringValue("contract person not selected")
	@Key("contract.person")
    String contract_person();
	
	@DefaultStringValue("empty calculation")
	@Key("contract.calc")
    String contract_calc();
	
	@DefaultStringValue("empty adress")
	@Key("contract.adress")
    String contract_adress();
	
	@DefaultStringValue("empty postal index")
	@Key("adress.state")
    String adress_state();
	
	@DefaultStringValue("empty region")
	@Key("adress.region")
    String adress_region();
	
	@DefaultStringValue("empty locality")
	@Key("adress.loc")
    String adress_loc();
	
	@DefaultStringValue("empty street")
	@Key("adress.str")
    String adress_str();
	
	@DefaultStringValue("empty apartment")
	@Key("adress.apartm")
    String adress_apartm();
	
	@DefaultStringValue("empty name")
	@Key("person.name")
    String person_name();
	
	@DefaultStringValue("empty last name")
	@Key("person.lastName")
    String person_lastName();
	
	@DefaultStringValue("empty birth date")
	@Key("person.birthDate")
    String person_birthDate();
	
	@DefaultStringValue("empty pass series")
	@Key("person.passSeries")
    String person_passSeries();
	
	@DefaultStringValue("empty pass num")
	@Key("person.passNum")
    String person_passNum();
	
	@DefaultStringValue("empty calculation sum")
	@Key("calc.sum")
    String calc_sum();
	
	@DefaultStringValue("empty calculation begin date")
	@Key("calc.beginDate")
    String calc_beginDate();
		
	@DefaultStringValue("empty calculation end date")
	@Key("calc.endDate")
    String calc_endDate();
	
	@DefaultStringValue("empty property type")
	@Key("calc.propertyType")
    String calc_propertyType();
	
	@DefaultStringValue("empty build year")
	@Key("calc.buildYear")
    String calc_buildYear();
	
	@DefaultStringValue("empty area")
	@Key("calc.area")
    String calc_area();
	
	@DefaultStringValue("empty calculation date")
	@Key("calc.calcDate")
    String calc_calcDate();
						
	@DefaultStringValue("empty price")
	@Key("calc.price")
    String calc_price();
		
}
