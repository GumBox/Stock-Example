package stockEx;


import java.util.Date;


public class Transaction {
	private int transID;
	static int ID = 0;
	private String idStock;
	private String idBuyer;
	private String idSeller;
	private int amount;
	private float price;
	private Date trans_date;

	public Transaction() {
		this.transID = ++ID;
	}

	public Transaction(String idStock, String idBuyer, String idSeller, int amount,
							float price) {
		this.transID = ++ID;
		this.idStock = idStock;
		this.idBuyer = idBuyer;
		this.idSeller = idSeller;
		this.amount = amount;
		this.price = price;
	}

	public int getTransID() { return transID; }

	public String getIdStock() { return idStock; }

	public void setIdStock(String idStock) { this.idStock = idStock; }

	public String getIdBuyer() { return idBuyer; }

	public void setIdBuyer(String idBuyer) { this.idBuyer = idBuyer; }

	public String getIdSeller() { return idSeller; }

	public void setIdSeller(String idSeller) { this.idSeller = idSeller; }

	public int getAmount() { return amount; }

	public void setAmount(int amount) { this.amount = amount; }

	public float getPrice() { return price; }

	public void setPrice(float price) { this.price = price; }

	public Date getTrans_date() { return trans_date; }

	public void setTrans_date(Date trans_date) { this.trans_date = trans_date; }

	@Override
	public String toString() {
		return "Transantion { " + "transID=" + ID + ", idStock='" + idStock + '\''
								+ ", idBuyer='" + idBuyer + '\'' + ", idSeller='"
								+ idSeller + '\'' + ", amount=" + amount + ", price="
								+ price + ", date=" + trans_date + " }";
	}
}
