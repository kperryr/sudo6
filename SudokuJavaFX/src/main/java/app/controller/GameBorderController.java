package app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import app.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import pkgEnum.eGameDifficulty;

public class GameBorderController implements Initializable  {

	private Game game;	
		
	@FXML
	private ToggleGroup tgDifficulty;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
	}
	public void setMainApp(Game game)
	{
		this.game = game;
	}
	public eGameDifficulty geteGameDifficulty() {
		
		RadioMenuItem mi = (RadioMenuItem)tgDifficulty.getSelectedToggle(); 		
		eGameDifficulty eGD = eGameDifficulty.get(mi.getId());
		return eGD;
	}
	
	
	
}
