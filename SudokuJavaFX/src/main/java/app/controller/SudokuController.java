package app.controller;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import app.Game;
import app.helper.SudokuStyler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pkgEnum.eGameDifficulty;
import pkgGame.Cell;
import pkgGame.Sudoku;

public class SudokuController implements Initializable {

	private Game game;

	@FXML
	private VBox vboxCenter;
	


	public void setMainApp(Game game) {
		this.game = game;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	private void ButtonPush(ActionEvent event) {
		BuildGrid();
	}

	private void BuildGrid() {
		
		Sudoku s = game.StartSudoku(9, eGameDifficulty.HARD);			
		GridPane gridSudoku = BuildSudoku();		
		
		vboxCenter.getChildren().clear();	// Clear any controls in the VBox
	
		
		
		//	Add the Grid to the Vbox
		vboxCenter.getChildren().add(gridSudoku);

		
		
		
/*		
		GridPane gridPaneNumbers = new GridPane();
		gridPaneNumbers.setCenterShape(true);
		ColumnConstraints colCon = new ColumnConstraints();
		colCon.setHgrow(Priority.NEVER); // This means the column will never grow, even if you re-size the scene
		colCon.halignmentProperty().set(HPos.CENTER);	//	Center the stuff you add to the column
		colCon.setMinWidth(45);		// Set the width of the column
		gridPaneNumbers.getColumnConstraints().add(colCon);
		
		RowConstraints rowCon = new RowConstraints();
		rowCon.setMinHeight(45);	//	Set the height of the row
		rowCon.setVgrow(Priority.NEVER);	// This means the row will never grow, even if you re-size the scene
		rowCon.valignmentProperty().set(VPos.CENTER);	// Center the stuff added to the row
		gridPaneNumbers.getRowConstraints().add(rowCon);
		
		for (int iCol = 0; iCol < s.getiSize(); iCol++) {
			StackPane p = new StackPane();		
			p.getChildren().add(new ImageView(GetImage(iCol + 1)));						
			gridPaneNumbers.add(p, iCol, 0);
			
		}
		
		vboxCenter.getChildren().add(gridPaneNumbers);*/
		
		
		
	}
	
	private GridPane BuildSudoku()
	{
		Sudoku s = this.game.getSudoku();
		SudokuStyler ss = new SudokuStyler(s);
		GridPane gridPaneSudoku = new GridPane();
		gridPaneSudoku.setCenterShape(true);

		for (int iCol = 0; iCol < s.getiSize(); iCol++) {
			
			//	ColumnConstraint is a generic rule... how every column in the grid should behave
			ColumnConstraints colCon = new ColumnConstraints();
			colCon.setHgrow(Priority.NEVER); // This means the column will never grow, even if you re-size the scene
			colCon.halignmentProperty().set(HPos.CENTER);	//	Center the stuff you add to the column
			colCon.setMinWidth(45);		// Set the width of the column
			gridPaneSudoku.getColumnConstraints().add(colCon);

			//	RowConstraint is a generic rule... how every row in the grid should behave
			RowConstraints rowCon = new RowConstraints();
			rowCon.setMinHeight(45);	//	Set the height of the row
			rowCon.setVgrow(Priority.NEVER);	// This means the row will never grow, even if you re-size the scene
			rowCon.valignmentProperty().set(VPos.CENTER);	// Center the stuff added to the row
			gridPaneSudoku.getRowConstraints().add(rowCon);

			for (int iRow = 0; iRow < s.getiSize(); iRow++) {

				//	The image control is going to be added to a StackPane, which can be centered
				StackPane pane = new StackPane();

				if (s.getPuzzle()[iRow][iCol] != 0) {
					ImageView iv = new ImageView(GetImage(s.getPuzzle()[iRow][iCol]));
					pane.getChildren().add(iv);
				}			
				pane.getStyleClass().clear();	// Clear any errant styling in the pane				
				pane.setStyle(ss.getStyle(new Cell(iRow, iCol)));	// Set the styling.  
				gridPaneSudoku.add(pane, iCol, iRow);	// Add the pane to the grid
			}
		}
		
		return gridPaneSudoku;
	}

	private Image GetImage(int iValue) {
		InputStream is = getClass().getClassLoader().getResourceAsStream("img/" + iValue + ".png");
		return new Image(is);
	}
}
