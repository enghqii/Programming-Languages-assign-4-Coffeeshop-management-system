import java.util.ArrayList;

class MenuNotFoundException extends Exception{

	private static final long serialVersionUID = 3374935405187385762L;

	public MenuNotFoundException() {
		super("Menu not found.");
	}
}

public class MenuModel {
	
	private ArrayList<Menu> model = null;

	public MenuModel() {
		
	}
		
	public void setContainer(ArrayList<Menu> container){
		this.model = container;
	}
	
	public ArrayList<Menu> getContainer(){
		return this.model;
	}

}
