import java.awt.Dimension;
import java.awt.LayoutManager;
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
	
	//private CustomerModel customerModel = null;
	private CustomerController customerCtrler = null;

	public CustomerManagementPanel() {
		init();
	}

	public CustomerManagementPanel(LayoutManager layout) {
		super(layout);
		init();
	}

	public CustomerManagementPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		init();
	}

	public CustomerManagementPanel(LayoutManager layout,
			boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
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
				
				uidField.setText("");
				nameField.setText("");
				regDayField.setText(Customer.df.format(new Date()));
				phoneField.setText("");
				birthField.setText("");
			}
		});
		this.add(newCustomer);
		
		findCustomer = new JButton("Find Customer");
		findCustomer.setBounds(160, 60, 100, 30);
		findCustomer.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
								
				try{
					
					String str = uidField.getText();
					str = str.replaceAll("\\s+","");
					int uid = Integer.parseInt(str);
					Customer customer = customerCtrler.findCustomer(uid);
					
					System.out.println("[FIND] "+customer.toString());
					
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
			
		});
		this.add(findCustomer);
		
		deleteCustomer = new JButton("Delete Customer");
		deleteCustomer.setBounds(270, 60, 100, 30);
		deleteCustomer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String str = uidField.getText();
				int uid = Integer.parseInt(str);
				
				try {
					
					customerCtrler.findCustomer(uid);
					
				} catch (CustomerNotFoundException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					return;
				}
				JOptionPane.showMessageDialog(null, "[" + uid +"] deleting customer");
				
				customerCtrler.deleteCustomer(uid);
			}
		});
		this.add(deleteCustomer);
		
		registerCustomer = new JButton("Register Customer");
		registerCustomer.setBounds(380, 60, 100, 30);
		registerCustomer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
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
		});
		this.add(registerCustomer);

		// UID
		JLabel uidLabel = new JLabel();
		uidLabel.setBounds(50, 120, 50, 25);
		uidLabel.setText("UID:");
		this.add(uidLabel);
		
		uidField = new JTextField();
		uidField.setBounds(100, 120, 120, 25);
		this.add(uidField);
		
		// name
		JLabel nameLabel = new JLabel();
		nameLabel.setBounds(50, 150, 50, 25);
		nameLabel.setText("Name:");
		this.add(nameLabel);
		
		nameField = new JTextField();
		nameField.setBounds(100, 150, 120, 25);
		this.add(nameField);
		
		// phone
		JLabel phoneLabel = new JLabel();
		phoneLabel.setBounds(50, 180, 50, 25);
		phoneLabel.setText("Phone:");
		this.add(phoneLabel);
		
		phoneField = new JTextField();
		phoneField.setBounds(100, 180, 120, 25);
		this.add(phoneField);
		
		// regDay
		JLabel regDayLabel = new JLabel();
		regDayLabel.setBounds(50, 210, 50, 25);
		regDayLabel.setText("regDay:");
		this.add(regDayLabel);

		regDayField = new JTextField();
		regDayField.setBounds(100, 210, 120, 25);
		this.add(regDayField);

		// birthDay
		JLabel birthLabel = new JLabel();
		birthLabel.setBounds(50, 240, 50, 25);
		birthLabel.setText("birth:");
		this.add(birthLabel);

		birthField = new JTextField();
		birthField.setBounds(100, 240, 120, 25);
		this.add(birthField);

		customerCtrler = new CustomerController();
	}

}
