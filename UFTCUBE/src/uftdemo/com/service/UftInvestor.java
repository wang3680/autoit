/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *362000,362002,362200，362405,362408,6111501,6111502,6111503,620001_40005,620001_40006
 */
package uftdemo.com.service;

import com.hundsun.mcapi.MCServers;
import com.hundsun.mcapi.interfaces.ISubscriber;
import com.hundsun.mcapi.subscribe.MCSubscribeParameter;
import com.hundsun.t2sdk.common.core.context.ContextUtil;
import com.hundsun.t2sdk.common.share.dataset.DatasetService;
import com.hundsun.t2sdk.impl.client.T2Services;
import com.hundsun.t2sdk.interfaces.IClient;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;
import com.hundsun.t2sdk.interfaces.share.event.EventTagdef;
import com.hundsun.t2sdk.interfaces.share.event.EventType;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;
import org.apache.log4j.LogManager;

import java.util.logging.Logger;

/**
 *
 * @author zhankai16396
 */
public class UftInvestor {

    public UftInvestor() {
    }
    public IClient client = null;
    public IClient client1 = null;
    public T2Services server = T2Services.getInstance();// 获取T2实例
    public Logger logger = Logger.getLogger("ufxTrade");
    UftMdCallBack mdCallBack = new UftMdCallBack();
    org.apache.log4j.Logger logger4j = LogManager.getLogger(UftTradeCallBack.class);
    //设置节点
    public void Init() throws Exception {
        logger.info("ar_tree");
        //ar_tree是配置在配置文件中的服务
        client = server.getClient("ar_tree");//trade走节点ar_tree
        client1 = server.getClient("ar_tree_msg");//message center走节点ar_tree_msg
        mdCallBack.setClient(client1);//设置message center节点
    }

//异步登录打包
    public void ReqFunction331100(String account, String password,String opStation,String op_entrust_way,String op_branch_no) throws Exception {
        // 获取event
        IEvent event = ContextUtil.getServiceContext().getEventFactory()
                .getEventByAlias("331100", EventType.ET_REQUEST);
        // 往event中添加dataset
        IDataset dataset = DatasetService.getDefaultInstance().getDataset();
        dataset.addColumn("op_branch_no");//操作分支机构
        dataset.addColumn("op_entrust_way");//委托方式
        dataset.addColumn("op_station");//站点地址
        dataset.addColumn("password");//密码
        dataset.addColumn("password_type");//密码类别
        dataset.addColumn("input_content");//客户标志类别
        dataset.addColumn("account_content");//输入内容（账号）
        dataset.appendRow();
        dataset.updateString("op_branch_no", op_branch_no);//操作分支机构打入值1
        dataset.updateString("op_entrust_way", op_entrust_way);
        dataset.updateString("op_station", opStation);
        dataset.updateString("password", password);
        dataset.updateString("password_type", "2");
        dataset.updateString("input_content", "1");
        dataset.updateString("account_content", account);
        event.setServiceId("331100"); //打入功能号
        event.putEventData(dataset); //入参打包
        event.setIntegerAttributeValue(EventTagdef.TAG_SENDERID, 5); //分配节点为5
        unPack(event, event.getServiceId()); //入参解包，同时打印发送包参数信息
        client.send(event); //发送包
    }

    //行情查询
    public void ReqFunction400(String exchange_type, String stock_code, String sysnode_id) throws Exception {
        // 获取event
        IEvent event = ContextUtil.getServiceContext().getEventFactory()
                .getEventByAlias("400", EventType.ET_REQUEST);
        // 往event中添加dataset
        IDataset dataset = DatasetService.getDefaultInstance().getDataset();
        dataset.addColumn("exchange_type");//交易类别
        dataset.addColumn("stock_code");//证券代码
        dataset.appendRow();
        dataset.updateString("exchange_type", exchange_type);//操作分支机构打入值1
        dataset.updateString("stock_code", stock_code);
        event.setServiceId("400"); //打入功能号
        event.setStringAttributeValue("46", sysnode_id);//打入system node is 到46号域
        event.putEventData(dataset); //入参打包
        event.setIntegerAttributeValue(EventTagdef.TAG_SENDERID, 5); //分配节点为5
        unPack(event, event.getServiceId()); //入参解包，同时打印发送包参数信息
        client.send(event); //发送包
    }

