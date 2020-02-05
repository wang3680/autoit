package uftdemo.com.bean;

public class Stock {
    private String stkcode;
    private String price;
    private String amount;

    private String exchangeType;

    public String getStkcode() {
        return stkcode;
    }

    public void setStkcode(String stkcode) {
        this.stkcode = stkcode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }
}
