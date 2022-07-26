
package stockEx;


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import stockEx.cotroller.ProcessOrder;
import stockEx.cotroller.ProcessTransaction;


class PriceDesc implements Comparator<Order> {
	@Override
	public int compare(Order o1, Order o2) {
		return o2.getPrice().compareTo(o1.getPrice());
	}
}

public class Main {
	static String connectionUrl = "jdbc:sqlserver://Localhost:1433;"
							+ "databaseName=stockEx;encrypt=true;trustServerCertificate=true;user=sa;password=sa";
	// Declare the JDBC objects.
	static Connection conn = null;

	static {
		try {
			// Establish the connection.
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");// buoc 1
			conn = DriverManager.getConnection(connectionUrl); // buoc 2

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		String[] lstStock = { "VIN", "SSB", "SHB", "EIB", "MSB", "OCB", "FPT" };
		String[] lstTrader = { "Trader_1", "Trader_2", "Trader_3", "Trader_4", "Trader_5",
								"Trader_6", "Trader_7" };

		Map<String, Stock> hm = new HashMap<>();
		for (String sId : lstStock) {
			Stock st = new Stock(sId);
			hm.put(sId, st);
		}

		Map<String, PriorityQueue<Order>> hm_st_Sellq = new HashMap<>();
		Map<String, PriorityQueue<Order>> hm_st_Buyq = new HashMap<>();

		for (String sIdStock : hm.keySet()) {
			hm_st_Sellq.put(sIdStock, new PriorityQueue<Order>());
			hm_st_Buyq.put(sIdStock, new PriorityQueue<>(new PriceDesc()));
		}

		/* call class query function database */
		ProcessOrder pOrd = new ProcessOrder();
		ProcessTransaction pTrans = new ProcessTransaction();

		int count = 0;

		while (true && count < 5000) {
			// Lệnh mua, bán ngẫu nhiên
			int iIDStock = (int) Math.round(Math.random() * (lstStock.length - 1));
			int iIDTrader = (int) Math.round(Math.random() * (lstTrader.length - 1));

			double x = Math.random();
			int iAmount = (int) Math.round(Math.random() * 100);
			Float price = (float) Math.random() * 1000;
			Order ord;

			if (x < 0.5) {
				ord = new Order(1, lstStock[iIDStock], lstTrader[iIDTrader], iAmount,
										price);
				PriorityQueue<Order> bQ = hm_st_Buyq.get(lstStock[iIDStock]);
				bQ.add(ord);
			} else {
				ord = new Order(2, lstStock[iIDStock], lstTrader[iIDTrader], iAmount,
										price);
				PriorityQueue<Order> sellQ = hm_st_Sellq.get(lstStock[iIDStock]);
				sellQ.add(ord);
			}

			pOrd.insert(conn, ord);

			// Khớp lệnh
			Queue<Order> sQ = hm_st_Sellq.get(lstStock[iIDStock]);
			Queue<Order> bQ = hm_st_Buyq.get(lstStock[iIDStock]);

			// Tạo list chứa tất cả các transantion.
			List<Transaction> transantionList = new ArrayList<>();

			while (!bQ.isEmpty() && !sQ.isEmpty()) {
				Order buyOrder = bQ.peek();
				Order sellOrder = sQ.peek();
				if (buyOrder.getPrice() >= sellOrder.getPrice()) {
					Transaction transantion = new Transaction();
					transantion.getTransID();
					transantion.setIdStock(buyOrder.getsIDStock());
					transantion.setIdBuyer(buyOrder.getsIDTrader());
					transantion.setIdSeller(sellOrder.getsIDTrader());
					transantion.setPrice(sellOrder.getPrice());
					if (buyOrder.getAmount() < sellOrder.getAmount()) {
						transantion.setAmount(buyOrder.getAmount());

						// cập nhật lại số lượng của chứng khoán của queue
						sellOrder.setAmount(sellOrder.getAmount() - buyOrder.getAmount());
						bQ.poll();

					} else if (buyOrder.getAmount() > sellOrder.getAmount()) {
						transantion.setAmount(sellOrder.getAmount());

						// cập nhật lại số lượng của chứng khoán của queue
						buyOrder.setAmount(buyOrder.getAmount() - sellOrder.getAmount());
						sQ.poll();

					} else {
						transantion.setAmount(sellOrder.getAmount());
						bQ.poll();
						sQ.poll();
					}

					transantionList.add(transantion);

					pTrans.queryInsertTrans(conn, transantion);
					System.out.println(transantion);
					count++;

				} else {
					break;
				}

			}

		}
		pTrans.sumPriceTrans(conn);
		System.out.println();

		pTrans.sumPriceOfStock(conn);
		pTrans.listMaxPriceOfStock(conn);
	}

}
