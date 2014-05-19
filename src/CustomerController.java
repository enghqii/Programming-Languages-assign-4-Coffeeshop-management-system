
public class CustomerController {
	
	private CustomerModel customerModel = null;

	public CustomerController() {
		
		customerModel = new CustomerModel();
		customerModel.load("cust.txt");	
	}
	
	public synchronized void load(String fileName){
		customerModel.load(fileName);
	}
	
	public synchronized void save(String fileName){
		customerModel.save(fileName);
	}
	
	public synchronized Customer findCustomer(int uid) throws CustomerNotFoundException {
		
		for (Customer customer : customerModel.getContainer()) {
			if(customer.getUID() == uid) {
				return customer;
			}
		}
		
		throw new CustomerNotFoundException();
	}
	
	public synchronized void deleteCustomer(int uid) {
		for (Customer customer : customerModel.getContainer()) {
			if(customer.getUID() == uid) {
				customerModel.getContainer().remove(customer);
				return;
			}
		}
	}
	
	public synchronized void registerCustomer(String line){
		Customer cust = new Customer(line);
		
		deleteCustomer(cust.getUID());

		customerModel.getContainer().add(cust);
	}
	
	public synchronized void order(int uid) throws CustomerNotFoundException {
		
		Customer customer = this.findCustomer(uid);
		
		customer.increaseNOrder();
	}

}
