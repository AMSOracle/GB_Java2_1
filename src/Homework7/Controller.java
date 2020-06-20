package Homework7;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;


public class Controller implements MessageReaderListener{
    @FXML
    TextArea txtChat,txtWrite;
    @FXML
    Button btnSend;

    ChatClient ch;
    private void sendText(){
        if (!(txtWrite.getText().equals(""))){
            txtChat.appendText(txtWrite.getText()+"\n");
            try {
                ch.sendMsg(txtWrite.getText());
            }catch (IOException e){
                new Alert(Alert.AlertType.WARNING,"Ошибка отправки сообщения", ButtonType.OK).showAndWait();
            }
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

    public void setChatClient(ChatClient chatClient) {
        this.ch = chatClient;
        ch.addListener(this);
    }

    @Override
    public void newMessage(String msg) {
        txtChat.appendText(msg+"\n");
    }
}
