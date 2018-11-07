package app.helper;

import javafx.scene.layout.StackPane;
import pkgGame.Cell;

public class SudokuCell extends StackPane {

	private Cell cell; 
	private int iCellValue;

	public SudokuCell (Cell c)
	{
		this.cell = c;
	}
	public int getiCellValue() {
		return iCellValue;
	}

	public void setiCellValue(int iCellValue) {
		this.iCellValue = iCellValue;
	}
	public Cell getCell() {
		return cell;
	}
 
	
	
	
}
