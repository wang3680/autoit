package demo.controller;


import demo.bean.AccessToken;
import demo.bean.Account;
import demo.util.HttpClientUtil;
import demo.util.JacksonUtils;

import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 开户  仿真 
 * @author luopeng12856
 * 2016—05-26
 */
public class TradingController {
	private static String APPKEY    = "36925ca9-6768-47f7-8124-fa2a9a8a2c7d";
	private static String APPSECRET = "d392dff7-14f5-4437-a0b2-5386fa72162f";
	private static String BASIC     = "Basic ";

	public static void main(String[] args) throws UnsupportedEncodingException, KeyManagementException, NoSuchAlgorithmException {

		// 客户端凭证模式 获取令牌
		Map<String, String> token_map = new HashMap<String, String>();
		token_map.put("grant_type", "client_credentials");// 客户端凭证模式时，必须为“client_credentials”；
		String tokenResult = HttpClientUtil.sendPost(HttpClientUtil.URL + "/oauth2/oauth2/token", token_map,
				HttpClientUtil.CHARSET, HttpClientUtil.CHARSET, null, HttpClientUtil.Base64(APPKEY, APPSECRET, BASIC),
				"获取公共令牌");

		// 共有令牌返回数据json解析
		AccessToken accesstoken = JacksonUtils.json2Object(tokenResult, AccessToken.class);

		// 开户
		Map<String, String> openaccount_map = new HashMap<String, String>();
		openaccount_map.put("sendercomp_id", "91000"); // 发送机构编码
		openaccount_map.put("apply_no", "Z091000");    // 交易申请编号
		openaccount_map.put("inst_user_id", "2");      // 三方机构用户编号  (如获取私有令牌报密码错误 请更换inst_user_id参数值)
		openaccount_map.put("accountbusinsys_no", "1000");// 账号业务系统类型 1000表示证券交易
		String openaccount_result = HttpClientUtil.sendPost(HttpClientUtil.URL + "/sim/V1/sim_openaccount",
				openaccount_map, HttpClientUtil.CHARSET, HttpClientUtil.CHARSET, null,
				HttpClientUtil.BEARER + accesstoken.getAccess_token(), "开户");
		openaccount_result = openaccount_result.replace("{\"data\":[", "").replace("]}", "");

		// json 开户解析
		Account account = JacksonUtils.json2Object(openaccount_result, Account.class);

		// 通过开户的资金账号 密码 获取私有令牌
		Map<String, String> private_token_map = new HashMap<String, String>();
		private_token_map.put("targetcomp_id", "91000"); // 发送机构编号
		private_token_map.put("sendercomp_id", "91000"); // 目标机构编号
		private_token_map.put("targetbusinsys_no", "1000"); // 证券交易必须填1000
		private_token_map.put("op_station", "127.0.0.1");// 客户端登录时的站点信息，移动终端优先填写手机号码，无法获取或其它平台填写IP地址
		private_token_map.put("input_content", "6"); // 帐号类别, 1表示资金帐号，6表示客户号
		if (account != null) {
			private_token_map.put("account_content", account.getAccount_content());// 资金帐号或者客户号
			private_token_map.put("password", account.getPassword()); // 密码
		}
		//private_token_map.put("password",SecretToolkit.password("111111"));
		//private_token_map.put("secret_key_info", SecretToolkit.secret_key_info());
		String private_tokenResult = HttpClientUtil.sendPost(HttpClientUtil.URL + "/oauth2/oauth2/oauthacct_trade_bind",
				private_token_map, HttpClientUtil.CHARSET, HttpClientUtil.CHARSET, null,
				HttpClientUtil.Base64(APPKEY, APPSECRET, BASIC), "获取私有令牌");
		AccessToken private_accesstoken = JacksonUtils.json2Object(private_tokenResult, AccessToken.class);

		Map<String, String> enter_map = new HashMap<String, String>();
		enter_map.put("sendercomp_id", "91000");// 发送机构编号
		enter_map.put("targetcomp_id", "91000");// 目标机构编号
		enter_map.put("exchange_type", "1"); // 交易类别：1-上海 2-深圳 9-特转A A-特转BD-沪B
												// H-深B
		enter_map.put("stock_code", "600570"); // 证券代码
		enter_map.put("entrust_amount", "200"); // 委托数量，单位：股
		enter_map.put("entrust_price", "63.17");// 委托价格
		enter_map.put("entrust_bs", "1"); // 买卖方向：1-买入，2-卖出
		enter_map.put("entrust_prop", "0"); // 委托属性：0-买卖 3-申购 (股转 b,c,d,e) (市价委托
											// 上海：R,U 深圳：Q,S,T,U,V)
		enter_map.put("stock_account", ""); // 证券账号
		HttpClientUtil.sendPost(HttpClientUtil.URL + "/secu/v1/entrust_enter", enter_map, HttpClientUtil.CHARSET,
				HttpClientUtil.CHARSET, null, HttpClientUtil.BEARER + private_accesstoken.getAccess_token(), "普通委托");

		Map<String, String> entrust_qry_map = new HashMap<String, String>();
		entrust_qry_map.put("sendercomp_id", "91000");// 发送机构编号
		entrust_qry_map.put("targetcomp_id", "91000");// 目标机构编号
		entrust_qry_map.put("action_in", "0"); // 操作控制值，0-查询全部委托；1-查询可撤委托；2-按批号查询（通过locate_entrust_no送入）
		entrust_qry_map.put("exchange_type", "1"); // 【限制字典子项：1-上海 2-深圳 9-特转AA-特转B D-沪B H-深B】 数据字典(1301)

		HttpClientUtil.sendPost(HttpClientUtil.URL + "/secu/v1/entrust_qry", entrust_qry_map, HttpClientUtil.CHARSET,
				HttpClientUtil.CHARSET, null, HttpClientUtil.BEARER + private_accesstoken.getAccess_token(), "证券委托查询");

		// 证券持仓查询
		Map<String, String> stockposition_qry_map = new HashMap<String, String>();
		stockposition_qry_map.put("targetcomp_id", "91000");// 发送机构编号
		stockposition_qry_map.put("sendercomp_id", "91000");// 目标机构编号
		HttpClientUtil.sendPost(HttpClientUtil.URL + "/secu/v1/stockposition_qry", stockposition_qry_map,
				HttpClientUtil.CHARSET, HttpClientUtil.CHARSET, null,
				HttpClientUtil.BEARER + private_accesstoken.getAccess_token(), "持仓查询");
	}

}
