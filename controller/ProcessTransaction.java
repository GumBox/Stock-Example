
package stockEx.cotroller;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import stockEx.Transaction;


public class ProcessTransaction {
	public void queryInsertTrans(Connection conn, Transaction trans) {
		String insertTrans = "INSERT INTO Transactions (transID, idStock, idBuyer, idSeller, amount, price, trans_date)"
								+ " VALUES ( ?, ?, ?, ?, ?, ?, ?);";

		if (conn != null) {
			try {
				PreparedStatement preSta = conn.prepareStatement(insertTrans);
				preSta.setInt(1, trans.getTransID());
				preSta.setString(2, trans.getIdStock());
				preSta.setString(3, trans.getIdBuyer());
				preSta.setString(4, trans.getIdSeller());
				preSta.setInt(5, trans.getAmount());
				preSta.setFloat(6, trans.getPrice());
				preSta.setDate(7, (Date) trans.getTrans_date());

				preSta.executeUpdate();

				preSta.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void sumPriceTrans(Connection conn) {
		try {
			String queryTrans = "SELECT SUM(price) as sum_Price FROM Transactions";

			Statement preSta = conn.createStatement();

			ResultSet rs = preSta.executeQuery(queryTrans);
			while (rs.next()) {
				float sumPrice = rs.getFloat("sum_Price");
				System.out.format("Sum of transaction: %.2f", sumPrice);
				System.out.println();

			}
			preSta.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sumPriceOfStock(Connection conn) {
		try {
			String queryTrans = "SELECT idStock, SUM(price) as sum_price_stock from Transactions GROUP BY idStock";

			Statement preSta = conn.createStatement();

			ResultSet rs = preSta.executeQuery(queryTrans);
			while (rs.next()) {
				String idStock = rs.getString("idStock");
				float sumPrice = rs.getFloat("sum_price_stock");
				System.out.format("ID Stock: %s = %.2f", idStock, sumPrice);
				System.out.println();
			}

			preSta.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void listMaxPriceOfStock(Connection conn) {
		try {
			String queryTrans = "with s as (select idStock, sum(t.amount * t.price) as sumOfIDStock from Transactions t group by idStock) select idStock, sumOfIDStock from s where sumOfIDStock = (select max(sumOfIDStock) from s )";
			Statement preSta = conn.createStatement();

			ResultSet rs = preSta.executeQuery(queryTrans);
			while (rs.next()) {
				String idStock = rs.getString("idStock");
				Float priceOfIDStock = rs.getFloat("sumOfIDStock");
				System.out.format("ID Stock Max transaction: %s = %.4f", idStock, priceOfIDStock);
				System.out.println();
			}

			preSta.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
