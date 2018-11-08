package app.controller;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import app.Game;
import app.helper.SudokuCell;
import app.helper.SudokuStyler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pkgEnum.eGameDifficulty;
import pkgGame.Cell;
import pkgGame.Sudoku;

public class SudokuController implements Initializable {

	private Game game;

	@FXML
	private GridPane gpTop;

	@FXML
	private HBox hboxGrid;

	@FXML
	private HBox hboxNumbers;

	private int iCellSize = 45;
	private static final DataFormat myFormat = new DataFormat("com.cisc181.Data.Cell");

	private eGameDifficulty eGD = null;
	private Sudoku s = null;

	public void setMainApp(Game game) {
		this.game = game;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	private void ButtonPush(ActionEvent event) {
		CreateSudokuInstance();
		BuildGrid();
	}

	private void CreateSudokuInstance() {
		eGD = this.game.geteGameDifficulty();
		s = game.StartSudoku(9, eGD);
	}

	private void BuildGrid() {

		// Paint the top grid on the form
		BuildTopGrid(eGD);
		GridPane gridSudoku = BuildSudokuGrid();

		gridSudoku.getStyleClass().add("GridPane");
		hboxGrid.getChildren().clear(); // Clear any controls in the VBox
		hboxGrid.getStyleClass().add("VBoxGameGrid");

		// Add the Grid to the Vbox
		hboxGrid.getChildren().add(gridSudoku);

		GridPane gridNumbers = BuildNumbersGrid();
		hboxNumbers.getChildren().add(gridNumbers);

	}

	private void BuildTopGrid(eGameDifficulty eGD) {
		gpTop.getChildren().clear();

		Label lblDifficulty = new Label(eGD.toString());
		gpTop.add(lblDifficulty, 0, 0);

		ColumnConstraints colCon = new ColumnConstraints();
		colCon.halignmentProperty().set(HPos.CENTER);
		gpTop.getColumnConstraints().add(colCon);

		RowConstraints rowCon = new RowConstraints();
		rowCon.valignmentProperty().set(VPos.CENTER);
		gpTop.getRowConstraints().add(rowCon);

		gpTop.getStyleClass().add("GridPaneInsets");
	}

	/**
	 * BuildNumbersGrid - this method will build the grid that has the avilable
	 * numbers to choose.
	 * 
	 * @return a GridPane, fully built & styled
	 */
	private GridPane BuildNumbersGrid() {
		Sudoku s = this.game.getSudoku();
		SudokuStyler ss = new SudokuStyler(s);
		GridPane gridPaneNumbers = new GridPane();
		gridPaneNumbers.setCenterShape(true);
		gridPaneNumbers.setMaxWidth(iCellSize + 15);
		for (int iCol = 0; iCol < s.getiSize(); iCol++) {
			ColumnConstraints colCon = new ColumnConstraints();
			colCon.setHgrow(Priority.NEVER); // This means the column will never grow, even if you re-size the scene
			colCon.halignmentProperty().set(HPos.CENTER); // Center the stuff you add to the column
			colCon.setMinWidth(iCellSize); // Set the width of the column
			gridPaneNumbers.getColumnConstraints().add(colCon);

			SudokuCell paneSource = new SudokuCell(new Cell(0, iCol));

			ImageView iv = new ImageView(GetImage(iCol + 1));
			paneSource.getCell().setiCellValue(iCol + 1);
			paneSource.getChildren().add(iv);

			paneSource.getStyleClass().clear(); // Clear any errant styling in the pane

			// Set a event handler to fire if the pane was clicked
			paneSource.setOnMouseClicked(e -> {
				System.out.println(paneSource.getCell().getiCellValue());
			});

			// Set an event handler to fire if the pane was dragged
			paneSource.setOnDragDetected(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {

					/* allow any transfer mode */
					Dragboard db = paneSource.startDragAndDrop(TransferMode.ANY);

					/* put a string on dragboard */
					// Put the Cell on the clipboard, on the other side, cast as a cell
					ClipboardContent content = new ClipboardContent();
					content.put(myFormat, paneSource.getCell());
					db.setContent(content);
					event.consume();
				}
			});

			// Add the pane to the grid
			gridPaneNumbers.add(paneSource, iCol, 0);

		}

		return gridPaneNumbers;
	}

