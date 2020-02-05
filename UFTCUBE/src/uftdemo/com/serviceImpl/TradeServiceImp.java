package uftdemo.com.serviceImpl;

import com.hundsun.t2sdk.impl.client.T2Services;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import uftdemo.com.service.TradeService;
import uftdemo.com.service.UftInvestor;
import uftdemo.com.service.UftTradeCallBack;

import java.util.Date;

public class TradeServiceImp implements TradeService {

    private T2Services server = T2Services.getInstance();//T2实例化
    private static String global_user_token = null;
    private static String global_branch_no = null;
    private static String global_client_id = null;
    private static String global_sysnode_id = null;

}
