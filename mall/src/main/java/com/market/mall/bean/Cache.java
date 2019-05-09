package com.market.mall.bean;

public class Cache {
	private Long pid;
	private String pname;
	private String amount;
	private String pprice;
	private String pimg;
	private Double money;
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPprice() {
		return pprice;
	}
	public void setPprice(String pprice) {
		this.pprice = pprice;
	}
	public String getPimg() {
		return pimg;
	}
	public void setPimg(String pimg) {
		this.pimg = pimg;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	@Override
	public String toString() {
		return "Cache [pid=" + pid + ", pname=" + pname + ", amount=" + amount + ", pprice=" + pprice + ", pimg=" + pimg
				+ ", money=" + money + "]";
	}
	
}
