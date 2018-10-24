package app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import app.Game;
import javafx.fxml.Initializable;

public class SudokuController  implements Initializable {

	private Game game;	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
	}
	public void setMainApp(Game game)
	{
		this.game = game;
	}
}
