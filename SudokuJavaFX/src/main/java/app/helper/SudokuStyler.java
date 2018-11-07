package app.helper;

import pkgGame.Cell;
import pkgGame.Sudoku;

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
	 * @param c
	 * @return
	 */
	private int iGetTop(Cell c)
	{
		if (s.bFirstRow(c)) {
			return iPuzzleBorder;
		}
		return iCellBorder;
	}
	
	/**
	 * iGetBottom - Figure out the width of the 'bottom' part of the cell
	 * @param c
	 * @return
	 */
	private int iGetBottom(Cell c)
	{
		if (s.bRegionRow(c)) {
			return iRegionBorder;
		}
		else if (s.bLastRow(c)) {
			return iPuzzleBorder;
		}
		else
			return iCellBorder;
	}
	
	/**
	 * iGetLeft - Figure out the width of the 'left' part of the cell
	 * @param c
	 * @return
	 */
	private int iGetLeft(Cell c)
	{
		if (s.bFirstCol(c)) {
			return iPuzzleBorder;
		}
		else
			return iCellBorder;
	}
	
	/**
	 * iGetRight - Figure out the width of the 'right' part of the cell
	 * @param c
	 * @return
	 */
	private int iGetRight(Cell c)
	{
		if (s.bLastCol(c)) {
			return iPuzzleBorder;
		}
		else if (s.bRegionCol(c))
		{
			return iRegionBorder;
		}
		else
			return iCellBorder;
	}
	
	/**
	 * getStyle - Figure out the styling of the cell (color, cell border)
	 * @param c
	 * @return
	 */
	public String getStyle(Cell c) {
		String strStyle = "-fx-background-color: black, white; ";
		
		strStyle += String.format("-fx-background-insets: 0, %1$s %2$s %3$s %4$s;", 
				iGetTop(c), iGetRight(c), iGetBottom(c), iGetLeft(c));
		
		return strStyle;
	}

}
