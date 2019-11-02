package uftdemo.com.controller;

import com.hundsun.t2sdk.impl.client.T2Services;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import uftdemo.com.service.UftInvestor;
import uftdemo.com.service.UftTradeCallBack;
import uftdemo.com.util.IniUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
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
            String account = iniUtil.readByPathName("config\\config.ini", "account-normal", "fundaccount");
            String password = iniUtil.readByPathName("config\\config.ini", "account-normal", "password");
            String stock_code = iniUtil.readByPathName("config\\config.ini", "SH", "stkcode");
            String entrust_amount = iniUtil.readByPathName("config\\config.ini", "SH", "amount");
            String entrust_price = iniUtil.readByPathName("config\\config.ini", "SH", "price");
            String entrust_bs = "1";
            String entrust_prop = "0";
            String registe_sure_flag = "1";
            String opStation = iniUtil.readByPathName("config\\config.ini", "account-normal", "op_station");
            String entrustWay = iniUtil.readByPathName("config\\config.ini", "account-normal", "op_entrust_way");
            String op_branch_no = iniUtil.readByPathName("config\\config.ini", "account-normal", "op_branch_no");

            //间隔时间
            String loginTime = iniUtil.readByPathName("config\\config.ini", "config", "loginTime");
            String entrusTime = iniUtil.readByPathName("config\\config.ini", "config", "entrusTime");
            //埋单时间
            String maidanTime = iniUtil.readByPathName("config\\config.ini", "config", "maidanTime");

            String a = "";
            do {
                investor.ReqFunction331100(account, password, opStation, entrustWay, op_branch_no); //登录
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
                if (stock_code.substring(0, 1).equalsIgnoreCase("6")) {
                    exchange_type = "1";
                }

                ScheduledExecutorService exe = Executors.newSingleThreadScheduledExecutor();
                LocalDateTime date = LocalDateTime.now();

                String time[] = maidanTime.split(":");
                int hour = Integer.parseInt(time[0]);
                int minute = Integer.parseInt(time[1]);
                int second = Integer.parseInt(time[2]);
                int nanOfSecond = Integer.parseInt(time[3]);
                LocalDateTime maidanTimeLocal = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                        hour, minute, second, nanOfSecond);

                String finalExchange_type = exchange_type;

                logger4j.info("********埋单时间：" + maidanTimeLocal);
                logger4j.info("********当前时间：" + LocalDateTime.now());
                if (maidanTimeLocal.isAfter(LocalDateTime.now())){//埋单时间大于当前时间，开始执行任务，否则不执行
                    logger4j.info("********"+"埋单时间大于当前时间！");
                    exe.schedule(() -> {
                                try {
                                    logger4j.info("********"+LocalDateTime.now() + "********委托开始执行！");
                                investor.ReqFunction333002(global_user_token, finalExchange_type, entrustWay,
                                        account, password, "2", stock_code, entrust_amount,
                                        entrust_price, entrust_bs, entrust_prop,registe_sure_flag,opStation,op_branch_no);
                                    logger4j.info("********委托成功！");
                                    logger4j.info("********"+"委托信息："+stock_code+","+entrust_price+","+entrust_amount);
                                    exe.shutdown();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            },
                            LocalDateTime.now().until(maidanTimeLocal, ChronoUnit.NANOS), TimeUnit.NANOSECONDS);
                    a = tCBack.getReturnNumber();
                }else {
                    logger4j.info("********"+"埋单时间小于当前时间！系统将在10秒后退出");
                    for (int i=10;i>0;i--) {
                        Thread.sleep(1000);
                        logger4j.info("********剩余时间："+i);
                    }
                    System.exit(0);
                }
//                Thread.sleep(Long.parseLong(entrusTime));
            } while (a.contains("重新登录"));

//            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
