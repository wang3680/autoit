package uftdemo.com.controller;

import com.hundsun.t2sdk.impl.client.T2Services;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import uftdemo.com.service.UftInvestor;
import uftdemo.com.service.UftTradeCallBack;
import uftdemo.com.util.IniUtil;

import java.time.LocalDateTime;

public class ChedanController {
    public static Logger logger4j = LogManager.getLogger(ChedanController.class);
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

        //间隔时间
        String loginTime = iniUtil.readByPathName("config\\config.ini","config","loginTime");
        String entrusTime = iniUtil.readByPathName("config\\config.ini","config","entrusTime");

        investor.ReqFunction331100(account, password,opStation,entrustWay,op_branch_no); //登录
        Thread.sleep(Long.parseLong(loginTime));//多线程任务处理过快，为获取登录后出参必须有等待时间
        global_user_token = tCBack.user_token();//获取登录后返回的user_token
        global_branch_no = tCBack.branch_no();//获取登录后返回的branch_no
        global_client_id = tCBack.client_id();//获取登录后返回的client_id
        global_sysnode_id = tCBack.sysnode_id(); //获取登录后返回的client_id

        investor.ReqFunction333101(global_client_id, global_branch_no,global_user_token,account,password,global_sysnode_id,
                op_branch_no,entrustWay,opStation);
        Thread.sleep(1000);
        String returnEntrustNo = tCBack.getReturnEntrustNo();//获取第一笔的委托编号
        logger4j.info("--委托编号："+returnEntrustNo);
//        Thread.sleep(1000);
        investor.ReqFunction333017(global_user_token,global_branch_no,global_client_id,account,password,returnEntrustNo,
                op_branch_no,entrustWay,opStation);
        logger4j.info(LocalDateTime.now()+"--账号："+global_client_id+"：撤单成功！");
        Thread.sleep(5000);
        System.exit(0);
    }
}
