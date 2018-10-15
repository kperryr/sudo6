package pkgGame;

import java.util.Objects;

public class Cell {
	private int iRow;
	private int iCol;

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

	@Override
	public int hashCode() {
		return Objects.hash(iRow, iCol);
	}

}