    //普通委托
    public void ReqFunction333002(String user_token, String exchange_type, String op_entrust_way, String account, String password, String password_type, String stock_code,
            String entrust_amount, String entrust_price, String entrust_bs, String entrust_prop,String registe_sure_flag,String opStation,String opBranchNo) throws Exception {

        IEvent event = ContextUtil.getServiceContext().getEventFactory()
                .getEventByAlias("333002", EventType.ET_REQUEST);
        IDataset dataset = DatasetService.getDefaultInstance().getDataset();
        dataset.addColumn("user_token");
        dataset.addColumn("exchange_type");
        dataset.addColumn("stock_account");
        dataset.addColumn("op_entrust_way");
        dataset.addColumn("fund_account");
        dataset.addColumn("password");
        dataset.addColumn("password_type");
        dataset.addColumn("stock_code");
        dataset.addColumn("entrust_amount");
        dataset.addColumn("entrust_price");
        dataset.addColumn("entrust_bs");
        dataset.addColumn("entrust_prop");
        dataset.addColumn("registe_sure_flag");
        dataset.addColumn("op_station");//站点地址
        dataset.addColumn("op_branch_no");
        dataset.appendRow();
        dataset.updateString("user_token", user_token);
        dataset.updateString("exchange_type", exchange_type);
        dataset.updateString("stock_account", "");
        dataset.updateString("op_entrust_way", op_entrust_way);
        dataset.updateString("fund_account", account);
        dataset.updateString("password", password);
        dataset.updateString("password_type", password_type);
        dataset.updateString("stock_code", stock_code);
        dataset.updateString("entrust_amount", entrust_amount);
        dataset.updateString("entrust_price", entrust_price);
        dataset.updateString("entrust_bs", entrust_bs);
        dataset.updateString("entrust_prop", entrust_prop);
        dataset.updateString("registe_sure_flag", registe_sure_flag);
        dataset.updateString("op_station", opStation);
        dataset.updateString("op_branch_no",opBranchNo);
        event.setServiceId("333002");
        event.setStringAttributeValue("46", "2");
        event.putEventData(dataset);
        event.setIntegerAttributeValue(EventTagdef.TAG_SENDERID, 5);
        unPack(event, event.getServiceId()); //入参解包，同时打印发送包参数信息
        client.send(event);
    }

    //委托撤单
    public void ReqFunction333017(String user_token, String branch_no, String client_id, String account, String password,
                                  String entrust_no,String op_branch_no,String op_entrust_way,String op_station) throws Exception {

        IEvent event = ContextUtil.getServiceContext().getEventFactory()
                .getEventByAlias("333017", EventType.ET_REQUEST);
        IDataset dataset = DatasetService.getDefaultInstance().getDataset();
        dataset.addColumn("op_branch_no");
        dataset.addColumn("user_token");
        dataset.addColumn("op_entrust_way");
        dataset.addColumn("op_station");
        dataset.addColumn("branch_no");
        dataset.addColumn("client_id");
        dataset.addColumn("fund_account");
        dataset.addColumn("password");
        dataset.addColumn("entrust_no");
        dataset.appendRow();
        dataset.updateString("op_branch_no", op_branch_no);
        dataset.updateString("user_token", user_token);
        dataset.updateString("op_entrust_way", op_entrust_way);
        dataset.updateString("op_station", op_station);
        dataset.updateString("branch_no", branch_no);
        dataset.updateString("client_id", client_id);
        dataset.updateString("fund_account", account);
        dataset.updateString("password", password);
        dataset.updateString("entrust_no", entrust_no);
        event.setServiceId("333017");
        event.setStringAttributeValue("46", "2");
        event.putEventData(dataset);
        event.setIntegerAttributeValue(EventTagdef.TAG_SENDERID, 5);

        unPack(event, event.getServiceId()); //入参解包，同时打印发送包参数信息
        client.send(event);
    }

