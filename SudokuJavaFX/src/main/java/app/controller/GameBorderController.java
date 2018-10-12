package app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import app.Flamingo;
import javafx.fxml.Initializable;

public class GameBorderController implements Initializable  {


	private Flamingo FlamingoGame;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}

	public void setMainApp(Flamingo FlamingoGame)
	{
		this.FlamingoGame = FlamingoGame;
	}
}
