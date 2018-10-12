package app.controller;



import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import app.Flamingo;
//import pkgPokerBLL.Action;
//import pkgPokerEnum.eAction;

public class ClientServerStartController implements Initializable {


	@FXML
	private TextField txtPlayerName;
	@FXML
	private RadioButton rbtnServer;
	@FXML
	private RadioButton rbtnClient;
	@FXML
	private TextField txtServerPort;
	@FXML
	private TextField txtClientPort;
	@FXML
	private TextField txtComputerName;

	private Flamingo mainApp;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//	This is the crazy way you have to code to make an item
		//	the default selection / focus
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				txtPlayerName.requestFocus();
			}
		});
	}

	public void setMainApp(Flamingo mainApp) {
		this.mainApp = mainApp;
	}

	@FXML
	public void optServerClientSelected(ActionEvent event) {
		RadioButton rbServerClient = (RadioButton) event.getSource();
		switch (rbServerClient.getId().toString()) {
		case "rbtnServer":
			txtServerPort.setDisable(!rbServerClient.isSelected());
			txtClientPort.setDisable(rbServerClient.isSelected());
			txtComputerName.setDisable(rbServerClient.isSelected());
			break;
		case "rbtnClient":
			txtServerPort.setDisable(rbServerClient.isSelected());
			txtClientPort.setDisable(!rbServerClient.isSelected());
			txtComputerName.setDisable(!rbServerClient.isSelected());

			break;
		}
	}

	@FXML
	public void btnOK(ActionEvent event) {
		int iPort = 0;
		String strComputerName = "localhost";
		boolean bServer = false;
		if (rbtnServer.isSelected()) {
			bServer = true;
			iPort = Integer.parseInt(txtServerPort.getText());
		} else if (rbtnClient.isSelected()) {
			strComputerName = txtComputerName.getText();
			iPort = Integer.parseInt(txtClientPort.getText());
		}
				
	     mainApp.StartBlackJack(bServer, strComputerName, iPort, txtPlayerName.getText());
	}

	@FXML
	public void btnCancel(ActionEvent event) {
		Platform.exit();
		System.exit(0);

		System.out.println("End Program");

	}

}