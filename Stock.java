package stockEx;

public class Stock {
	private String sIDStock;

	Stock(String sId) {
		sIDStock = sId;

	}

	public String getsIDStock() {
		return sIDStock;
	}

	public void setsIDStock(String sIDStock) {
		this.sIDStock = sIDStock;
	}

}
