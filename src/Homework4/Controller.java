package Homework4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class Controller {
    @FXML
    TextArea txtChat,txtWrite;
    @FXML
    Button btnSend;
    private void sendText(){
        if (!(txtWrite.getText().equals(""))){
            txtChat.appendText(txtWrite.getText()+"\n");
        }
        txtWrite.clear();
    }
    public void btnSendClicked(ActionEvent actionEvent) {
        sendText();
    }
    public void keyTyped(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER){
            keyEvent.consume();
            sendText();
        }
    }
}
