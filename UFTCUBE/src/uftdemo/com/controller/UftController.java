package uftdemo.com.controller;

import com.hundsun.mcapi.MCServers;
import com.hundsun.t2sdk.impl.client.T2Services;
import uftdemo.com.service.UftInvestor;
import uftdemo.com.service.UftTradeCallBack;

import java.util.Scanner;

public class UftController {

	private T2Services server = T2Services.getInstance();//T2实例化
    private static String global_user_token = null;
    private static String global_branch_no = null;
    private static String global_client_id = null;
    private static String global_sysnode_id = null;
    static UftInvestor investor;
    public static void main(String[] args) {
        try {
        	UftController app = new UftController();
            investor = new UftInvestor(); //投资者创建实例
            investor.InitT2();
            investor.Init();
//            menu(app, investor);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //获取T2SDK配置文件中配置的连接
    //启动T2SDK，只需要在服务器启动时调用一次就可以了
    public void InitT2() throws Exception {
        server.setT2sdkConfigString("config/t2sdk_hundsun-config.xml");// 配置文件路径
        server.init();
        server.start();
        MCServers.MCInit();
    }

//主菜单
//    public static void menu(UftController app, UftInvestor investor) {
//        try {
//            System.out.println("Warnning !!! This demo is only for testing,not recommand to use for real market!!!");
//            System.out.println("------------------------------------------------");
//            System.out.println(" [1]  331100 请求登录			    ");
//            System.out.println(" [0]  退出系统			    ");
//            System.out.println("请输入数字然后按回车键进行操作选择：");
//            Thread.sleep(1000);
//            int j = 0;
//            String i, n, account, password;
//            Scanner in = new Scanner(System.in);
//            i = in.next();
//            switch (i) {
//                case "1":
//                    UftTradeCallBack tCBack = new UftTradeCallBack(); //普通业务消息回调创建实例
//                    System.out.println("请输入账号");
//                    account = in.next();
//                    System.out.println("请输入密码");
//                    password = in.next();
//                    investor.ReqFunction331100(account, password,"1"); //登录
//                    Thread.sleep(1000);//多线程任务处理过快，为获取登录后出参必须有等待时间
//                    global_user_token = tCBack.user_token();//获取登录后返回的user_token
//                    global_branch_no = tCBack.branch_no();//获取登录后返回的branch_no
//                    global_client_id = tCBack.client_id();//获取登录后返回的client_id
//                    global_sysnode_id = tCBack.sysnode_id(); //获取登录后返回的client_id
//                    menuIT(app, investor, account, global_client_id, global_branch_no, global_user_token, password, global_sysnode_id); //独立交易员界面
//
//                default:
//                    System.out.println("No such choice, back to the main menu");
//                    menu(app, investor);
//                    break;
//            }
//            while (true) {
//                Thread.sleep(1000);
//            }
//
//        } catch (Exception e) {
//            System.out.println("1.返回主目录");
//            System.out.println("0. 退出程序");
//            System.out.println("请输入数字然后按回车键进行操作选择：");
//            Scanner in = new Scanner(System.in);
//            String n = in.next();
//            switch (n) {
//                case "1":
//                    menu(app, investor);//back to main menu
//                    break;
//                case "0":
//                    System.exit(0);//end app
//                    break;
//                default:
//                    System.out.println("No such choice, back to the main menu");
//                    menu(app, investor);
//                    break;
//            }
//            e.printStackTrace();
//        }
//    }

    //独立投资者
    public static void menuIT(UftController app, UftInvestor investor, String account, String client_id, String branch_no, String user_token, String password, String sysnode_id) {
        try {
            System.out.println("Warnning !!! This demo is only for testing,not recommand to use this for real market!!!");
            System.out.println("------------------------------------------------");
            System.out.println(" [1]  333002 普通委托");
            System.out.println(" [2]  333017 委托撤单");
            System.out.println(" [3]  333101 证券委托查询");
            System.out.println(" [4]  333102 成交查询");
            System.out.println(" [5]  333104 持仓查询");
            System.out.println(" [6]  332255 资金快速查询");
            System.out.println(" [7]  620001-23 证券委托回报订阅");
            System.out.println(" [8]  620001-12 证券成交回报订阅");
            System.out.println(" [9]  400 行情查询");
            System.out.println("请输入数字然后按回车键进行操作选择：");
            String n, i;
            String trade_unit;
            String exchange_type;
            String stock_code;
            String entrust_amount;
            String entrust_price;
            String entrust_bs;
            String entrust_no;
            String entrust_prop;
            Scanner in = new Scanner(System.in);
            i = in.next();
            switch (i) {
                case "1"://普通委托
                    System.out.println("请输入证券代码，并按回车键确认");
                    stock_code = in.next();//证券代码输入
                    System.out.println("请输入数字选择交易类型：（0）资金 （1）上海 （2）深圳，并按回车键确认");
                    exchange_type = in.next();//交易所输入
                    System.out.println("请输入委托数量，并按回车键确认");
                    entrust_amount = in.next();//交易数量输入
                    System.out.println("请输入委托价格，并按回车键确认");
                    entrust_price = in.next();//交易价格输入
                    System.out.println("请输入数字选择买卖方向：（1）买入 （2）卖出，并按回车键确认");
                    entrust_bs = in.next();//买卖方向输入
                    System.out.println("请输入数字选择委托属性：（0）买卖 （1）配股 ...，并按回车键确认.");
                    entrust_prop = in.next();//委托属性输入
                    investor.ReqFunction333002(user_token, exchange_type, "Z", account, password, "2", stock_code, entrust_amount, entrust_price, entrust_bs, entrust_prop,"1","1","0");
                    Thread.sleep(1000);
                    System.out.println("1.返回主目录");
                    System.out.println("0. 退出程序");
                    System.out.println("请输入数字然后按回车键进行操作选择：");
                    n = in.next();
                    switch (n) {
                        case "1":
                            menuIT(app, investor, account, client_id, branch_no, user_token, password, sysnode_id);//back to menu
                            break;
                        case "0":
                            System.exit(0);//end app
                            break;
                        default:
                            Thread.sleep(1000);
                            System.out.println("No such choice, back to the main menu");
                            menuIT(app, investor, account, client_id, branch_no, user_token, password, sysnode_id);
                            break;
                    }
                    break;
                case "2"://委托撤单
                    System.out.println("请输入委托编号，并按回车键确认");
                    entrust_no = in.next();//需撤单委托编号输入
                    investor.ReqFunction333017(user_token, branch_no, client_id, account, password, entrust_no,"","","");
                    Thread.sleep(1000);
                    System.out.println("1.返回主目录");
                    System.out.println("0. 退出程序");
                    System.out.println("请输入数字然后按回车键进行操作选择：");
                    n = in.next();
                    switch (n) {
                        case "1":
                            menuIT(app, investor, account, client_id, branch_no, user_token, password, sysnode_id);//back to menu
                            break;
                        case "0":
                            System.exit(0);//end app
                            break;
                        default:
                            Thread.sleep(1000);
                            System.out.println("No such choice, back to the main menu");
                            menuIT(app, investor, account, client_id, branch_no, user_token, password, sysnode_id);
                            break;
                    }
                    break;
                case "3"://证券委托查询
                    investor.ReqFunction333101(client_id, branch_no, user_token, account, password, sysnode_id,"","","");
                    Thread.sleep(1000);
                    System.out.println("1.返回主目录");
                    System.out.println("0. 退出程序");
                    System.out.println("请输入数字然后按回车键进行操作选择：");
                    n = in.next();
                    switch (n) {
                        case "1":
                            menuIT(app, investor, account, client_id, branch_no, user_token, password, sysnode_id);//back to menu
                            break;
                        case "0":
                            System.exit(0);//end app
                            break;
                        default:
                            Thread.sleep(1000);
                            System.out.println("No such choice, back to the main menu");
                            menuIT(app, investor, account, client_id, branch_no, user_token, password, sysnode_id);
                            break;
                    }
                case "4"://成交查询
                    investor.ReqFunction333102(user_token, branch_no, client_id, account, password, sysnode_id);
                    Thread.sleep(1000);
                    System.out.println("1.返回主目录");
                    System.out.println("0. 退出程序");
                    System.out.println("请输入数字然后按回车键进行操作选择：");
                    n = in.next();
                    switch (n) {
                        case "1":
                            menuIT(app, investor, account, client_id, branch_no, user_token, password, sysnode_id);//back to menu
                            break;
                        case "0":
                            System.exit(0);//end app
                            break;
                        default:
                            Thread.sleep(1000);
                            System.out.println("No such choice, back to the main menu");
                            menuIT(app, investor, account, client_id, branch_no, user_token, password, sysnode_id);
                            break;
                    }
                case "5"://持仓查询
                    investor.ReqFunction333104(branch_no, client_id, user_token, account, password, sysnode_id);
                    Thread.sleep(1000);
                    System.out.println("1.返回主目录");
                    System.out.println("0. 退出程序");
                    System.out.println("请输入数字然后按回车键进行操作选择：");
                    n = in.next();
                    switch (n) {
                        case "1":
                            menuIT(app, investor, account, client_id, branch_no, user_token, password, sysnode_id);//back to menu
                            break;
                        case "0":
                            System.exit(0);//end app
                            break;
                        default:
                            Thread.sleep(1000);
                            System.out.println("No such choice, back to the main menu");
                            menuIT(app, investor, account, client_id, branch_no, user_token, password, sysnode_id);
                            break;
                    }
                case "6"://客户资金快速查询
                    investor.ReqFunction332255(branch_no, client_id, user_token, account, password, sysnode_id);
                    Thread.sleep(1000);
                    System.out.println("1.返回主目录");
                    System.out.println("0. 退出程序");
                    System.out.println("请输入数字然后按回车键进行操作选择：");
                    n = in.next();
                    switch (n) {
                        case "1":
                            menuIT(app, investor, account, client_id, branch_no, user_token, password, sysnode_id);//back to menu
                            break;
                        case "0":
                            System.exit(0);//end app
                            break;
                        default:
                            Thread.sleep(1000);
                            System.out.println("No such choice, back to the main menu");
                            menuIT(app, investor, account, client_id, branch_no, user_token, password, sysnode_id);
                            break;
                    }
                case "7"://订阅-证券委托回报
                    investor.ReqFunction620001_23(account, branch_no);
                    Thread.sleep(1000);
                    System.out.println("1.返回主目录");
                    System.out.println("0. 退出程序");
                    System.out.println("请输入数字然后按回车键进行操作选择：");
                    n = in.next();
                    switch (n) {
                        case "1":
                            menuIT(app, investor, account, client_id, branch_no, user_token, password, sysnode_id);//back to menu
                            break;
                        case "0":
                            System.exit(0);//end app
                            break;
                        default:
                            Thread.sleep(1000);
                            System.out.println("No such choice, back to the main menu");
                            menuIT(app, investor, account, client_id, branch_no, user_token, password, sysnode_id);
                            break;
                    }
                case "8"://订阅-证券成交回报
                    investor.ReqFunction620001_12(account, branch_no);
                    Thread.sleep(1000);
                    System.out.println("1.返回主目录");
                    System.out.println("0. 退出程序");
                    System.out.println("请输入数字然后按回车键进行操作选择：");
                    n = in.next();
                    switch (n) {
                        case "1":
                            menuIT(app, investor, account, client_id, branch_no, user_token, password, sysnode_id);//back to menu
                            break;
                        case "0":
                            System.exit(0);//end app
                            break;
                        default:
                            Thread.sleep(1000);
                            System.out.println("No such choice, back to the main menu");
                            menuIT(app, investor, account, client_id, branch_no, user_token, password, sysnode_id);
                            break;
                    }
                case "9": //行情查询
                    System.out.println("请输入数字选择交易类型：（0）资金 （1）上海 （2）深圳，并按回车键确认");
                    exchange_type = in.next();//交易所输入
                    System.out.println("请输入证券代码，并按回车键确认");
                    stock_code = in.next();//证券代码输入
                    investor.ReqFunction400(exchange_type, stock_code,sysnode_id);
                    Thread.sleep(1000);
                    System.out.println("1.返回主目录");
                    System.out.println("0. 退出程序");
                    System.out.println("请输入数字然后按回车键进行操作选择：");
                    n = in.next();
                    switch (n) {
                        case "1":
                            menuIT(app, investor, account, client_id, branch_no, user_token, password, sysnode_id);//back to menu
                            break;
                        case "0":
                            System.exit(0);//end app
                            break;
                        default:
                            Thread.sleep(1000);
                            System.out.println("No such choice, back to the main menu");
                            menuIT(app, investor, account, client_id, branch_no, user_token, password, sysnode_id);
                            break;
                    }
            }

            while (true) {
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
