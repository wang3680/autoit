/*
Source: pbox.derivedClass
Software Copyright: Hundsun Technologies Inc.
System: Pbox Security Demo (Asynchronous)
Module: Unpackage output parameter(Trading realated)
Describition:
This class is receiveing trading related package from broker,and unpacking the package, the documents for function description and Java 
programming helper can be found at pbox website: 
Function id include: 
author: Kai Zhan
Date:2016/07/14
Others:
This demo is a sample for using Hundsun pbox System, we are highly not recommand any user to use this demo at any real market，for more details 
please read <T2SDK JAVA Developer Manual>
*/
package uftdemo.com.service;

import com.hundsun.t2sdk.impl.client.ClientSocket;
import com.hundsun.t2sdk.interfaces.ICallBackMethod;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;
import com.hundsun.t2sdk.interfaces.share.event.EventReturnCode;
import com.hundsun.t2sdk.interfaces.share.event.EventTagdef;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;
import javafx.scene.control.TextArea;

import java.util.logging.Logger;

public class UftTradeCallBackTest implements ICallBackMethod {

    //private pboxTrade TradeRuest;
    private static String glo_user_token = null;
    private static String glo_branch_no = null;
    private static String glo_client_id = null;
    private static String glo_sysnode_id = null;
    private Logger logger = Logger.getLogger("tradeCallBack");//打印节点
    private static int glo_number = 0;

    public int datasetCount = 0;

    private static String returnNumber = null;

    public String getReturnNumber(){
        return returnNumber;
    }
    //抓取user_token
    public String user_token() {
        return glo_user_token;
    }
    //抓取branch_no
    public String branch_no() {
        return glo_branch_no;
    }

    //抓取client_id
    public String client_id(){
        return glo_client_id;
    }

    //抓取sysnode_id
    public String sysnode_id() {
        return glo_sysnode_id;
    }

    @Override
    public void execute(IEvent arg0, ClientSocket arg1) {
        //获取功能号
        long iFunctionID = arg0.getIntegerAttributeValue(EventTagdef.TAG_FUNCTION_ID);
        returnNumber = arg0.getErrorNo();
        //判断返回值
        if (arg0.getReturnCode() != EventReturnCode.I_OK) {
            //返回T2错误
            System.out.println(arg0.getErrorNo() + " : " + arg0.getErrorInfo());
            //返回业务错误
            StringBuilder sb = new StringBuilder();
            //获取结果集
            IDatasets result = arg0.getEventDatas();
            //获取结果集个数
            datasetCount = result.getDatasetCount();
            //System.out.println(arg0.getServiceId());
            //遍历结果集
            for (int i = 0; i < datasetCount; i++) {
                IDataset ds = result.getDataset(i);
                int columnCount = ds.getColumnCount();
                //打印结果集标注
                for (int j = 1; j <= columnCount; j++) {
                    sb.append(ds.getColumnName(j));
                    sb.append("|");
                }
                //遍历单个结果集记录，遍历前首先将指针置到开始
                ds.beforeFirst();
                while (ds.hasNext()) {
                    sb.append("\n");
                    ds.next();
                    //打印结果集数据
                    for (int j = 1; j <= columnCount; j++) {
                        sb.append(ds.getString(j)).append("|");
                    }
                }
            }
            System.err.println("以下是错误信息999: \n" + sb);

        } else if (iFunctionID == 331100) {//362000 登录回调解包
            StringBuilder sb = new StringBuilder();
            //获取返回集
            IDatasets result = arg0.getEventDatas();
            //获取结果集总数
            int datasetCount = result.getDatasetCount();
            //System.out.println(arg0.getServiceId());//打印功能号
            //遍历所有的结果集
            for (int i = 0; i < datasetCount; i++) {
                //读取单个结果集信息
                IDataset ds = result.getDataset(0);
                glo_user_token = ds.getString("user_token");//从出参中提取user_token
                glo_branch_no = ds.getString("branch_no");//从出参中提取branch_no
                glo_client_id = ds.getString("client_id");//从出参中提取client_id
                glo_sysnode_id = ds.getString("sysnode_id"); //从参数中提取sysnode_id
                int columnCount = ds.getColumnCount();//获取数据数量
                //System.out.println(arg0.getServiceId());
                //打印结果集标题
                for (int j = 1; j <= columnCount; j++) {
                    sb.append(ds.getColumnName(j));
                    sb.append("|");
                }
                //遍历单个结果集记录，遍历前首先将指针置到开始
                ds.beforeFirst();
                //打印结果集数据
                while (ds.hasNext()) {
                    sb.append("\n");
                    ds.next();
                    for (int j = 1; j <= columnCount; j++) {
                        sb.append(ds.getString(j)).append("|");
                    }
                }
            }
            System.out.println("以下是相关出参：\n"+sb);

        } else {
            StringBuilder sb = new StringBuilder();
            //获取结果集
            IDatasets result = arg0.getEventDatas();
            //获取结果集个数
            int datasetCount = result.getDatasetCount();
            //System.out.println(arg0.getServiceId());//打印功能号
            //遍历结果集
            for (int i = 0; i < datasetCount; i++) {
                IDataset ds = result.getDataset(i);//里边结果集
                int columnCount = ds.getColumnCount();//获取结果集总数
                for (int j = 1; j <= columnCount; j++) {
                    //获取结果集标题
                    sb.append(ds.getColumnName(j));
                    sb.append("|");
                }
                ds.beforeFirst();
                //遍历单个结果集记录，遍历前首先将指针置到开始
                while (ds.hasNext()) {
                    sb.append("\n");
                    ds.next();
                    //打印结果集数据
                    for (int j = 1; j <= columnCount; j++) {
                        sb.append(ds.getString(j)).append("|");
                    }
                }
            }
            System.out.println("以下是相关出参：\n"+sb);
        }
    }
}
