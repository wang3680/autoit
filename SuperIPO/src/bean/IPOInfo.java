package bean;

import demo.controller.MarketController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

public class IPOInfo {

    private StringProperty stock;
	private StringProperty type;
	private StringProperty stockName;

    private StringProperty ipoPrice;//ipo price 
    private StringProperty marketDate;//market date 
    private StringProperty upNum;//up num 
    private StringProperty upPrice;//up price

	private  StringProperty upRate;//总涨幅
	private  StringProperty price;//最新价
	private StringProperty business_amount;//成交数量
	private StringProperty open_flag;//开通功能配置串,是否已开板 1：已开板；2：未开板；


	public IPOInfo(IPOInfoString iPOIString, MarketController marketController){
		String typeTemp = null;
		if(iPOIString.getSecu_code().substring(7,9).equalsIgnoreCase("SS")){
			typeTemp = "上海";
		}else{
			typeTemp = "深圳";
		}
		this.ipoPrice = new SimpleStringProperty(iPOIString.getIssue_price());
		this.type = new SimpleStringProperty(typeTemp);
		this.marketDate = new SimpleStringProperty(iPOIString.getList_date());
		this.stock = new SimpleStringProperty(iPOIString.getSecu_code());
		this.stockName = new SimpleStringProperty(iPOIString.getSecu_abbr());
		this.upNum = new SimpleStringProperty(iPOIString.getContinu_open_num());
		String a  = marketController.getCodePrice(iPOIString.getSecu_code()).get(2)+"";
		Double b = Double.parseDouble(a)*1.1;
		BigDecimal bigDecimal = new BigDecimal(b);
		Double c = bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		this.upPrice = new SimpleStringProperty(c+"");
//		this.price = new SimpleStringProperty(marketController.getCodePrice(iPOIString.getSecu_code()).get(2)+"");
		this.price = new SimpleStringProperty(a);
		this.business_amount = new SimpleStringProperty(marketController.getCodePrice(iPOIString.getSecu_code()).get(3)+"");
		this.upRate = new SimpleStringProperty(iPOIString.getUp_rate());
		String d = iPOIString.getOpen_flag();
		String e = null;
		if(d.equalsIgnoreCase("1")){
			e = "已开";
		}else{
			e = "未开";
		}
		this.open_flag = new SimpleStringProperty(e);
	}

	public String getOpen_flag() {
		return open_flag.get();
	}

	public StringProperty open_flagProperty() {
		return open_flag;
	}

	public void setOpen_flag(String open_flag) {
		this.open_flag.set(open_flag);
	}

	public String getUpRate() {
		return upRate.get();
	}

	public StringProperty upRateProperty() {
		return upRate;
	}

	public void setUpRate(String upRate) {
		this.upRate.set(upRate);
	}

	public String getBusiness_amount() {
		return business_amount.get();
	}

	public StringProperty business_amountProperty() {
		return business_amount;
	}

	public void setBusiness_amount(String business_amount) {
		this.business_amount.set(business_amount);
	}

	public String getStock() {
		return stock.get();
	}
	public void setStock(String stock) {
		this.stock.set(stock);
	}
	public StringProperty stockProperty(){
		return stock;
	}

	public String getType() {
		return type.get();
	}

	public StringProperty typeProperty() {
		return type;
	}

	public void setType(String type) {
		this.type.set(type);
	}

	public String getStockName() {
		return stockName.get();
	}
	public void setStockName(String stockName) {
		this.stockName.set(stockName);
	}
	public StringProperty stockNameProperty(){
		return stockName;
	}
	public String getPrice() {
		return price.get();
	}
	public void setPrice(String price) {
		this.price.set(price);
	}
	public StringProperty priceProperty(){
		return price;
	}
	public String getIpoPrice() {
		return ipoPrice.get();
	}
	public void setIpoPrice(String ipoPrice) {
		this.ipoPrice.set(ipoPrice);
	}
	public StringProperty ipoPriceProperty(){
		return ipoPrice;
	}
	public String getMarketDate() {
		return marketDate.get();
	}
	public void setMarketDate(String marketDate) {
		this.marketDate.set(marketDate);
	}
	public StringProperty marketDateProperty(){
		return marketDate;
	}
	public String getUpNum() {
		return upNum.get();
	}
	public void setUpNum(String upNum) {
		this.upNum.set(upNum);
	}
	public StringProperty upNumProperty(){
		return upNum;
	}
	public String getUpPrice() {
		return upPrice.get();
	}
	public void setUpPrice(String upPrice) {
		this.upPrice.set(upPrice);
	}
	public StringProperty upPriceProperty(){
		return upPrice;
	}
    
}
