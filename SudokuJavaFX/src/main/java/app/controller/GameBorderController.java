package app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import app.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import pkgEnum.eGameDifficulty;

public class GameBorderController implements Initializable  {

	private Game game;	

	
	@FXML
	private MenuBar mnuMenu;
	
	@FXML
	private ToggleGroup tgDifficulty;
	
	@FXML
	private ToggleGroup tgPuzzleSize;
	
	@FXML
	private CheckMenuItem chkShowHints;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
	}
	public void setMainApp(Game game)
	{
		this.game = game;
	}
	@FXML
	private void mnuPuzzleSizeChange(ActionEvent event) {
	
		RadioMenuItem mi = (RadioMenuItem)event.getSource();
		System.out.println(mi.getId());
		switch (mi.getId())
		{
		case "4":
			this.game.setStageWidth(400);
			break;
		case "9":
			this.game.setStageWidth(600);
			break;

		}
		System.out.println("Puzzle Size Changed");
		
		
	}
	public eGameDifficulty geteGameDifficulty() {
		
		RadioMenuItem mi = (RadioMenuItem)tgDifficulty.getSelectedToggle(); 		
		eGameDifficulty eGD = eGameDifficulty.get(mi.getId());
		return eGD;
	}
	
	public int getPuzzleSize()
	{
		RadioMenuItem mi = (RadioMenuItem)tgPuzzleSize.getSelectedToggle(); 	
		return Integer.parseInt(mi.getId());		
	}
	
	public boolean getShowHints()
	{
		return 	chkShowHints.isSelected();
	}
	
	
}
