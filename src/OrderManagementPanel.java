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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class OrderManagementPanel extends JPanel {

	private static final long serialVersionUID = -6444188729612553039L;

	private Date today = null;

	private JList<String> menuList = null;

	private JTextField uidField = null;
	private OrderDisplayTextArea orderDisp = null;

	// reference holder
	private MenuController menuCtrler = null;
	private CustomerController custCtrler = null;

	public OrderManagementPanel(MenuController menuCtrler,
			CustomerController custCtrler) {

		this.menuCtrler = menuCtrler;
		menuCtrler.setOrderPanel(this);
		this.custCtrler = custCtrler;

		init();
	}

	private void init() {
		setLayout(null);

		// today
		{
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			today = new Date();

			JLabel date = new JLabel("TODAY : " + df.format(today));
			date.setBounds(10, 25, 200, 25);
			this.add(date);
		}

		{
			// customer uid
			JLabel uidLabel = new JLabel("Customer UID : ");
			uidLabel.setBounds(10, 60, 200, 25);
			this.add(uidLabel);

			uidField = new JTextField();
			uidField.setBounds(110, 60, 150, 25);
			this.add(uidField);

			// retrive menu from menu model;
			menuList = new JList<String>();
			menuList.setListData(menuCtrler.getMenuList());
			menuList.setBounds(110, 100, 150, 180);
			menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			this.add(menuList);
		}

		// buttons
		{
			JButton orderAddButton = new JButton("주문 추가");
			orderAddButton.setBounds(10, 300, 87, 25);
			orderAddButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					orderAdd();
				}
			});
			this.add(orderAddButton);
			
			JButton orderCompleteButton = new JButton("주문 완료");
			orderCompleteButton.setBounds(205, 300, 87, 25);
			orderCompleteButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					orderComplete();
				}
			});
			this.add(orderCompleteButton);

			JButton cancelButton = new JButton("주문 취소");
			cancelButton.setBounds(107, 300, 87, 25);
			cancelButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					orderCancel();
				}
			});
			this.add(cancelButton);
		}
		
		// text area
		{
			orderDisp = new OrderDisplayTextArea(300, 60, 250, 220);
			orderDisp.init();
			this.add(orderDisp);

		}
	}

	private void orderAdd() {
		
		System.out.println("ADD ORDER");

		String selected = menuList.getSelectedValue();
		
		if(selected != null){
			try {
			
				int uid = Integer.parseInt(uidField.getText());
				
				custCtrler.findCustomer(uid);
				Menu menu = menuCtrler.findMenu(selected);
				
				menuCtrler.orderAddMenu(uid, today, menu);
				orderDisp.appendOrder(menu);
				
			} catch (MenuNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Menu Not Found");
			} catch (CustomerNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Customer Not Found");
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Set Customer UID");
			}
		}
	}

	private void orderComplete() {
		
		try {

			menuCtrler.orderComplete();
			
			int uid = Integer.parseInt(uidField.getText());
			boolean coupon = custCtrler.order(uid);
			
			if(coupon){
				//System.out.println("COUPON SENT to " + uid);
				menuCtrler.addCoupon(uid, today);
				JOptionPane.showMessageDialog(null, "send COUPON to " + uid);
			}
			
			orderDisp.init();
			System.out.println("COMPLETE ORDER");
			
		} catch (CustomerNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Customer Not Found");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	private void orderCancel(){
		menuCtrler.orderCandel();
		orderDisp.init();
	}

	public void updateMenuList(String[] model) {
		menuList.setListData(model);
	}
}
