import javax.swing.JTextArea;


public class OrderDisplayTextArea extends JTextArea {
	
	private static final long serialVersionUID = -4192768733193992081L;
	
	private String initStr = null;
	
	private String orderStr = null;
	private String totalStr = null;
	
	private int subTotal = 0;

	public OrderDisplayTextArea(int x, int y, int width, int height) {
		
		this.setBounds(x, y, width, height);
		this.setEditable(false);
	}
	
	public void init(){
		
		subTotal = 0;
		
		this.initStr = "+++++++++++ WELCOME +++++++++++\n";
		this.orderStr = "";
		this.totalStr = "";
		
		this.update();
		
	}
	
	public void appendOrder(Menu menu){
		subTotal += menu.getPrice();
		orderStr += (menu.getName() + "\t" + menu.getPrice() + "\n");
		this.update();
	}
	
	public void buildTotalString(){
		totalStr = "sub total : " + subTotal + "\n"
				+ "tax : " + (int)(subTotal * 0.1) + "\n"
				+ "TOTAL : " + (int)(subTotal * 1.1);
	}
	
	public void update(){
		this.buildTotalString();
		this.setText(initStr + "\n" + orderStr + "\n" + totalStr);
	}
	

}
