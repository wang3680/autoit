package demo.controller;

import demo.bean.AccessToken;
import demo.util.HttpClientUtil;
import demo.util.JacksonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 行情
 * 
 * @author luopeng12856 2016—05-26
 */
public class MarketPriceController {
	private static String APPKEY = "70360e03-7a4e-4dd0-bae7-493039227969";
	private static String APPSECRET = "a71dbad8-32a5-413a-b013-e083c21a8229";

	private static String BASIC = "Basic ";

	AccessToken accesstoken ;

	public MarketPriceController() throws UnsupportedEncodingException {
		// 客户端凭证模式 获取令牌
		Map<String, String> token_map = new HashMap<String, String>();
		token_map.put("grant_type", "client_credentials");// 客户端凭证模式时，必须为“client_credentials”；
		String tokenResult = HttpClientUtil.sendPost(HttpClientUtil.URL
						+ "/oauth2/oauth2/token", token_map, HttpClientUtil.CHARSET,
				HttpClientUtil.CHARSET, null,
				HttpClientUtil.Base64(APPKEY, APPSECRET, BASIC), "获取公共令牌");
		accesstoken = JacksonUtils.json2Object(tokenResult,
				AccessToken.class);
	}

	public static void main(String[] args) throws IOException,
			KeyManagementException, NoSuchAlgorithmException {
		MarketPriceController marketController = new MarketPriceController();
		marketController.getCodePrice("600570.SS");
	}

	public String getIPOInfo() throws IOException, KeyManagementException,
			NoSuchAlgorithmException {

		// IPO行情报价
		Map<String, String> IPO_map = new HashMap<String, String>();
		// real_map.put("en_prod_code", "600570.SS");//股票代码
		IPO_map.put("fields",
				"secu_code,secu_abbr,list_date,issue_price,continu_open_num,up_rate,open_flag");// 字段集合
		IPO_map.put("page_count", "30");// 字段集合
		String a = HttpClientUtil.sendGet(HttpClientUtil.URL
				+ "/info/v3/new_share_performance", IPO_map,
				HttpClientUtil.CHARSET, null, HttpClientUtil.BEARER
						+ accesstoken.getAccess_token(), "IPO行情报价");
		return a;
	}
	public List<Integer> getCodePrice(String code){
		 //行情报价
		 Map<String, String> real_map = new HashMap<String, String>();
		 real_map.put("en_prod_code", code);//股票代码
		 real_map.put("fields","last_px,business_amount");//字段集合
		String a = HttpClientUtil.sendGet(HttpClientUtil.URL + "/quote/v1/real",
		 real_map, HttpClientUtil.CHARSET, null,
		 HttpClientUtil.BEARER + accesstoken.getAccess_token(), "行情报价");
		JSONObject jsonO = JSONObject.fromObject(a);
		JSONArray b = jsonO.getJSONObject("data").getJSONObject("snapshot").getJSONArray(code);
//		JSONObject b = jsonO.getJSONObject("data").getJSONObject("snapshot").getJSONObject(code);
		List<Integer> c = JSONArray.toList(b);
		 return c;
	}

}
