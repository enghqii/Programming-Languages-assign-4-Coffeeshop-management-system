
public class CustomerController {
	
	private CustomerModel customerModel = null;

	public CustomerController() {
		
		customerModel = new CustomerModel();
		customerModel.load("cust.txt");	
	}
	
	public void newCustomer(){
		
	}
	
	public Customer findCustomer(int uid) throws CustomerNotFoundException {
		
		for (Customer customer : customerModel.getContainer()) {
			if(customer.getUID() == uid) {
				return customer;
			}
		}
		
		throw new CustomerNotFoundException();
	}
	
	public void deleteCustomer(int uid) {
		for (Customer customer : customerModel.getContainer()) {
			if(customer.getUID() == uid) {
				customerModel.getContainer().remove(customer);
				return;
			}
		}
	}
	
	public void registerCustomer(String line){
		Customer cust = new Customer(line);
		
		deleteCustomer(cust.getUID());

		customerModel.getContainer().add(cust);
	}

}
