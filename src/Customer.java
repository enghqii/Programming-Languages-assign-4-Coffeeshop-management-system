import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Customer {
	
	public static DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	
	private int 	uid = -1;
	
	private Date 	regDay;
	
	private String 	name;
	private String	phone;
	private Date 	birthDay;

	public Customer() {
		
	}
	
	public Customer(String line) {
		
		String[] token = line.split("\\|");
		
		try{
		
		uid 		= Integer.parseInt(token[0]);
		regDay 		= df.parse(token[1]);
		
		name 		= token[2];
		phone 		= token[3];
		birthDay 	= df.parse(token[4]);
		
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	public String toString(){
		
		String str ="";
		str = uid + "|" + df.format(regDay) + "|" + name + "|" + phone + "|" + df.format(birthDay);
		
		return str;
	}
	
	public int getUID() {
		return uid;
	}
	
	public String getName(){
		return name;
	}
	
	public String getRegisterDayString(){
		return df.format(regDay);
	}
	
	public String getPhoneString(){
		return phone;
	}
	
	public String getBirthDayString(){
		return df.format(birthDay);
	}

}
