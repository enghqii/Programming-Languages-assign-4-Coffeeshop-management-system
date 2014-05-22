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
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import util.Pair;


public class MenuController {
	
	private MenuModel 	menuModel = null;
	private OrderModel 	orderModel = null;
	
	private Queue<Order> orderQueue = new LinkedList<Order>();
	
	// reference holder
	private OrderManagementPanel orderPanel = null;

	public MenuController() {
		
		this.load("menu.txt");
	}

	// File IO functions
	
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
	
	// util functions
	
    public synchronized String[] getMenuList(){
        
        int cnt = this.menuModel.getContainer().size();
        String[] menuList = new String[cnt];
        
        for(int i = 0; i < cnt; i++){
            menuList[i] = this.menuModel.getContainer().get(i).getName();
        }
        
        return menuList;
    }

	public synchronized Menu findMenu(String menu) throws MenuNotFoundException {
		
		for(Menu aMenu : menuModel.getContainer()){
			
			if (aMenu.getName().compareTo(menu) == 0) {
				return aMenu;
			}
			
		}
		
		throw new MenuNotFoundException();
	}

	// order ManageMent Controls

	public void setOrderPanel(OrderManagementPanel orderManagementPanel) {
		this.orderPanel = orderManagementPanel;
	}
	
	public synchronized void orderAddMenu(int uid, Date timeStamp, Menu menu) {
	
		Order order = new Order(uid, timeStamp, menu.getName(), menu.getPrice());
		orderQueue.add(order);
	}
	
	public synchronized void orderCandel(){
		
		orderQueue.clear();
	}
	
	public synchronized void orderComplete() throws Exception{

		if (orderQueue.size() == 0) {
			throw new Exception("Add order first");
		}
		
		for(Order order : orderQueue){
			orderModel.getConatiner().add(order);
		}
		
		orderQueue.clear();
		
		save("menu2.txt");
	}
	
	// Shop Management Controls
	
	public synchronized void addMenu(String menuName, int price){
		
		Menu menu = new Menu(menuName, price);
		menuModel.getContainer().add(menu);
		
		orderPanel.updateMenuList(getMenuList());

		save("menu2.txt");
	}
	
	public synchronized void deleteMenu(Menu menu){
		menuModel.getContainer().remove(menu);
	}

	public synchronized Map<String, Pair<Integer, Integer>> getSalesData(Date from, Date to) {
		
		ArrayList<Order> salesList = new ArrayList<Order>();

		for (Order order : orderModel.getConatiner()) {

			if (!from.after(order.getTimeStamp())
					&& !to.before(order.getTimeStamp())) {

				salesList.add(order);
			}
		}

		Map<String, Pair<Integer, Integer>> salesData = new TreeMap<String, Pair<Integer, Integer>>();

		for (Order order : salesList) {
			
			String menuName = order.getMenuName();
			
			if (salesData.containsKey(menuName)) {
				
				Pair<Integer, Integer> data = salesData.get(menuName);
				data.setFirst(data.getFirst() + 1);
				data.setSecond(data.getSecond() + order.getPrice());
				
			} else {
				Pair<Integer, Integer> data = new Pair<Integer, Integer>(1, order.getPrice());
				salesData.put(menuName, data);
			}
		}

		return salesData;
	}

	public synchronized void addCoupon(int uid, Date today) {
		
		Order order = new Order(uid, today, "COUPON", 0);
		orderModel.getConatiner().add(order);
	}

}
