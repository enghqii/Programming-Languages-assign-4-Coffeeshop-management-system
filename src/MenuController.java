import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MenuController {
	
	private MenuModel 	menuModel = null;
	private OrderModel 	orderModel = null;

	public MenuController() {
		
		this.load("menu.txt");
	}

	// load menu and orders
	public synchronized void load(String fileName) {
		
		menuModel 	= new MenuModel();
		ArrayList<Menu> menuList = new ArrayList<Menu>();

		orderModel = new OrderModel();
		ArrayList<Order> orderList = new ArrayList<Order>();

		try {
			
			File menu = new File(fileName);
			BufferedReader menuReader = new BufferedReader(new FileReader(menu));
			
			while (true) {
				
				String line = menuReader.readLine();
				if (line == null)
					break;
				
				//System.out.println("[menu load]" + line);
				
				String[] token = line.split("\\|");

				if (token[0].compareTo("M") == 0) {
					
					// menu
					Menu m = new Menu(token[1], Integer.parseInt(token[2]));
					menuList.add(m);
					
				} else if (token[0].compareTo("O") == 0) {
					
					// order
					DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
					
					int uid = Integer.parseInt(token[1]);
					Date timeStamp = df.parse(token[2]);
					String menuName = token[3];
					int price = Integer.parseInt(token[4]);
					
					Order o = new Order(uid, timeStamp, menuName, price);
					orderList.add(o);
					
				} else {
					// WTF
				}

			}
			
			menuModel.setContainer(menuList);
			orderModel.setContainer(orderList);

			menuReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	public synchronized void save(String fileName){
		
		try {
			
			File menuFile = new File(fileName);
			BufferedWriter menuWriter = new BufferedWriter(new FileWriter(menuFile, false));
			
			for(Menu menu : menuModel.getContainer()){
				menuWriter.write(menu.toString());
				menuWriter.newLine();
			}
			
			menuWriter.newLine();
			
			for(Order order : orderModel.getConatiner()){
				menuWriter.write(order.toString());
				menuWriter.newLine();
			}
			
			menuWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized Menu findMenu(String menu) throws MenuNotFoundException {
		
		for(Menu aMenu : menuModel.getContainer()){
			
			if (aMenu.getName().compareTo(menu) == 0) {
				return aMenu;
			}
			
		}
		
		throw new MenuNotFoundException();
	}
	
	public String[] getMenuList(){
		
		int cnt = this.menuModel.getContainer().size();
		String[] menuList = new String[cnt];
		
		for(int i = 0; i < cnt; i++){
			menuList[i] = this.menuModel.getContainer().get(i).getName();
		}
		
		return menuList;
	}
	
	public void order(int uid, Date timeStamp, String menuName) throws MenuNotFoundException {
		Menu menu = this.findMenu(menuName);
		
		Order order = new Order(uid, timeStamp, menuName, menu.getPrice());
		orderModel.getConatiner().add(order);
	}

}
