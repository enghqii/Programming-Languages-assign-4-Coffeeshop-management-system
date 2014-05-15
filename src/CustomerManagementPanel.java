import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* 탭 메뉴 안에 기능들을 쓰레드로 */

public class CustomerManagementPanel extends JPanel {

	private static final long serialVersionUID = -1319518727223465907L;
	
	private CustomerModel customerModel = null;

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
	
	private void init(){

		JLabel label = new JLabel();
		label.setText("UID");
		
		final JTextField field = new JTextField();
		field.setPreferredSize(new Dimension(100, 25));
		//field.setText("          ");
		
		JButton button = new JButton("Find");
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try{
				
					String str = field.getText();
					str = str.replaceAll("\\s+","");
					int uid = Integer.parseInt(str);
					Customer customer = customerModel.findCustomer(uid);
					
					System.out.println("[FIND] "+customer.toString());
					
				} catch (CustomerNotFoundException e1) {
					// TODO : customer not found
					JOptionPane.showMessageDialog(null, e1.getMessage());
					System.out.println("Customer Not Found.");
				}
			}
			
		});

		this.add(label);
		this.add(field);
		this.add(button);
		
		customerModel = new CustomerModel();
		customerModel.load("cust.txt");
	}

}