    //证券委托查询
    public void ReqFunction333101(String client_id, String branch_no, String user_token, String account, String password,
                                  String sysnode_id,String op_branch_no,String op_entrust_way,String op_station) throws Exception {
        // 获取event
        IEvent event = ContextUtil.getServiceContext().getEventFactory()
                .getEventByAlias("333101", EventType.ET_REQUEST);
        // 往event中添加dataset
        IDataset dataset = DatasetService.getDefaultInstance().getDataset();
        dataset.addColumn("op_branch_no");//操作分支机构
        dataset.addColumn("op_entrust_way");//委托方式
        dataset.addColumn("op_station");//站点地址
        dataset.addColumn("branch_no");//分支机构
        dataset.addColumn("client_id");//客户编号
        dataset.addColumn("user_token");//用户口令
        dataset.addColumn("fund_account");//资产账户
        dataset.addColumn("password");//密码
        dataset.addColumn("request_num");//请求行数
        dataset.appendRow();
        dataset.updateString("op_branch_no", op_branch_no);
        dataset.updateString("op_entrust_way", op_entrust_way); //op_entrust_way 打入7 网上交易
        dataset.updateString("op_station", op_station);
        dataset.updateString("branch_no", branch_no);
        dataset.updateString("client_id", client_id);
        dataset.updateString("user_token", user_token);
        dataset.updateString("fund_account", account);
        dataset.updateString("password", password);
        dataset.updateString("request_num", "1000");
        event.setServiceId("333101");//打入功能号
        event.setStringAttributeValue("46", sysnode_id);//打入system node is 到46号域
        event.putEventData(dataset);//入参打包
        event.setIntegerAttributeValue(EventTagdef.TAG_SENDERID, 5); //分配节点为5
        unPack(event, event.getServiceId()); //入参解包，同时打印发送包参数信息
        client.send(event);//发送包
    }

    //证券成交查询
    public void ReqFunction333102(String user_token, String branch_no, String client_id, String account, String password, String sysnode_id) throws Exception {
        IEvent event = ContextUtil.getServiceContext().getEventFactory()
                .getEventByAlias("333102", EventType.ET_REQUEST);
        // 往event中添加dataset
        IDataset dataset = DatasetService.getDefaultInstance().getDataset();
        dataset.addColumn("user_token");//用户口令
        dataset.addColumn("op_branch_no");//操作分支机构
        dataset.addColumn("op_entrust_way");//委托方式
        dataset.addColumn("op_station");//站点地址
        dataset.addColumn("branch_no");//分支机构
        dataset.addColumn("client_id");//客户编号
        dataset.addColumn("fund_account");//资产账户
        dataset.addColumn("password");//密码
        dataset.addColumn("request_num");//请求行数
        dataset.appendRow();
        dataset.updateString("user_token", user_token);
        dataset.updateString("op_branch_no", "");
        dataset.updateString("op_entrust_way", "7");
        dataset.updateString("op_station", "1");
        dataset.updateString("branch_no", branch_no);
        dataset.updateString("client_id", client_id);
        dataset.updateString("fund_account", account);
        dataset.updateString("password", password);
        dataset.updateString("request_num", "1000");
        event.setServiceId("333102");//打入功能号
        event.setStringAttributeValue("46", sysnode_id);//打入system node is 到46号域
        event.putEventData(dataset);//入参打包
        event.setIntegerAttributeValue(EventTagdef.TAG_SENDERID, 5);//分配节点为5
        unPack(event, event.getServiceId());//入参解包，同时打印发送包参数信息
        client.send(event);//发送包
    }

    //证券持仓查询
    public void ReqFunction333104(String branch_no, String client_id, String user_token, String account, String password, String sysnode_id) throws Exception {
        IEvent event = ContextUtil.getServiceContext().getEventFactory()
                .getEventByAlias("333104", EventType.ET_REQUEST);
        // 往event中添加dataset
        IDataset dataset = DatasetService.getDefaultInstance().getDataset();
        dataset.addColumn("op_branch_no");//操作分支机构
        dataset.addColumn("op_entrust_way");//委托方式
        dataset.addColumn("op_station");//站点地址
        dataset.addColumn("branch_no");//分支机构
        dataset.addColumn("client_id");//客户编号
        dataset.addColumn("user_token");//用户口令
        dataset.addColumn("fund_account");//资产账户
        dataset.addColumn("password");//密码
        dataset.appendRow();
        dataset.updateString("op_branch_no", "");
        dataset.updateString("op_entrust_way", "7");
        dataset.updateString("op_station", "1");
        dataset.updateString("branch_no", branch_no);
        dataset.updateString("client_id", client_id);
        dataset.updateString("user_token", user_token);
        dataset.updateString("fund_account", account);
        dataset.updateString("password", password);
        event.setServiceId("333104");//打入功能号
        event.setStringAttributeValue("46", sysnode_id);//打入system node is 到46号域
        event.putEventData(dataset);//入参打包
        event.setIntegerAttributeValue(EventTagdef.TAG_SENDERID, 5);//分配节点为5
        unPack(event, event.getServiceId());//入参解包，同时打印发送包参数信息
        client.send(event);//发送包
    }

