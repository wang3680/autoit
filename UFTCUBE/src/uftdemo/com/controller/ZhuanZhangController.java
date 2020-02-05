package uftdemo.com.controller;

import com.hundsun.t2sdk.impl.client.T2Services;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import uftdemo.com.service.UftInvestor;
import uftdemo.com.service.UftTradeCallBack;
import uftdemo.com.util.IniUtil;

public class ZhuanZhangController {
    public static Logger logger4j = LogManager.getLogger(EntrustByTimeController.class);
    private T2Services server = T2Services.getInstance();//T2实例化
    private static String global_user_token = null;
    private static String global_branch_no = null;
    private static String global_client_id = null;
    private static String global_sysnode_id = null;

    static IniUtil iniUtil = new IniUtil();

    public static void main(String[] args) {
        try {
//            UftController app = new UftController();
            UftInvestor investor = new UftInvestor(); //投资者创建实例
            investor.InitT2();
            investor.Init();

            UftTradeCallBack tCBack = new UftTradeCallBack(); //普通业务消息回调创建实例
            String account = iniUtil.readByPathName("config\\config.ini", "account-normal", "fundaccount");
            String password = iniUtil.readByPathName("config\\config.ini", "account-normal", "password");
            String stock_code_SH = iniUtil.readByPathName("config\\config.ini", "SH", "stkcode");
            String entrust_amount_SH = iniUtil.readByPathName("config\\config.ini", "SH", "amount");
            String entrust_price_SH = iniUtil.readByPathName("config\\config.ini", "SH", "price");

            String stock_code_SZ = iniUtil.readByPathName("config\\config.ini", "SZ", "stkcode");
            String entrust_amount_SZ = iniUtil.readByPathName("config\\config.ini", "SZ", "amount");
            String entrust_price_SZ = iniUtil.readByPathName("config\\config.ini", "SZ", "price");

            String entrust_bs = "1";
            String entrust_prop = "0";
            String registe_sure_flag = "1";
            String opStation = iniUtil.readByPathName("config\\config.ini", "account-normal", "op_station");
            String entrustWay = iniUtil.readByPathName("config\\config.ini", "account-normal", "op_entrust_way");
            String op_branch_no = iniUtil.readByPathName("config\\config.ini", "account-normal", "op_branch_no");

            //间隔时间
            String loginTime = iniUtil.readByPathName("config\\config.ini", "config", "loginTime");
            String entrusTime = iniUtil.readByPathName("config\\config.ini", "config", "entrusTime");

            //银行转账
            String zhuanzhang = iniUtil.readByPathName("config\\config.ini", "account-normal", "zhuanzhang");
            String money = iniUtil.readByPathName("config\\config.ini", "account-normal", "money");
            String bank_password = iniUtil.readByPathName("config\\config.ini", "account-normal", "bank_password");

            String bank_no = iniUtil.readByPathName("config\\config.ini", "account-normal", "bank_no");

            String a = "";
            String b = "";
            do {
                investor.ReqFunction331100(account, password, opStation, entrustWay, op_branch_no); //登录
                Thread.sleep(Long.parseLong(loginTime));//多线程任务处理过快，为获取登录后出参必须有等待时间
                global_user_token = tCBack.user_token();//获取登录后返回的user_token
                global_branch_no = tCBack.branch_no();//获取登录后返回的branch_no
                global_client_id = tCBack.client_id();//获取登录后返回的client_id
                global_sysnode_id = tCBack.sysnode_id(); //获取登录后返回的client_id


                logger4j.info("********"+"###转账开始");
                //转账，1–银行转证券 2- 证券转银行

                investor.ReqFunction332200(global_client_id, global_branch_no, global_user_token, account, password, global_sysnode_id
                        , op_branch_no, entrustWay, opStation, zhuanzhang, money,bank_password,bank_no);

                Thread.sleep(2000);
                logger4j.info("********"+"###转账完成");

            } while (a.contains("重新登录"));
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
