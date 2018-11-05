package app;

import static org.junit.Assert.*;

import org.junit.Test;

import app.helper.SudokuStyler;
import pkgGame.Cell;
import pkgGame.Sudoku;

public class SudokuStylerTest {

	@Test
	public void TestRegion() {
		Sudoku s = null;
		try {
			s = new Sudoku(9);
		} catch (Exception e) {
			fail("Sudoku Failed to build");
		}
		
		SudokuStyler ss = new SudokuStyler(s);
		
		
		assertEquals(false,s.bRegionCol(new Cell(0,0)));
		assertEquals(false,s.bRegionCol(new Cell(0,1)));
		assertEquals(true,s.bRegionCol(new Cell(0,2)));
		assertEquals(false,s.bRegionCol(new Cell(0,3)));
		assertEquals(false,s.bRegionCol(new Cell(0,4)));
		assertEquals(true,s.bRegionCol(new Cell(0,5)));
		assertEquals(false,s.bRegionCol(new Cell(0,6)));
		assertEquals(false,s.bRegionCol(new Cell(0,7)));
		assertEquals(true,s.bRegionCol(new Cell(0,8)));

	}

}
