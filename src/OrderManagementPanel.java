import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;


public class OrderManagementPanel extends JPanel {
	
	private static final long serialVersionUID = -6444188729612553039L;
	
	private Date today = null;
	
	private JList<String> menuList = null;
	
	private JTextField uidField = null;
	
	// reference holder
	private MenuController 		menuCtrler = null;
	private CustomerController 	custCtrler = null;
	
	public OrderManagementPanel(MenuController menuCtrler, CustomerController custCtrler) {
		
		this.menuCtrler = menuCtrler;
		menuCtrler.setOrderPanel(this);
		this.custCtrler = custCtrler;
		
		init();
	}

	private void init(){
		setLayout(null);
		
		// today
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		today = new Date();
		
		JLabel date = new JLabel("TODAY : " + df.format(today));
		date.setBounds(10, 35, 200, 25);
		this.add(date);
		
		// customer uid
		JLabel uidLabel = new JLabel("Customer UID : ");
		uidLabel.setBounds(10, 60, 200, 25);
		this.add(uidLabel);
		
		uidField = new JTextField();
		uidField.setBounds(110, 60, 100, 25);
		this.add(uidField);
		
		// retrive menu from menu model;
		menuList = new JList<String>();
		menuList.setListData(menuCtrler.getMenuList());
		menuList.setBounds(10, 100, 100, 100);
		menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.add(menuList);
		
		// buttons
		JButton orderButton = new JButton("Order");
		orderButton.setBounds(10, 300, 100, 35);
		orderButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				order();				
			}
		});
		this.add(orderButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(130, 300, 100, 35);
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// cancel
				uidField.setText("");
			}
		});
		this.add(cancelButton);
	}

	private void order(){
		
		try {	
			// get which menu is selected
			
			String selected = menuList.getSelectedValue();
			System.out.println(selected);
			
			if(selected != null){
			
				int uid = Integer.parseInt(uidField.getText());
				custCtrler.order(uid);
				menuCtrler.order(uid, today, selected);
				
				menuCtrler.save("menu2.txt");
			}
			
		} catch (CustomerNotFoundException e) {
			
			JOptionPane.showMessageDialog(null, "Customer Not Found");
			
		} catch (MenuNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateMenuList(String[] model){
		menuList.setListData(model);
	}
}
