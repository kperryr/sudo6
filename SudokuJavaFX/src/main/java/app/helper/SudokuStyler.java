package app.helper;

import pkgGame.Cell;
import pkgGame.Sudoku;

public class SudokuStyler {

	private Sudoku s;
	
	public SudokuStyler(Sudoku s)
	{
		this.s = s;
	}
	
	public String getStyle(Cell c)
	{
		String strStyle = "-fx-background-color: black, white;";
		
		if ((c.getiRow() == 0) && (c.getiCol() ==0))
		{
			strStyle += "-fx-background-insets: 0, 5 1 1 5;";
		}
		else if (c.getiRow() == s.getiSize())
		{
			strStyle += "-fx-background-insets: 0, 1 1 5 1;";
		}
		else if (c.getiRow() == 0)
		{
			strStyle += "-fx-background-insets: 0, 5 1 1 1;";
		}
		else if (c.getiCol() == 0)
		{
			strStyle += "-fx-background-insets: 0, 1 1 1 5;";
		}		
		
		return strStyle;
	}
	
}
