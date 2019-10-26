package uftdemo.com.controller;

import com.hundsun.t2sdk.impl.client.T2Services;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import uftdemo.com.service.UftInvestor;
import uftdemo.com.service.UftTradeCallBack;
import uftdemo.com.util.IniUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class EntrustByTimeController {
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
            String account = iniUtil.readByPathName("config\\config.ini","account-normal","fundaccount");
            String password = iniUtil.readByPathName("config\\config.ini","account-normal","password");
            String stock_code = iniUtil.readByPathName("config\\config.ini","SH","stkcode");
            String entrust_amount = iniUtil.readByPathName("config\\config.ini","SH","amount");
            String entrust_price = iniUtil.readByPathName("config\\config.ini","SH","price");
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
                String exchange_type = "2";
                if(stock_code.substring(0,1).equalsIgnoreCase("6")){
                    exchange_type = "1";
                }

                ScheduledExecutorService exe = Executors.newSingleThreadScheduledExecutor();
                LocalDateTime date = LocalDateTime.now();
                LocalDateTime localDateTime = LocalDateTime.of(date.getYear(),date.getMonth(),date.getDayOfMonth(),9,24,59);

                String finalExchange_type = exchange_type;

                logger4j.info("--埋单时间："+localDateTime);
                logger4j.info("--当前时间："+LocalDateTime.now());
                exe.schedule(()-> {
                            try {
                                logger4j.info("---委托开始执行！");
                                investor.ReqFunction333002(global_user_token, finalExchange_type, entrustWay,
                                        account, password, "2", stock_code, entrust_amount,
                                        entrust_price, entrust_bs, entrust_prop,registe_sure_flag,opStation,op_branch_no);
                                logger4j.info("---委托成功！");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        },
                        LocalDateTime.now().until(localDateTime, ChronoUnit.SECONDS),TimeUnit.SECONDS);
                a = tCBack.getReturnNumber();
//                Thread.sleep(Long.parseLong(entrusTime));
            }while(a.contains("重新登录"));
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
