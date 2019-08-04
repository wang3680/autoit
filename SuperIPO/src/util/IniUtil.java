package util;

import bean.IPOInfo;
import bean.Stock;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

public class IniUtil {

	public String readByPathName(String path, String config, String name) {
		String  str = null;
		try {
			Wini ini = new Wini(new File(path));
			str = ini.get(config, name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return str;
	}
	public Stock readBeanByPathName(String path, String config) {
		Stock  stk = new Stock();
		try {
			Wini ini = new Wini(new File(path));
			String a = ini.get(config).get("stkcode");
			stk.setStkcode(a);
			stk.setPrice(ini.get(config).get("price"));
			stk.setAmount(ini.get(config).get("amount"));
			if (config.equalsIgnoreCase("sh")){
				stk.setExchangeType("1");
			}else {
				stk.setExchangeType("2");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return stk;
	}
	public void writeByPathName(String path, String config, String name,String data) {
		String  str = null;
		try {
			Wini ini = new Wini(new File(path));
			ini.put(config, name, data);
			ini.store();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	public void writeByPathName(String path, String config, String [] data) {
		String  str = null;
		try {
			Wini ini = new Wini(new File(path));
			ini.put(config,"stkcode", data[0]);
			ini.put(config,"price", data[1]);
			ini.put(config,"amount", data[2]);
			ini.store();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public String [] writeByPathName(String path, IPOInfo ipoInfo, String textFild) {
		String upPrices = ipoInfo.getUpPrice();
		System.out.println("click:"+upPrices);
			Double a = Double.parseDouble(textFild)/Double.parseDouble(upPrices);
			String b = new java.text.DecimalFormat("#00").format(a);
			int c = Integer.parseInt(b);
			String d = c/100*100+"";
//			alertInfo.f_alert_informationDialog("设置完成!",d);
			String [] e = new String[3];
			e[0] = ipoInfo.getStock().subSequence(0,6)+"";
			e[1] = ipoInfo.getUpPrice();
			e[2] = d;

				if(ipoInfo.getType().equalsIgnoreCase("上海")){
					writeByPathName(path,"SH",e);
				}else{
					writeByPathName(path,"SZ",e);
				}
		return e;
	}
	}

