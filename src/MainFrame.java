import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;


public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 4849503812515818937L;
	
	private JTabbedPane tabbedPane = null;

	public MainFrame() throws HeadlessException {
		init();
	}

	public MainFrame(GraphicsConfiguration gc) {
		super(gc);
		init();
	}

	public MainFrame(String title) throws HeadlessException {
		super(title);
		init();
	}

	public MainFrame(String title, GraphicsConfiguration gc) {
		super(title, gc);
		init();
	}
	
	private void init(){
		
		this.setSize(600, 400);
		this.setTitle("Management System");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tabbedPane = new JTabbedPane();
		
		tabbedPane.add("林巩 包府", new OrderManagementPanel());
		tabbedPane.add("概厘 包府", new ShopManagementPanel());
		tabbedPane.add("绊按 包府", new CustomerManagementPanel());
		
		this.add(tabbedPane );
	}

}
