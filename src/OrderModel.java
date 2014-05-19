import java.util.ArrayList;


public class OrderModel {
	
	ArrayList<Order> model = null;

	public OrderModel() {
		
	}
	
	public void setContainer(ArrayList<Order> container){
		model = container;
	}
	
	public ArrayList<Order> getConatiner(){
		return this.model;
	}

}