    //客户资金快速查询
    public void ReqFunction332255(String branch_no, String client_id, String user_token, String account, String password, String sysnode_id) throws Exception {
        // 获取event
        IEvent event = ContextUtil.getServiceContext().getEventFactory()
                .getEventByAlias("332255", EventType.ET_REQUEST);
        // 往event中添加dataset
        IDataset dataset = DatasetService.getDefaultInstance().getDataset();
        dataset.addColumn("op_branch_no");//操作分支机构
        dataset.addColumn("op_entrust_way");//委托方式
        dataset.addColumn("op_station");//站点地址
        dataset.addColumn("branch_no");//分支机构
        dataset.addColumn("client_id");//客户编号
        dataset.addColumn("user_token");//用户口令
        dataset.addColumn("fund_account");//资产账户
        dataset.addColumn("password");//密码
        dataset.appendRow();
        dataset.updateString("op_branch_no", "");
        dataset.updateString("op_entrust_way", "7");
        dataset.updateString("op_station", "1");
        dataset.updateString("branch_no", branch_no);
        dataset.updateString("client_id", client_id);
        dataset.updateString("user_token", user_token);
        dataset.updateString("fund_account", account);
        dataset.updateString("password", password);
        event.setServiceId("332255");//打入功能号
        event.setStringAttributeValue("46", sysnode_id);//打入system node is 到46号域
        event.putEventData(dataset);//入参打包
        event.setIntegerAttributeValue(EventTagdef.TAG_SENDERID, 5);//分配节点为5
        unPack(event, event.getServiceId()); //入参解包，同时打印发送包参数信息
        client.send(event);//发送包
    }

    //银行证券转账
    public void ReqFunction332200(String client_id, String branch_no, String user_token, String account, String password,
                                  String sysnode_id, String op_branch_no, String op_entrust_way, String op_station,
                                  String transfer_direction, String occur_balance, String bank_password, String bank_no) throws Exception {
        // 获取event
        IEvent event = ContextUtil.getServiceContext().getEventFactory()
                .getEventByAlias("332200", EventType.ET_REQUEST);
        // 往event中添加dataset
        IDataset dataset = DatasetService.getDefaultInstance().getDataset();
        dataset.addColumn("op_branch_no");//操作分支机构
        dataset.addColumn("op_entrust_way");//委托方式
        dataset.addColumn("op_station");//站点地址
        dataset.addColumn("branch_no");//分支机构
        dataset.addColumn("client_id");//客户编号
        dataset.addColumn("user_token");//用户口令
        dataset.addColumn("fund_account");//资产账户
        dataset.addColumn("password");//密码
        dataset.addColumn("request_num");//请求行数
        dataset.addColumn("money_type");//币种类别
        dataset.addColumn("bank_no");//银行代码
        dataset.addColumn("transfer_direction");//交易方向
        dataset.addColumn("occur_balance");//发生金额
        dataset.addColumn("bank_password");//银行密码
        dataset.appendRow();
        dataset.updateString("op_branch_no", op_branch_no);
        dataset.updateString("op_entrust_way", op_entrust_way); //op_entrust_way 打入7 网上交易
        dataset.updateString("op_station", op_station);
        dataset.updateString("branch_no", branch_no);
        dataset.updateString("client_id", client_id);
        dataset.updateString("user_token", user_token);
        dataset.updateString("fund_account", account);
        dataset.updateString("password", password);
        dataset.updateString("request_num", "1000");
        dataset.updateString("money_type", "0");
        dataset.updateString("bank_no", bank_no);
        dataset.updateString("transfer_direction", transfer_direction);
        dataset.updateString("occur_balance", occur_balance);
        dataset.updateString("bank_password", bank_password);
        event.setServiceId("332200");//打入功能号
        event.setStringAttributeValue("46", sysnode_id);//打入system node is 到46号域
        event.putEventData(dataset);//入参打包
        event.setIntegerAttributeValue(EventTagdef.TAG_SENDERID, 5); //分配节点为5
        unPack(event, event.getServiceId()); //入参解包，同时打印发送包参数信息
        client.send(event);//发送包
    }

