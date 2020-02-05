package uftdemo.com.controller;

import com.hundsun.t2sdk.impl.client.T2Services;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import uftdemo.com.service.UftInvestor;
import uftdemo.com.service.UftTradeCallBack;
import uftdemo.com.util.IniUtil;

public class MonitorController {
    public static Logger logger4j = LogManager.getLogger(MonitorController.class);
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
            String account = iniUtil.readByPathName("config\\config.ini","account-normal","fundaccount");
            String password = iniUtil.readByPathName("config\\config.ini","account-normal","password");
            String stock_code_SH = iniUtil.readByPathName("config\\config.ini","SH","stkcode");
            String entrust_amount_SH = iniUtil.readByPathName("config\\config.ini","SH","amount");
            String entrust_price_SH = iniUtil.readByPathName("config\\config.ini","SH","price");

            String stock_code_SZ = iniUtil.readByPathName("config\\config.ini","SZ","stkcode");
            String entrust_amount_SZ = iniUtil.readByPathName("config\\config.ini","SZ","amount");
            String entrust_price_SZ = iniUtil.readByPathName("config\\config.ini","SZ","price");

            String entrust_bs = "1";
            String entrust_prop = "0";
            String registe_sure_flag="1";
            String opStation = iniUtil.readByPathName("config\\config.ini","account-normal","op_station");
            String entrustWay = iniUtil.readByPathName("config\\config.ini","account-normal","op_entrust_way");
            String op_branch_no = iniUtil.readByPathName("config\\config.ini","account-normal","op_branch_no");

            //间隔时间
            String loginTime = iniUtil.readByPathName("config\\config.ini","config","loginTime");
            String entrusTime = iniUtil.readByPathName("config\\config.ini","config","entrusTime");

            String a  = "";
            String b  = "";
            do {
            investor.ReqFunction331100(account, password,opStation,entrustWay,op_branch_no); //登录
            Thread.sleep(Long.parseLong(loginTime));//多线程任务处理过快，为获取登录后出参必须有等待时间
            global_user_token = tCBack.user_token();//获取登录后返回的user_token
            global_branch_no = tCBack.branch_no();//获取登录后返回的branch_no
            global_client_id = tCBack.client_id();//获取登录后返回的client_id
            global_sysnode_id = tCBack.sysnode_id(); //获取登录后返回的client_id

//            String a[] = {global_user_token, "1", "7",
//                    account, password, "2", stock_code, entrust_amount,
//                    entrust_price, entrust_bs, entrust_prop};
            //tCBack.a = a;

                String stock_code = "";
                String entrust_amount = "";
                String entrust_price = "";
                if(Integer.parseInt(entrust_amount_SH)==0){
                    stock_code = stock_code_SZ;
                    entrust_amount = entrust_amount_SZ;
                    entrust_price = entrust_price_SZ;
                }else {
                    stock_code = stock_code_SH;
                    entrust_amount = entrust_amount_SH;
                    entrust_price = entrust_price_SH;
                }

            String exchange_type = "2";
            if(stock_code.substring(0,1).equalsIgnoreCase("6")){
                exchange_type = "1";
            }

                do{
                    logger4j.info("********"+"委托开始");
                    investor.ReqFunction333002(global_user_token, exchange_type, entrustWay,
                            account, password, "2", stock_code, entrust_amount,
                            entrust_price, entrust_bs, entrust_prop,registe_sure_flag,opStation,op_branch_no);
                    Thread.sleep(1000);
                    a = tCBack.getReturnNumber();
                    investor.ReqFunction333101(global_client_id, global_branch_no,global_user_token,account,password,global_sysnode_id,
                            op_branch_no,entrustWay,opStation);
                    Thread.sleep(1000);
                    b = tCBack.getReturnEntrustPrice();
                    if(b.isEmpty()){b="0";}
                    logger4j.info("********"+"委托代码及价格"+stock_code+","+entrust_price);
                    logger4j.info("********"+"查询出的委托价格"+b);
                    if(Double.parseDouble(b)==Double.parseDouble(entrust_price)){

                        logger4j.info("********"+"委托结束");
                        logger4j.info("********"+"系统将在10秒后退出");
                        for (int i=10;i>0;i--) {
                            Thread.sleep(1000);
                            logger4j.info("********剩余时间："+i);
                        }
                        System.exit(0);
                    }
                    Thread.sleep(Long.parseLong(entrusTime));
                }while(a.contains("当前时间不允许委托")||a.contains("证券交易未初始化")||a.contains("可用资金不足"));

            }while(a.contains("重新登录"));
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
