/*
 Source: pbox.derivedClass
 Software Copyright: Hundsun Technologies Inc.
 System: Pbox Security Demo (Asynchronous)
 Module: Unpackage output parameter(Report push related)
 Describition:
 This class is receiveing reprot push related package from broker,and unpacking the package, the documents for function description and Java 
 programming helper can be found at pbox website: 
 Function id include: 
 author: Kai Zhan
 Date:2016/07/20
 Others:
 This demo is a sample for using Hundsun pbox System, we are highly not recommand any user to use this demo at any real market，for more details 
 please read <T2SDK JAVA Developer Manual>
 */
package uftdemo.com.service;

import com.hundsun.mcapi.interfaces.ISubCallback;
import com.hundsun.mcapi.subscribe.MCSubscribeParameter;
import com.hundsun.t2sdk.interfaces.IClient;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.dataset.IDatasets;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;
import java.util.logging.Logger;

public class UftMdCallBack implements ISubCallback {

    public static int g_iCount = 0;
    private Logger logger = Logger.getLogger("mdCallBack");
    private static IClient client = null;

    //设置节点
    public static void setClient(IClient client) {
        UftMdCallBack.client = client;
    }

    @Override
    public void OnReceived(String topicName, IEvent event) {
        // TODO Auto-generated method stub
        IDatasets result = event.getEventDatas();
        int datesetCount = result.getDatasetCount();
        StringBuilder sb = new StringBuilder();
        IDataset ds =event.getEventDatas().getDataset(0) ;
        int columnCount =ds.getColumnCount();
        if (datesetCount > 0) {
            if (topicName.equals("secu.entrust_update_notice")) {
                System.out.println("以下是委托变更推送:");
                for (int j = 1; j <= columnCount; j++) {
                    sb.append(ds.getColumnName(j));//打印数据标题
                    sb.append("|");
                    //sb.append(ds.getColumnType(j));//打印数据标题
                    //sb.append("\n");
                }
                // 遍历单个结果集记录，遍历前首先将指针置到开始
                ds.beforeFirst();
                while (ds.hasNext()) {
                    sb.append("\n");
                    ds.next();
                    for (int j = 1; j <= columnCount; j++) {
                        sb.append(ds.getString(j));//打印数据结果集
                        sb.append("|");
                    }
                }
                System.out.println(sb);
            } 
            if (topicName.equals("secu.busin_push")) {
                System.out.println("以下是委托成交推送:");
                for (int j = 1; j <= columnCount; j++) {
                    sb.append(ds.getColumnName(j));//打印数据标题
                    sb.append("|");
                    //sb.append(ds.getColumnType(j));//打印数据标题
                    //sb.append("\n");
                }
                // 遍历单个结果集记录，遍历前首先将指针置到开始
                ds.beforeFirst();
                while (ds.hasNext()) {
                    sb.append("\n");
                    ds.next();
                    for (int j = 1; j <= columnCount; j++) {
                        sb.append(ds.getString(j));//打印数据结果集
                        sb.append("|");
                    }
                }
                System.out.println(sb);
            }
        } else {
            System.out.println("无数据");
        }

    }

    @Override
    public void OnRecvTickMsg(MCSubscribeParameter param, String tickMsgInfo) {
        // TODO Auto-generated method stub

    }

}
