import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ShopManagementPanel extends JPanel {
	
	private static final long serialVersionUID = 1497516927596439025L;

	public ShopManagementPanel() {
		init();
	}

	private void init(){
		setLayout(null);
		
		JTextField findMenuNameField = new JTextField();
		findMenuNameField.setBounds(10, 50, 100, 25);
		this.add(findMenuNameField);
		
		JButton findMenu = new JButton("Find Menu");
		findMenu.setBounds(120, 50, 100, 25);
		this.add(findMenu);
		
		JLabel menuName = new JLabel("Menu name : ");
		menuName.setBounds(10, 85, 100, 25);
		this.add(menuName);
		
		JTextField menuNameField = new JTextField();
		menuNameField.setBounds(100, 85, 100, 25);
		this.add(menuNameField);
		
		JLabel price = new JLabel("Price : ");
		price.setBounds(10, 115, 100, 25);
		this.add(price);
		
		JTextField priceField = new JTextField();
		priceField.setBounds(100, 115, 100, 25);
		this.add(priceField);
		
		JButton newMenu = new JButton("new Menu");
		newMenu.setBounds(10, 160, 100, 25);
		this.add(newMenu);
		
		JButton deleteMenu = new JButton("delete");
		deleteMenu.setBounds(10, 190, 100, 25);
		this.add(deleteMenu);

		JButton saveMenu = new JButton("save");
		saveMenu.setBounds(10, 220, 100, 25);
		this.add(saveMenu);
		
	}
}
