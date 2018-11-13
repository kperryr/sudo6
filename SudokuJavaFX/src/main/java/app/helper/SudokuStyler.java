package app.helper;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import pkgGame.Cell;
import pkgGame.Sudoku;
import pkgHelper.PuzzleViolation;

public class SudokuStyler {

	private int iPuzzleBorder = 5;
	private int iRegionBorder = 3;
	private int iCellBorder = 1;

	private Sudoku s;

	public SudokuStyler(Sudoku s) {
		this.s = s;
	}

	/**
	 * iGetTop - Figure out the width of the 'top' part of the cell
	 * 
	 * @param c
	 * @return
	 */
	private int iGetTop(Cell c) {
		if (s.bFirstRow(c)) {
			return iPuzzleBorder;
		}
		return iCellBorder;
	}

	/**
	 * iGetBottom - Figure out the width of the 'bottom' part of the cell
	 * 
	 * @param c
	 * @return
	 */
	private int iGetBottom(Cell c) {
		if (s.bRegionRow(c)) {
			return iRegionBorder;
		} else if (s.bLastRow(c)) {
			return iPuzzleBorder;
		} else
			return iCellBorder;
	}

	/**
	 * iGetLeft - Figure out the width of the 'left' part of the cell
	 * 
	 * @param c
	 * @return
	 */
	private int iGetLeft(Cell c) {
		if (s.bFirstCol(c)) {
			return iPuzzleBorder;
		} else
			return iCellBorder;
	}

	/**
	 * iGetRight - Figure out the width of the 'right' part of the cell
	 * 
	 * @param c
	 * @return
	 */
	private int iGetRight(Cell c) {
		if (s.bLastCol(c)) {
			return iPuzzleBorder;
		} else if (s.bRegionCol(c)) {
			return iRegionBorder;
		} else
			return iCellBorder;
	}

	/**
	 * getStyle - Figure out the styling of the cell (color, cell border)
	 * 
	 * @param c
	 * @return
	 */
	public String getStyle(Cell c) {
		String strStyle = "-fx-background-color: black, white; ";

		strStyle += String.format("-fx-background-insets: 0, %1$s %2$s %3$s %4$s;", iGetTop(c), iGetRight(c),
				iGetBottom(c), iGetLeft(c));

		return strStyle;
	}

	public static ColumnConstraints getGenericColumnConstraint(int iCellSize) {
		ColumnConstraints colCon = new ColumnConstraints();
		colCon.setHgrow(Priority.NEVER); // This means the column will never grow, even if you re-size the scene
		colCon.halignmentProperty().set(HPos.CENTER); // Center the stuff you add to the column
		colCon.setMinWidth(iCellSize); // Set the width of the column

		return colCon;
	}

	public static RowConstraints getGenericRowConstraint(int iCellSize) {
		// RowConstraint is a generic rule... how every row in the grid should behave
		RowConstraints rowCon = new RowConstraints();
		rowCon.setMinHeight(iCellSize); // Set the height of the row
		rowCon.setVgrow(Priority.NEVER); // This means the row will never grow, even if you re-size the scene
		rowCon.valignmentProperty().set(VPos.CENTER); // Center the stuff added to the row
		return rowCon;
	}

	public static Pane getRedPane() {
		Pane p = new Pane();
		String strStyle = "-fx-background-color: #AA0000; ";
		p.setStyle(strStyle);
		return p;
	}

	public static void HandlePuzzleViolations(GridPane gp, ArrayList<PuzzleViolation> PVs) {
		Pane p = getRedPane();

		Platform.runLater(() -> {
			for (PuzzleViolation pv : PVs) {
				for (Node child : gp.getChildren()) {
					Integer column = GridPane.getColumnIndex(child);
					Integer row = GridPane.getRowIndex(child);

					SudokuCell sc = (SudokuCell) child;
					sc.getStyleClass().clear();
					sc.setStyle("-fx-background-color: #AA0000;");

					// sc.getChildren().add(0, p);
				}
			}
		});
	}

	public static void RemoveGridStyling(GridPane gp) {
		Platform.runLater(() -> {
			for (Node child : gp.getChildren()) {
				Integer column = GridPane.getColumnIndex(child);
				Integer row = GridPane.getRowIndex(child);

				SudokuCell sc = (SudokuCell) child;
				sc.getStyleClass().clear();
			}
		});
	}

}
