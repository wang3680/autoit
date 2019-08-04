package controller;

import bean.IPOInfo;
import bean.IPOInfoString;
import demo.controller.MarketController;
import demo.controller.MarketPriceController;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.Callback;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.AlertInfo;
import util.IniUtil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class ShowIPOController implements Initializable{

	@FXML  
    private Pagination pn_pagination;  
    @FXML  
    private TableView<IPOInfo> tv_IPOInfotable;
    @FXML  
    private TableColumn<IPOInfo, String> tc_stock;
    @FXML  
    private TableColumn<IPOInfo, String> tc_stockName;  
    @FXML
    private TableColumn<IPOInfo, String> tc_ipoPrice;  
    @FXML  
    private TableColumn<IPOInfo, String> tc_marketDate;  
    @FXML  
    private TableColumn<IPOInfo, String> tc_upNum;  
    @FXML  
    private TableColumn<IPOInfo, String> tc_type;
    @FXML
    private TableColumn<IPOInfo, String> tc_upPrice;
    @FXML  
    private TableColumn<IPOInfo, String> tc_buttonIni;

    @FXML
    private TableColumn<IPOInfo, String> tc_price;
    @FXML
    private TableColumn<IPOInfo, String> tc_vol;
    @FXML
    private TableColumn<IPOInfo, String> tc_upRate;
    @FXML
    private TableColumn<IPOInfo, String> tc_openFlag;
    @FXML
    private TextField tv_money;

    AlertInfo alertInfo = new AlertInfo();
    IniUtil iniUtil = new IniUtil();
    static String configPath = "\\PBOX\\config.ini";
    File file = new File("");
    /**  
     * Initializes the controller class.  
     */  
    @Override  
    public void initialize(URL url, ResourceBundle rb) {  
        // TODO  
        tc_stock.setCellValueFactory(cellData -> cellData.getValue().stockProperty());

        tc_type.setCellFactory(col->{
            TableCell<IPOInfo, String> cell = new TableCell<IPOInfo, String>(){
                ObservableValue ov;
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if(!isEmpty()){
                        //ov = getTableColumn().getCellObservableValue(getIndex());
                        //System.out.println(item);
                        if (getTableRow() != null&&item.contains("上海")){
                            this.getTableRow().setStyle("-fx-background-color: #20B2AA");
                        }else{
                            this.getTableRow().setStyle("-fx-background-color: #4F94CD");
                        }
                    }
                }
            };

            return cell;
        });

        tc_type.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
//        tv_IPOInfotable.setRowFactory(col->{
//            TableRow<IPOInfo> tr = new TableRow<IPOInfo>(){
//                @Override
//                public void updateIndex(int i) {
//                    super.updateIndex(i);
//                    System.out.println(this.getTableView().getColumns().get(1));
//                }
//            };
//
//            return tr;
//        });
        tc_stockName.setCellValueFactory(cellData -> cellData.getValue().stockNameProperty());  
        tc_price.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        tc_vol.setCellValueFactory(cellData -> cellData.getValue().business_amountProperty());
        tc_ipoPrice.setCellValueFactory(cellData -> cellData.getValue().ipoPriceProperty());  
        tc_marketDate.setCellValueFactory(cellData -> cellData.getValue().marketDateProperty());  
        tc_upNum.setCellValueFactory(cellData -> cellData.getValue().upNumProperty());
        tc_upRate.setCellValueFactory(cellData -> cellData.getValue().upRateProperty());
        tc_upPrice.setCellValueFactory(cellData -> cellData.getValue().upPriceProperty());
        tc_openFlag.setCellValueFactory(cellData -> cellData.getValue().open_flagProperty());
        pn_pagination.setPageFactory(new Callback<Integer, Node>() {  
        @Override  
        public Node call(Integer param) {  
            showList(param);  
            return tv_IPOInfotable;  
        }
    });  
        tc_buttonIni.setCellFactory((col) ->{
        	TableCell<IPOInfo, String> cell = new TableCell<IPOInfo, String>(){

				@Override
				protected void updateItem(String arg0, boolean arg1) {
					// TODO Auto-generated method stub
					super.updateItem(arg0, arg1);
					Button button = new Button("set");
					this.setGraphic(button);
					button.setOnMouseClicked((bu)->{
						IPOInfo ipoInfo = this.getTableView().getItems().get(this.getIndex());
                        String textFild = tv_money.getText().toString();
                        if(textFild.length()==0){
                            alertInfo.f_alert_informationDialog("请设置金额!",textFild);
                        }else{
                            try {
                                String []a =iniUtil.writeByPathName(file.getCanonicalPath()+configPath,ipoInfo,textFild);
                                alertInfo.f_alert_informationDialog("设置成功!",
                                        "股票："+a[0]+" 价格："+a[1]+" 数量："+a[2]);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
					});
				}

        	};
        	return cell;
        });
        /**
        tc_type.setCellFactory((col)->{
            TableCell<IPOInfo, String> cell = new TableCell<IPOInfo, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    Text text = new Text();
                    IPOInfo ipoInfo = this.getTableView().getItems().get(this.getIndex());
                    String a = ipoInfo.getStock().substring(7,9);
                    System.out.println("click:"+a);
                    if(a.equalsIgnoreCase("SS")){
                        System.out.println("SS");
                        text.setText("上海");
                    }else{
                        System.out.println("SZ");
                        text.setText("深圳");
                    }
                    this.setGraphic(text);
                }
            };
            return cell;
        });
         **/
    }    
      
    private void showList(Integer pagesize){  
        ObservableList<IPOInfo> list = FXCollections.observableArrayList();  
        //直接填充数据，可以通过数据库等数据
        /**
        IPOInfoString Stringperson1 = new IPOInfoString();  
        Stringperson1.setSecu_code("600570"+pagesize);  
        Stringperson1.setSecu_abbr("恒生电子"); 
        
        IPOInfo person1 = new IPOInfo(Stringperson1);  
        IPOInfoString Stringperson2 = new IPOInfoString();  
        Stringperson2.setSecu_code("000001"+pagesize);  
        Stringperson2.setSecu_abbr("平安银行");  
        IPOInfo person2 = new IPOInfo(Stringperson2);  
        list.add(person1);  
        list.add(person2);
        **/

        MarketController marketController = null;
        MarketPriceController marketPriceController = null;
        try {
            marketController = new MarketController();
            marketPriceController = new MarketPriceController();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String a;
		try {
			a = marketPriceController.getIPOInfo();
			JSONObject jsonO = JSONObject.fromObject(a);
			JSONArray j= jsonO.getJSONArray("data");
			ObservableList<IPOInfoString> listString= FXCollections.observableList(JSONArray.toList(j,IPOInfoString.class));
			for(int i=0; i<listString.size();i++){
				list.add(new IPOInfo(listString.get(i),marketController));
			}
		} catch (KeyManagementException | NoSuchAlgorithmException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        tv_IPOInfotable.setItems(list);  
    }  
}
