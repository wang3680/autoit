package bean;

import java.io.Serializable;
import java.util.List;

public class Account implements Serializable {
    private String account;
    private String passwd;
    private String entrustWay;
    private String sysNodeId;
    private String opStation;

    private String shStock;
    private String shPrice;
    private String shAmount;
    private String szStock;
    private String szPrice;
    private String szAmount;

    /**
     * 2018-11-13
     * 一个账号有多个股票可以委托
     */
    private List<Stock> stockList;

    public String exchangeType;

    public Account(String account, String passwd, String entrustWay, String sysNodeId, String opStation) {
        this.account = account;
        this.passwd = passwd;
        this.entrustWay = entrustWay;
        this.sysNodeId = sysNodeId;
        this.opStation = opStation;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEntrustWay() {
        return entrustWay;
    }

    public void setEntrustWay(String entrustWay) {
        this.entrustWay = entrustWay;
    }

    public String getSysNodeId() {
        return sysNodeId;
    }

    public void setSysNodeId(String sysNodeId) {
        this.sysNodeId = sysNodeId;
    }

    public String getOpStation() {
        return opStation;
    }

    public void setOpStation(String opStation) {
        this.opStation = opStation;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    public String getShStock() {
        return shStock;
    }

    public void setShStock(String shStock) {
        this.shStock = shStock;
    }

    public String getShPrice() {
        return shPrice;
    }

    public void setShPrice(String shPrice) {
        this.shPrice = shPrice;
    }

    public String getShAmount() {
        return shAmount;
    }

    public void setShAmount(String shAmount) {
        this.shAmount = shAmount;
    }

    public String getSzStock() {
        return szStock;
    }

    public void setSzStock(String szStock) {
        this.szStock = szStock;
    }

    public String getSzPrice() {
        return szPrice;
    }

    public void setSzPrice(String szPrice) {
        this.szPrice = szPrice;
    }

    public String getSzAmount() {
        return szAmount;
    }

    public void setSzAmount(String szAmount) {
        this.szAmount = szAmount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "account='" + account + '\'' +
                ", passwd='" + passwd + '\'' +
                ", entrustWay='" + entrustWay + '\'' +
                ", sysNodeId='" + sysNodeId + '\'' +
                ", opStation='" + opStation + '\'' +
                '}';
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }
}
