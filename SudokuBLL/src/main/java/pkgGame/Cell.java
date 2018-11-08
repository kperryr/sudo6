package pkgGame;

import java.io.Serializable;
import java.util.Objects;

public class Cell implements Serializable {
	private int iRow;
	private int iCol;
	private int iCellValue;

	public Cell(int iRow, int iCol) {
		super();
		this.iRow = iRow;
		this.iCol = iCol;
	}

	public int getiRow() {
		return iRow;
	}

	public int getiCol() {
		return iCol;
	}

	public int getiCellValue() {
		return iCellValue;
	}

	public void setiCellValue(int iCellValue) {
		this.iCellValue = iCellValue;
	}

	@Override
	public int hashCode() {
		return Objects.hash(iRow, iCol);
	}

}
