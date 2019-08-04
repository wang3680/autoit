package util;

import javafx.scene.control.Alert;

public class AlertInfo {
    public void f_alert_informationDialog(String p_header, String p_message){
        Alert _alert = new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle("信息");
        _alert.setHeaderText(p_header);
        _alert.setContentText(p_message);
//        _alert.initOwner(d_stage);
        _alert.showAndWait();
    }
}