    //证券委托回报
    public void ReqFunction620001_23(String account, String branch_no) throws Exception {
        ISubscriber subscriber = MCServers.GetSubscriber();
        MCSubscribeParameter subParam = new MCSubscribeParameter();
        subParam.SetTopicName("secu.entrust_update_notice");//设定主题
        subParam.SetFromNow(true);//自动补缺遗失消息
        subParam.SetReplace(false);//保留当前订阅 
        subParam.SetFilter("fund_account", account);//设定主键
        subParam.SetFilter("branch_no", branch_no);//设定主键
        int iRet = subscriber.SubscribeTopic(subParam, 3000);//大于0则订阅成功
        System.out.println("订阅序号" + iRet);
        if (iRet > 0) {
            System.out.println("证券委托回报订阅成功：\n" + "以下是相关入参：\n" + "资产账户:" + subParam.GetFilter().GetFilterValue("fund_account") + "|" + "分支机构：" + subParam.GetFilter().GetFilterValue("branch_no"));
        } else {
            System.out.println("证券委托回报订阅失败" + MCServers.GetErrorMsg(iRet));
        }

    }

    //主推-证券成交回报
    public void ReqFunction620001_12(String account, String branch_no) throws Exception {
        ISubscriber subscriber = MCServers.GetSubscriber();
        MCSubscribeParameter subParam = new MCSubscribeParameter();
        subParam.SetTopicName("secu.busin_push");//设定主题
        subParam.SetFromNow(true);//自动补缺遗失消息
        subParam.SetReplace(false);//保留当前订阅 
        subParam.SetFilter("fund_account", account);//设定主键
        subParam.SetFilter("branch_no", branch_no);//设定主键
        int iRet = subscriber.SubscribeTopic(subParam, 3000);//大于0则订阅成功
        System.out.println("订阅序号" + iRet);
        if (iRet > 0) {
            System.out.println("委托成交推送订阅成功：\n" + "以下是相关入参：\n" + "资产账户:" + subParam.GetFilter().GetFilterValue("fund_account") + "|" + "分支机构：" + subParam.GetFilter().GetFilterValue("branch_no"));
        } else {
            System.out.println("委托成交推送订阅失败" + MCServers.GetErrorMsg(iRet));
        }
    }

    public void unPack(IEvent event, String functionId) {
        StringBuilder sb = new StringBuilder();
        //获取返回集
        IDatasets result = event.getEventDatas();
        //获取结果集总数
        int datasetCount = result.getDatasetCount();
        //遍历所有的结果集
        for (int i = 0; i < datasetCount; i++) {
            //读取单个结果集信息
            IDataset ds = result.getDataset(0);
            int columnCount = ds.getColumnCount(); //获取数据数量
            //System.out.println(arg0.getServiceId());  
            //打印结果集标题
//            for (int j = 1; j <= columnCount; j++) {
//                sb.append(ds.getColumnName(j));
//                sb.append("|");
//            }
            //遍历单个结果集记录，遍历前首先将指针置到开始
            ds.beforeFirst();
            //打印结果集数据
            while (ds.hasNext()) {
//                sb.append("\n");
                ds.next();
                for (int j = 1; j <= columnCount; j++) {
                    sb.append(ds.getString(j)).append("|");
                }
//                sb.append("\n");
            }
        }
        System.out.println("功能号" + functionId + "运行:以下是相关入参：\n" + sb);//打印入参
//        logger4j.info("功能号" + functionId + "运行:以下是相关入参：\n" + sb);
    }

    public void InitT2() throws Exception {
        server.setT2sdkConfigString("config/t2sdk_hundsun-config.xml");// 配置文件路径
//        server.setT2sdkConfigString("/home/work/autoSMS/bin/t2sdk_hundsun-config.xml");// 配置文件路径
        server.init();
        server.start();
        MCServers.MCInit();

    }
}
