import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import util.Pair;

public class ShopManagementPanel extends JPanel {

	private static final long serialVersionUID = 1497516927596439025L;

	private MenuController menuCtrler = null;

	private JTextField menuNameField = null;
	private JTextField priceField = null;

	private JTextField dateFrom = null;
	private JTextField dateTo = null;

	private JTextArea salesDisp = null;

	public ShopManagementPanel(MenuController menuCtrler) {
		this.menuCtrler = menuCtrler;
		init();
	}

	private void init() {
		setLayout(null);

		JLabel menuName = new JLabel("Menu name : ");
		menuName.setBounds(10, 85, 100, 25);
		this.add(menuName);

		menuNameField = new JTextField();
		menuNameField.setBounds(100, 85, 100, 25);
		this.add(menuNameField);

		JLabel price = new JLabel("Price : ");
		price.setBounds(10, 115, 100, 25);
		this.add(price);

		priceField = new JTextField();
		priceField.setBounds(100, 115, 100, 25);
		this.add(priceField);

		JButton newMenu = new JButton("새 메뉴");
		newMenu.setBounds(10, 160, 100, 25);
		newMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				newMenu();
			}
		});
		this.add(newMenu);

		JButton findMenu = new JButton("메뉴 검색");
		findMenu.setBounds(120, 160, 100, 25);
		findMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				findMenuAction();
			}

		});
		this.add(findMenu);

		JButton deleteMenu = new JButton("메뉴 삭제");
		deleteMenu.setBounds(10, 190, 100, 25);
		deleteMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deleteMenu();
			}
		});
		this.add(deleteMenu);

		JButton saveMenu = new JButton("메뉴 등록");
		saveMenu.setBounds(120, 190, 100, 25);
		saveMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveMenu();
			}
		});
		this.add(saveMenu);

		salesDisp = new JTextArea();
		salesDisp.setEditable(false);
		salesDisp.setBounds(250, 100, 300, 200);
		this.add(salesDisp);

		dateFrom = new JTextField();
		dateFrom.setBounds(250, 30, 200, 25);
		this.add(dateFrom);

		dateTo = new JTextField();
		dateTo.setBounds(250, 60, 200, 25);
		this.add(dateTo);

		JButton salesInfo = new JButton("매출 정보");
		salesInfo.setBounds(470, 60, 100, 25);
		salesInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				displaySalesInfo();
			}
		});
		this.add(salesInfo);

	}

	private void findMenuAction() {

		final String menuName = menuNameField.getText();

		Thread t = new Thread() {

			public void run() {

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				try {
					Menu menuFound = menuCtrler.findMenu(menuName);

					// set menu name field
					menuNameField.setText(menuFound.getName());
					// and price also
					priceField.setText("" + menuFound.getPrice());

				} catch (MenuNotFoundException e) {
					JOptionPane.showMessageDialog(null, "Menu Not Found");
				}

			}
		};
		t.start();
	}

	private void newMenu() {

		Thread t = new Thread() {

			public void run() {

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				menuNameField.setText("");
				priceField.setText("");	
			}
		};
		
		t.start();
	}

	private void deleteMenu() {
		
		final String menuName = menuNameField.getText();

		Thread t = new Thread() {

			public void run() {

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				try {
					Menu menu = menuCtrler.findMenu(menuName);
					menuCtrler.deleteMenu(menu);

				} catch (MenuNotFoundException e) {
					JOptionPane.showMessageDialog(null, "Menu Not Found");
				}

			}
		};
		t.start();
	}

	private void saveMenu() {

		final String menuName = menuNameField.getText();
		final int price = Integer.parseInt(priceField.getText());

		Thread t = new Thread() {

			public void run() {

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}


				try {
					Menu menu = menuCtrler.findMenu(menuName);
					menuCtrler.deleteMenu(menu);

				} catch (MenuNotFoundException e) {

				} finally {
					menuCtrler.addMenu(menuName, price);
				}

			}
		};
		t.start();

	}

	private void displaySalesInfo() {

		final String dateFromString = dateFrom.getText();
		final String dateToString = dateTo.getText();

		Thread t = new Thread() {

			public void run() {

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (dateFromString.matches("[0-9]{4}/[0-9]{2}/[0-9]{2}")
						&& dateToString.matches("[0-9]{4}/[0-9]{2}/[0-9]{2}")) {

					try {

						Date from = Order.df.parse(dateFromString);
						Date to = Order.df.parse(dateToString);

						Map<String, Pair<Integer, Integer>> salesData = menuCtrler
								.getSalesData(from, to);

						{
							int total = 0;
							String out = "";
							Iterator<Entry<String, Pair<Integer, Integer>>> it = salesData
									.entrySet().iterator();

							while (it.hasNext()) {

								Map.Entry<String, Pair<Integer, Integer>> pairs = (Map.Entry<String, Pair<Integer, Integer>>) it
										.next();
								String key = pairs.getKey();
								Pair<Integer, Integer> value = pairs.getValue();

								out += String.format("%-10s %-4d %-10d\n", key,
										value.getFirst(), value.getSecond());
								// System.out.println("{SalesData} " + key +
								// " : " + value.getFirst() + ", " +
								// value.getSecond());

								total += (value.getFirst() * value.getSecond());
							}

							out += ("TOTAL : " + total);

							salesDisp.setText(out);
						}

					} catch (ParseException e) {
						JOptionPane.showMessageDialog(null,
								"Date format not matched");
						return;
					}

					System.out.println("display sales info");

				} else {
					JOptionPane.showMessageDialog(null,
							"Date format not matched");
					return;
				}

			}
		};
		t.start();
	}
}
