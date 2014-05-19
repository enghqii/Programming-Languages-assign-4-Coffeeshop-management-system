
public class Menu {
	
	private String name = "";
	private int price 	= -1;

	public Menu(String name, int price) {
		this.name = name;
		this.price = price;
	}
	
	public String getName(){
		return name;
	}
	
	public int getPrice(){
		return price;
	}

	public String toString(){
		String toReturn = "M|";
		
		toReturn += (name + "|");
		toReturn += price;
		
		return toReturn;
	}
}
