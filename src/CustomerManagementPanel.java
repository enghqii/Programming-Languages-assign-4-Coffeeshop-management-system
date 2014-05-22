import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* 탭 메뉴 안에 기능들을 쓰레드로 */

public class CustomerManagementPanel extends JPanel {

	private static final long serialVersionUID = -1319518727223465907L;
	
	private JButton newCustomer 		= null;
	private JButton findCustomer 		= null;
	private JButton deleteCustomer 		= null;
	private JButton registerCustomer 	= null;	
	
	private JTextField uidField 	= null;
	private JTextField nameField 	= null;
	private JTextField regDayField 	= null;
	private JTextField phoneField 	= null;
	private JTextField birthField 	= null;
	
	// reference holder
	private CustomerController customerCtrler = null;

	public CustomerManagementPanel(CustomerController customerCtrler) {
		
		this.customerCtrler = customerCtrler;
		
		init();
	}
	
	// building UI
	private void init(){

		setLayout(null);
		
		newCustomer = new JButton("New Customer");
		newCustomer.setBounds(50, 60, 100, 30);
		newCustomer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				newCustomer();
			}
		});
		this.add(newCustomer);
		
		findCustomer = new JButton("Find Customer");
		findCustomer.setBounds(160, 60, 100, 30);
		findCustomer.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
								
				findCustomer();
			}
			
		});
		this.add(findCustomer);
		
		deleteCustomer = new JButton("Delete Customer");
		deleteCustomer.setBounds(270, 60, 100, 30);
		deleteCustomer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				deleteCustomer();
			}
		});
		this.add(deleteCustomer);
		
		registerCustomer = new JButton("Register Customer");
		registerCustomer.setBounds(380, 60, 100, 30);
		registerCustomer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				registerCustomer();				
			}
		});
		this.add(registerCustomer);

		// UID
		JLabel uidLabel = new JLabel();
		uidLabel.setBounds(50, 120, 50, 25);
		uidLabel.setText("UID:");
		this.add(uidLabel);
		
		uidField = new JTextField();
		uidField.setBounds(100, 120, 300, 25);
		this.add(uidField);
		
		// name
		JLabel nameLabel = new JLabel();
		nameLabel.setBounds(50, 150, 50, 25);
		nameLabel.setText("Name:");
		this.add(nameLabel);
		
		nameField = new JTextField();
		nameField.setBounds(100, 150, 300, 25);
		this.add(nameField);
		
		// phone
		JLabel phoneLabel = new JLabel();
		phoneLabel.setBounds(50, 180, 50, 25);
		phoneLabel.setText("Phone:");
		this.add(phoneLabel);
		
		phoneField = new JTextField();
		phoneField.setBounds(100, 180, 300, 25);
		this.add(phoneField);
		
		// regDay
		JLabel regDayLabel = new JLabel();
		regDayLabel.setBounds(50, 210, 50, 25);
		regDayLabel.setText("regDay:");
		this.add(regDayLabel);

		regDayField = new JTextField();
		regDayField.setBounds(100, 210, 300, 25);
		this.add(regDayField);

		// birthDay
		JLabel birthLabel = new JLabel();
		birthLabel.setBounds(50, 240, 50, 25);
		birthLabel.setText("birth:");
		this.add(birthLabel);

		birthField = new JTextField();
		birthField.setBounds(100, 240, 300, 25);
		this.add(birthField);
	}
	
	private void newCustomer(){
		
		uidField.setText("");
		nameField.setText("");
		regDayField.setText(Customer.df.format(new Date()));
		phoneField.setText("");
		birthField.setText("");
	}

	private void findCustomer() {

		String str = uidField.getText();
		final int uid = Integer.parseInt(str);

		Thread t = new Thread() {
			@Override
			public void run() {

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				try {
					
					Customer customer = customerCtrler.findCustomer(uid);

					System.out.println("[FIND] " + customer.toString());

					nameField.setText(customer.getName());
					regDayField.setText(customer.getRegisterDayString());
					phoneField.setText(customer.getPhoneString());
					birthField.setText(customer.getBirthDayString());

				} catch (CustomerNotFoundException e1) {

					JOptionPane.showMessageDialog(null, e1.getMessage());
					System.out.println("Customer Not Found.");

					nameField.setText("");
					regDayField.setText(""); // regday to be now day
					phoneField.setText("");
					birthField.setText("");
				}

			}
		};
		t.start();
	}

	private void deleteCustomer() {

		String str = uidField.getText();
		final int uid = Integer.parseInt(str);

		Thread t = new Thread() {
			@Override
			public void run() {

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				try {

					customerCtrler.findCustomer(uid);

				} catch (CustomerNotFoundException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					return;
				}
				JOptionPane.showMessageDialog(null, "[" + uid
						+ "] deleting customer");

				customerCtrler.deleteCustomer(uid);

			}
		};
		t.start();
	}

	private void registerCustomer() {

		Thread t = new Thread() {
			@Override
			public void run() {

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				if (regDayField.getText().matches("[0-9]{4}/[0-9]{2}/[0-9]{2}") == false) {
					// throw
					JOptionPane.showMessageDialog(null,
							"register day fmt is not match");
					return;
				}
				if (birthField.getText().matches("[0-9]{4}/[0-9]{2}/[0-9]{2}") == false) {
					// throw
					JOptionPane.showMessageDialog(null,
							"birth day fmt is not match");
					return;
				}
				if (phoneField.getText().matches("[0-9]{3}-[0-9]{4}-[0-9]{4}") == false) {
					// throw
					JOptionPane.showMessageDialog(null,
							"phone fmt is not match");
					return;
				}

				String line = "";

				int uid = Integer.parseInt(uidField.getText());
				line += (uid + "|");
				String regDay = regDayField.getText();
				line += (regDay + "|");
				String name = nameField.getText();
				line += (name + "|");
				String phone = phoneField.getText();
				line += (phone + "|");
				String birth = birthField.getText();
				line += (birth + "|");

				customerCtrler.registerCustomer(line);
			}
		};
		t.start();
	}
}
