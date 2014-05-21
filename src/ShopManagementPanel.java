import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ShopManagementPanel extends JPanel {

	private static final long serialVersionUID = 1497516927596439025L;

	private MenuController menuCtrler = null;

	private JTextField findMenuNameField = null;
	private JTextField menuNameField = null;
	private JTextField priceField = null;

	public ShopManagementPanel(MenuController menuCtrler) {
		this.menuCtrler = menuCtrler;
		init();
	}

	private void init() {
		setLayout(null);

		findMenuNameField = new JTextField();
		findMenuNameField.setBounds(10, 50, 100, 25);
		this.add(findMenuNameField);

		JButton findMenu = new JButton("Find Menu");
		findMenu.setBounds(120, 50, 100, 25);
		findMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				findMenuAction();
			}

		});
		this.add(findMenu);

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

		JButton newMenu = new JButton("new Menu");
		newMenu.setBounds(10, 160, 100, 25);
		newMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				newMenu();
			}
		});
		this.add(newMenu);

		JButton deleteMenu = new JButton("delete");
		deleteMenu.setBounds(10, 190, 100, 25);
		deleteMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deleteMenu();
			}
		});
		this.add(deleteMenu);

		JButton saveMenu = new JButton("save");
		saveMenu.setBounds(10, 220, 100, 25);
		saveMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveMenu();
			}
		});
		this.add(saveMenu);

	}

	private void findMenuAction() {

		String menuName = findMenuNameField.getText();

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
