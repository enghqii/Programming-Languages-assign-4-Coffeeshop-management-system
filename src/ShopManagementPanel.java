import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ShopManagementPanel extends JPanel {

	private static final long serialVersionUID = 1497516927596439025L;

	private MenuController menuCtrler = null;

	private JTextField menuNameField = null;
	private JTextField priceField = null;
	
	private JTextArea salesDisp = null;

	public ShopManagementPanel(MenuController menuCtrler) {
		this.menuCtrler = menuCtrler;
		init();
	}

	private void init() {
		setLayout(null);		

		JLabel menuName = new JLabel("Menu name : ");
		menuName.setBounds(10, 85, 100, 25);
		this.add(menuName);

		menuNameField = new JTextField();
		menuNameField.setBounds(100, 85, 100, 25);
		this.add(menuNameField);

		JLabel price = new JLabel("Price : ");
		price.setBounds(10, 115, 100, 25);
		this.add(price);

		priceField = new JTextField();
		priceField.setBounds(100, 115, 100, 25);
		this.add(priceField);

		JButton newMenu = new JButton("새 메뉴");
		newMenu.setBounds(10, 160, 100, 25);
		newMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				newMenu();
			}
		});
		this.add(newMenu);
		
		JButton findMenu = new JButton("메뉴 검색");
		findMenu.setBounds(120, 160, 100, 25);
		findMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				findMenuAction();
			}

		});
		this.add(findMenu);

		JButton deleteMenu = new JButton("메뉴 삭제");
		deleteMenu.setBounds(10, 190, 100, 25);
		deleteMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deleteMenu();
			}
		});
		this.add(deleteMenu);

		JButton saveMenu = new JButton("메뉴 등록");
		saveMenu.setBounds(120, 190, 100, 25);
		saveMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveMenu();
			}
		});
		this.add(saveMenu);
		
		salesDisp = new JTextArea();
		salesDisp.setEditable(false);
		salesDisp.setBounds(250, 100, 300, 200);
		this.add(salesDisp);
		
		JButton salesInfo = new JButton("매출 정보");
		salesInfo.setBounds(250, 70, 100, 25);
		this.add(salesInfo);

	}

	private void findMenuAction() {

		String menuName = menuNameField.getText();

		try {
			Menu menuFound = menuCtrler.findMenu(menuName);

			// set menu name field
			menuNameField.setText(menuFound.getName());
			// and price also
			priceField.setText("" + menuFound.getPrice());

		} catch (MenuNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Menu Not Found");
		}
	}

	private void newMenu() {

		menuNameField.setText("");
		priceField.setText("");
	}

	private void deleteMenu() {

	}

	private void saveMenu() {
		
		// TODO : 찾아서 있으면 바꾸고 없으면 add
		
		String menuName = menuNameField.getText();
		int price = Integer.parseInt(priceField.getText());
		
		menuCtrler.addMenu(menuName, price);
	}
}