	private GridPane BuildSudokuGrid() {

		Sudoku s = this.game.getSudoku();
		SudokuStyler ss = new SudokuStyler(s);
		GridPane gridPaneSudoku = new GridPane();
		gridPaneSudoku.setCenterShape(true);

		gridPaneSudoku.setMaxWidth(iCellSize * s.getiSize());
		gridPaneSudoku.setMaxHeight(iCellSize * s.getiSize());

		for (int iCol = 0; iCol < s.getiSize(); iCol++) {

			// ColumnConstraint is a generic rule... how every column in the grid should
			// behave
			ColumnConstraints colCon = new ColumnConstraints();
			colCon.setHgrow(Priority.NEVER); // This means the column will never grow, even if you re-size the scene
			colCon.halignmentProperty().set(HPos.CENTER); // Center the stuff you add to the column
			colCon.setMinWidth(iCellSize); // Set the width of the column
			gridPaneSudoku.getColumnConstraints().add(colCon);

			// RowConstraint is a generic rule... how every row in the grid should behave
			RowConstraints rowCon = new RowConstraints();
			rowCon.setMinHeight(iCellSize); // Set the height of the row
			rowCon.setVgrow(Priority.NEVER); // This means the row will never grow, even if you re-size the scene
			rowCon.valignmentProperty().set(VPos.CENTER); // Center the stuff added to the row
			gridPaneSudoku.getRowConstraints().add(rowCon);

			for (int iRow = 0; iRow < s.getiSize(); iRow++) {

				// The image control is going to be added to a StackPane, which can be centered
				SudokuCell paneTarget = new SudokuCell(new Cell(iRow, iCol));

				if (s.getPuzzle()[iRow][iCol] != 0) {
					ImageView iv = new ImageView(GetImage(s.getPuzzle()[iRow][iCol]));
					paneTarget.getCell().setiCellValue(s.getPuzzle()[iRow][iCol]);
					paneTarget.getChildren().add(iv);
				}
				paneTarget.getStyleClass().clear(); // Clear any errant styling in the pane
				paneTarget.setStyle(ss.getStyle(new Cell(iRow, iCol))); // Set the styling.

				paneTarget.setOnMouseClicked(e -> {
					System.out.println(paneTarget.getCell().getiCellValue());
				});

				paneTarget.setOnDragOver(new EventHandler<DragEvent>() {

					public void handle(DragEvent event) {
						if (event.getGestureSource() != paneTarget && event.getDragboard().hasContent(myFormat)) {

							// Don't let the user drag over items that already have a cell value set
							if (paneTarget.getCell().getiCellValue() == 0) {
								event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
							}
						}
						event.consume();
					}
				});

				paneTarget.setOnDragEntered(new EventHandler<DragEvent>() {
					public void handle(DragEvent event) {
						/* the drag-and-drop gesture entered the target */
						System.out.println("onDragEntered");
						/* show to the user that it is an actual gesture target */
						if (event.getGestureSource() != paneTarget && event.getDragboard().hasContent(myFormat)) {
							
							paneTarget.getStyleClass().clear();
							paneTarget.getStyleClass().add("bg-black-style");
						}

						event.consume();
					}
				});

				paneTarget.setOnDragExited(new EventHandler<DragEvent>() {
					public void handle(DragEvent event) {
/*						 mouse moved away, remove the graphical cues 
						Insets ins = paneTarget.getInsets();
						paneTarget.setBackground(
								new Background(new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, ins)));
*/
						event.consume();
					}
				});

				paneTarget.setOnDragDropped(new EventHandler<DragEvent>() {
					public void handle(DragEvent event) {

						Dragboard db = event.getDragboard();
						boolean success = false;

						if (db.hasContent(myFormat)) {
							Cell c = (Cell) db.getContent(myFormat);

							ImageView iv = new ImageView(GetImage(c.getiCellValue()));
							paneTarget.getCell().setiCellValue(c.getiCellValue());
							paneTarget.getChildren().clear();
							paneTarget.getChildren().add(iv);
							System.out.println(c.getiCellValue());
							success = true;
						}
						event.setDropCompleted(success);
						event.consume();
					}
				});

				gridPaneSudoku.add(paneTarget, iCol, iRow); // Add the pane to the grid
			}
		}

		return gridPaneSudoku;
	}

	private Image GetImage(int iValue) {
		InputStream is = getClass().getClassLoader().getResourceAsStream("img/" + iValue + ".png");
		return new Image(is);
	}
}
