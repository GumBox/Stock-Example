package stockEx;

public class Order implements Comparable<Order> {
	private Integer typeStock;// 1-- mua, 2-- bán
	private String sIDStock;
	private String sIDTrader;
	private Integer amount;
	private Float price;

	Order(Integer typeStock, String sIDStock, String sIDTrader, Integer amount,
							Float price) {

		this.typeStock = typeStock;
		this.sIDStock = sIDStock;
		this.sIDTrader = sIDTrader;
		this.amount = amount;
		this.price = price;
	}

	@Override
	public int compareTo(Order o) {
		return this.getPrice().compareTo(o.getPrice());
	}

	public Integer getType() { return typeStock; }

	public void setType(Integer typeStock) { this.typeStock = typeStock; }

	public String getsIDStock() {
		return sIDStock;
	}

	public void setsIDStock(String sIDStock) {
		this.sIDStock = sIDStock;
	}

	public String getsIDTrader() {
		return sIDTrader;
	}

	public void setsIDTrader(String sIDTrader) {
		this.sIDTrader = sIDTrader;
	}

	public Integer getAmount() { return amount; }

	public void setAmount(Integer amount) { this.amount = amount; }

	public Float getPrice() { return price; }

	public void setPrice(Float price) { this.price = price; }
}
