
package stockEx.cotroller;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import stockEx.Order;


public class ProcessOrder {

	public void insert(Connection conn, Order ord) {
		String insertQuery = "INSERT INTO Orders (type_stock,  sIDStock,  sIDTrader,  amount, price) "
								+ " VALUES(?, ?, ?, ?, ?);";
		if (conn != null) {
			try {
				PreparedStatement preSta = conn.prepareStatement(insertQuery);
				preSta.setInt(1, ord.getType());
				preSta.setString(2, ord.getsIDStock());
				preSta.setString(3, ord.getsIDTrader());
				preSta.setInt(4, ord.getAmount());
				preSta.setFloat(5, ord.getPrice());
				preSta.executeUpdate();

				preSta.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
