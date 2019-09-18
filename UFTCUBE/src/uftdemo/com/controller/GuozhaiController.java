package uftdemo.com.controller;

import com.hundsun.t2sdk.impl.client.T2Services;
import uftdemo.com.service.UftInvestor;
import uftdemo.com.service.UftTradeCallBack;
import uftdemo.com.util.IniUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GuozhaiController {
    private T2Services server = T2Services.getInstance();//T2实例化
    private static String global_user_token = null;
    private static String global_branch_no = null;
    private static String global_client_id = null;
    private static String global_sysnode_id = null;

    static IniUtil iniUtil = new IniUtil();

    public static void main(String[] args) throws Exception {
        UftInvestor investor = new UftInvestor(); //投资者创建实例
        investor.InitT2();
        investor.Init();

        UftTradeCallBack tCBack = new UftTradeCallBack(); //普通业务消息回调创建实例

        String account = iniUtil.readByPathName("config\\config.ini","account-normal","fundaccount");
        String password = iniUtil.readByPathName("config\\config.ini","account-normal","password");
        String opStation = iniUtil.readByPathName("config\\config.ini","account-normal","op_station");
        String entrustWay = iniUtil.readByPathName("config\\config.ini","account-normal","op_entrust_way");
        String op_branch_no = iniUtil.readByPathName("config\\config.ini","account-normal","op_branch_no");
        String money = iniUtil.readByPathName("config\\config.ini","account-normal","money");

        //间隔时间
        String loginTime = iniUtil.readByPathName("config\\config.ini","config","loginTime");
        String entrusTime = iniUtil.readByPathName("config\\config.ini","config","entrusTime");

        investor.ReqFunction331100(account, password,opStation,entrustWay,op_branch_no); //登录
        Thread.sleep(Long.parseLong(loginTime));//多线程任务处理过快，为获取登录后出参必须有等待时间
        global_user_token = tCBack.user_token();//获取登录后返回的user_token
        global_branch_no = tCBack.branch_no();//获取登录后返回的branch_no
        global_client_id = tCBack.client_id();//获取登录后返回的client_id
        global_sysnode_id = tCBack.sysnode_id(); //获取登录后返回的client_id


        String exchange_type = "2";
        String stock_code = "131810";

        String entrust_bs = "2";
        String entrust_prop = "0";
        String registe_sure_flag="1";
//        investor.ReqFunction400(exchange_type,stock_code,global_sysnode_id);
//        Thread.sleep(1);
//        String entrust_price = tCBack.getReturnBuyPrice3();
        String entrust_price = "1";
        Double a  = Double.parseDouble(money);
//        Double b = Double.parseDouble(1);
        Double c = a/100;
        BigDecimal bigDecimal = BigDecimal.valueOf(c);
        Double entrust_amount = bigDecimal.setScale(-1,BigDecimal.ROUND_DOWN).doubleValue();

        investor.ReqFunction333002(global_user_token, "1", entrustWay,
                account, password, "2", "131810", entrust_amount+"",
                entrust_price, entrust_bs, entrust_prop,registe_sure_flag,opStation,op_branch_no);

        System.out.println(LocalDateTime.now()+"--账号："+global_client_id+"：131810下单成功！");
        Thread.sleep(5000);
    }
}
