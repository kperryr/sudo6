package app;

import java.io.IOException;

import app.controller.GameBorderController;
import app.controller.SudokuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import pkgEnum.eGameDifficulty;
import pkgGame.Sudoku;

public class Game extends Application {

	private Stage primaryStage;
	private GameBorderController GBC = null;
	private SudokuController SC = null;
	private BorderPane GameBorderPane = null;
	private BorderPane SudokuPane = null;
	private Sudoku sudoku = null;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		HandleRoot();
		ShowSudoku();
	}

	public void HandleRoot() {
		// Parent root;
		try {

			FXMLLoader loader = new FXMLLoader();
			loader = new FXMLLoader(getClass().getResource("/game/app/view/GameBorder.fxml"));
			GameBorderPane = (BorderPane) loader.load();
			Scene scene = new Scene(GameBorderPane);

			primaryStage.setScene(scene);
			GBC = loader.getController();
			GBC.setMainApp(this);
			// primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ShowSudoku() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader = new FXMLLoader(getClass().getResource("/game/app/view/Sudoku.fxml"));
			SudokuPane = (BorderPane) loader.load();

			SudokuPane.prefHeight(500);
			SudokuPane.prefWidth(500);
			SudokuPane.setMinHeight(600);

			GameBorderPane.setCenter(SudokuPane);
			SC = loader.getController();
			SC.setMainApp(this);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setStageWidth(int iWidth) {
		primaryStage.setWidth(iWidth);
	}

	public Sudoku getSudoku() {
		return sudoku;
	}

	public BorderPane getGameBorderPane() {
		return GameBorderPane;
	}

	public BorderPane getSudokuPane() {
		return this.SudokuPane;
	}

	public eGameDifficulty geteGameDifficulty() {
		return this.GBC.geteGameDifficulty();
	}

	public int getPuzzleSize() {
		return this.GBC.getPuzzleSize();
	}

	public boolean getShowHints() {
		return this.GBC.getShowHints();
	}

	public Sudoku StartSudoku(int iSize, eGameDifficulty eGD) {
		try {
			this.sudoku = new Sudoku(iSize, eGD);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return this.sudoku;
	}

 

}
