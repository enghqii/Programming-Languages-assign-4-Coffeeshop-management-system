import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Order {
	
	public static DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	
	private int 	uid 		= 0;
	private Date 	timeStamp 	= null;
	private String 	menuName 	= null;
	private int		price 		= 0; 

	public Order(int uid, Date timeStamp, String menuName, int price) {
		
		this.uid 		= uid;
		this.timeStamp 	= timeStamp;
		this.menuName 	= menuName;
		this.price 		= price;
	}

	public String toString(){
		
		String toReturn = "O|";
		
		toReturn += (uid + "|");
		toReturn += (df.format(timeStamp) + "|");
		toReturn += (menuName + "|");
		toReturn += price;
		
		return toReturn;
	}
	
	public Date getTimeStamp(){
		return timeStamp;
	}
	
	public String getMenuName(){
		return menuName;
	}
	
	public int getPrice(){
		return price;
	}
}
