package app.controller;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import app.Game;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
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
		Sudoku s = game.StartSudoku(9);

		vboxCenter.getChildren().clear();
		GridPane gridPane = new GridPane();
		gridPane.getStyleClass().add("GridPane");
		gridPane.setCenterShape(true);
		gridPane.setVgap(10);
		gridPane.setHgap(10);

		for (int iCol = 0; iCol < s.getiSize(); iCol++) {
			ColumnConstraints colCon = new ColumnConstraints();
			// colCon.setMaxWidth(100);
			colCon.setHgrow(Priority.NEVER);
			colCon.halignmentProperty().set(HPos.CENTER);
			colCon.setMinWidth(35);
			gridPane.getColumnConstraints().add(colCon);

			RowConstraints rowCon = new RowConstraints();
			rowCon.setMinHeight(35);
			rowCon.setVgrow(Priority.NEVER);
			rowCon.valignmentProperty().set(VPos.CENTER);
			gridPane.getRowConstraints().add(rowCon);

			for (int iRow = 0; iRow < s.getiSize(); iRow++) {
				Pane pane = new Pane();
				ImageView iv = new ImageView(GetImage(s.getPuzzle()[iRow][iCol]));				
				pane.getChildren().add(iv);		
				
                pane.getStyleClass().add("game-grid-cell");
                if (iCol == 0) {
                    pane.getStyleClass().add("first-column");
                }
                if (iRow == 0) {
                    pane.getStyleClass().add("first-row");
                }
                
                if (iRow == 3)
                {
                		pane.getStyleClass().add("my-grid-pane");
                }
		
				gridPane.add(pane, iCol, iRow);
			}
		}
		vboxCenter.getChildren().add(gridPane);
	}

	private Image GetImage(int iValue) {
		InputStream is = getClass().getClassLoader().getResourceAsStream("img/" + iValue + ".png");
		return new Image(is);
	}
}
