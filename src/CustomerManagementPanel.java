import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* 탭 메뉴 안에 기능들을 쓰레드로 */

public class CustomerManagementPanel extends JPanel {

	private static final long serialVersionUID = -1319518727223465907L;

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
		JTextField field = new JTextField();
		field.setText("this is customer management panel");
		
		JLabel label = new JLabel();
		label.setText("I am a label.");

		this.add(field);
		this.add(label);
	}

}
