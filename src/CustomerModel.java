import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class CustomerNotFoundException extends Exception {
	
	private static final long serialVersionUID = 8996413759258260866L;

	public CustomerNotFoundException() {
		super("Customer not found.");
	}
}

public class CustomerModel {

	private ArrayList<Customer> model = null;

	public CustomerModel() {

		model = new ArrayList<Customer>();
	}

	public synchronized void load(String fileName) {
		
		model.clear();

		try {

			File cust = new File(fileName);
			BufferedReader custReader = new BufferedReader(new FileReader(cust));

			while (true) {
				String line = custReader.readLine();
				if (line == null)
					break;

				System.out.println(line);

				// create 'customer' and add to model
				Customer customer = new Customer(line);
				model.add(customer);
			}

			custReader.close();

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	public synchronized void save(String fileName) {

		try {
			
			File cust = new File(fileName);
			BufferedWriter custWriter = new BufferedWriter(new FileWriter(cust, false));
			// craete file from 1st
			
			for(Customer customer : model) {
				custWriter.write( customer.toString() );
				custWriter.newLine();
			}
			
			custWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Customer findCustomer(int uid) throws CustomerNotFoundException {

		for (Customer customer : model) {
			if(customer.getUID() == uid) {
				return customer;
			}
		}
		
		throw new CustomerNotFoundException();
	}
}
