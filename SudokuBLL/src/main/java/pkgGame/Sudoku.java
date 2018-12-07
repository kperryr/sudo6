package pkgGame;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import pkgEnum.*;
import pkgEnum.ePuzzleViolation;
import pkgHelper.LatinSquare;
import pkgHelper.PuzzleViolation;

/**
 * Sudoku - This class extends LatinSquare, adding methods, constructor to
 * handle Sudoku logic
 * 
 * @version 1.2
 * @since Lab #2
 * @author Bert.Gibbons
 *
 */
public class Sudoku extends LatinSquare implements Serializable {

	/**
	 * 
	 * iSize - the length of the width/height of the Sudoku puzzle.
	 * 
	 * @version 1.2
	 * @since Lab #2
	 */
	private int iSize;

	/**
	 * iSqrtSize - SquareRoot of the iSize. If the iSize is 9, iSqrtSize will be
	 * calculated as 3
	 * 
	 * @version 1.2
	 * @since Lab #2
	 */

	private int iSqrtSize;

	private HashMap<Integer, SudokuCell> cells = new HashMap<Integer, SudokuCell>();

	private eGameDifficulty eGameDifficulty;

	/**
	 * Sudoku - No-arg private constructor should set the eGameDifficulty to EASY by
	 * default
	 * 
	 * @version 1.5
	 * @since Lab #5
	 */
	private Sudoku() {
		super();
		this.eGameDifficulty = eGameDifficulty.EASY;
	}

	/**
	 * Sudoku - for Lab #2... do the following:
	 * 
	 * set iSize If SquareRoot(iSize) is an integer, set iSqrtSize, otherwise throw
	 * exception
	 * 
	 * Lab #4 change - Add SetCells() and fillRemaining(Cell) call
	 * 
	 * @version 1.4
	 * @since Lab #2
	 * @param iSize- length of the width/height of the puzzle
	 * @throws Exception if the iSize given doesn't have a whole number square root
	 */
	public Sudoku(int iSize) throws Exception {

		this();
		this.iSize = iSize;

		double SQRT = Math.sqrt(iSize);
		if ((SQRT == Math.floor(SQRT)) && !Double.isInfinite(SQRT)) {
			this.iSqrtSize = (int) SQRT;
		} else {
			throw new Exception("Invalid size");
		}

		int[][] puzzle = new int[iSize][iSize];
		super.setLatinSquare(puzzle);

		FillDiagonalRegions();
		SetCells();
		fillRemaining(this.cells.get(Objects.hash(0, iSqrtSize)));
		RemoveCells();

	}

	/**
	 * Sudoku - Overloaded constructor passing in iSize and eGameDifficulty call the
	 * Sudoku(int) constructor, then set the difficulty, then RemoveCells()
	 * 
	 * @version 1.5
	 * @since Lab #5
	 * @param iSize - Size of the puzzle
	 * @param eGD   - Difficulty of the game
	 * @throws Exception
	 */
	public Sudoku(int iSize, eGameDifficulty eGD) throws Exception {
		this(iSize);
		this.eGameDifficulty = eGD;
		RemoveCells();
	}

	/**
	 * Sudoku - pass in a given two-dimensional array puzzle, create an instance.
	 * Set iSize and iSqrtSize
	 * 
	 * @version 1.2
	 * @since Lab #2
	 * @param puzzle - given (working) Sudoku puzzle. Use for testing
	 * @throws Exception will be thrown if the length of the puzzle do not have a
	 *                   whole number square root
	 */
	public Sudoku(int[][] puzzle) throws Exception {
		super(puzzle);
		this.iSize = puzzle.length;
		double SQRT = Math.sqrt(iSize);
		if ((SQRT == Math.floor(SQRT)) && !Double.isInfinite(SQRT)) {
			this.iSqrtSize = (int) SQRT;
		} else {
			throw new Exception("Invalid size");
		}

	}

	public int getiSqrtSize() {
		return iSqrtSize;
	}


	private int mistakes = 0;

	public void addMistakes() {
		this.mistakes++;
	}

	public int getMistakesCount() {
		return mistakes;
	}

	public  void iSudokuPuzzleSolved() {
		
		int sudopuzz[][]= getPuzzle();
		boolean isSolvedPuzzle= true;
		for(int i=0;i<iSize; i++) {
			for(int j=0; j<iSize; j++) 
				if (sudopuzz[i][j]==0) {
					isSolvedPuzzle=false;
				}
			}
		if (isSolvedPuzzle==true) {
			System.out.println("Yay, you solved the Sudoku!");
		}
	}
	
	public boolean GameOver() {
		return mistakes>= MaxMistakes;
	}
	
	
	
	/**
	 * RemoveCells - this method will remove cells (set them to zero) until the
	 * game's difficulty is met
	 * 
	 * @version 1.5
	 * @since Lab #5
	 */
	private void RemoveCells() {
		SetRemaingCells();

		do {
			Random rand = new SecureRandom();
			int iRandomRow = rand.nextInt(this.iSize);
			int iRandomCol = rand.nextInt(this.iSize);
			this.getPuzzle()[iRandomRow][iRandomCol] = 0;
			SetRemaingCells();
		} while (!IsDifficultyMet(PossibleValuesMultiplier(this.cells)));

	}

	/**
	 * IsDifficultyMet - will return boolean if the given difficulty score meets the
	 * game's difficulty
	 * 
	 * @version 1.5
	 * @since Lab #5
	 * @param eGD             - given
	 * @param iPossibleValues
	 * @return
	 */

	private boolean IsDifficultyMet(int iPossibleValues) {
		eGameDifficulty eActualGameDifficulty = eGameDifficulty.get(iPossibleValues);

		if (eActualGameDifficulty == null)
			return false;
		if (eActualGameDifficulty.getiDifficulty() >= this.eGameDifficulty.getiDifficulty())
			return true;

		return false;
	}

	/**
	 * getiSize - the UI needs to know the size of the puzzle
	 *
	 * 
	 * @version 1.5
	 * @since Lab #5
	 */
	public int getiSize() {
		return iSize;
	}

	private static boolean isRegionBoundary(double dSize) {
		double SQRT = Math.sqrt(dSize);
		if ((SQRT == Math.floor(SQRT)) && !Double.isInfinite(SQRT)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * SetCells - purpose of this method is to create a HashMap of all the cells in
	 * the puzzle. If the puzzle is 9X9, there will be 81 cells in the puzzle.
	 * 
	 * The key for the HashMap is the Cell's hash code The value for the HashMap is
	 * the Cell.
	 *
	 * The values in the HashSet for each cell's valid values should be shuffled
	 * 
	 * @version 1.4
	 * @since Lab #4
	 */
	private void SetCells() {
		for (int iRow = 0; iRow < iSize; iRow++) {
			for (int iCol = 0; iCol < iSize; iCol++) {
				SudokuCell c = new SudokuCell(iRow, iCol);
				c.setlstValidValues(getAllValidCellValues(iCol, iRow));
				c.ShuffleValidValues();
				cells.put(c.hashCode(), c);
			}
		}
	}

	/**
	 * SetRemaingCells - set lstRemainingValidValues, don't shuffle. Pretty close to
	 * SetCells()
	 * 
	 * @version 1.5
	 * @since Lab #5
	 */
	private void SetRemaingCells() {

		for (int iRow = 0; iRow < iSize; iRow++) {
			for (int iCol = 0; iCol < iSize; iCol++) {
				SudokuCell c = new SudokuCell(iRow, iCol);
				c.setlstRemainingValidValues(getAllValidCellValues(iCol, iRow));
				cells.put(c.hashCode(), c);
			}
		}
	}

	/**
	 * PossibleValuesMultiplier - will return back an integer calculated from the
	 * possible remaining values
	 * 
	 * Steps... Step 1: Generate the Sudoku (using constructor), fill entire puzzle,
	 * set the difficulty Step 2: Randomly set a cell to zero Step 3: set
	 * lstRemainingValidValues with remaining possible values Step 4: calculate
	 * PossibleValuesMultipler. Iterate through the 'cells' hashmap, multiplying the
	 * lstRemainingValues.Size for each cell. If the multiplier is greater than the
	 * MAX_VALUE for an Integer, return the Integer.MAX_VALUE Step 5: if the puzzle
	 * still doesn't meet the required difficulty, go to step 2
	 * 
	 * 
	 * @version 1.5
	 * @since Lab #5
	 * @param cells
	 * @return
	 */
	private static int PossibleValuesMultiplier(HashMap<Integer, SudokuCell> cells) {
		int iMultiplier = 1;
		Iterator it = cells.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			SudokuCell SC = (SudokuCell) pair.getValue();

			if (iMultiplier * SC.getLstRemainingValidValues().size() > Integer.MAX_VALUE) {
				return Integer.MAX_VALUE;
			} else {
				iMultiplier *= SC.getLstRemainingValidValues().size();
			}
		}
		return iMultiplier;
	}

	/**
	 * getAllCellNumbers - This method will return all the valid values remaining
	 * for a given cell (by Col/Row).
	 * 
	 * For example, Cell [0,0] shold return [3,4] 0 1 0 0 <br>
	 * 2 0 0 4 <br>
	 * 0 0 0 0 <br>
	 * 0 0 0 0 <br>
	 * 
	 * @version 1.4
	 * @since Lab #4
	 * @param iCol - given column
	 * @param iRow - given row
	 * @return
	 */
	private HashSet<Integer> getAllValidCellValues(int iCol, int iRow) {

		HashSet<Integer> hsCellRange = new HashSet<Integer>();
		for (int i = 0; i < iSize; i++) {
			hsCellRange.add(i + 1);
		}
		HashSet<Integer> hsUsedValues = new HashSet<Integer>();
		Collections.addAll(hsUsedValues, Arrays.stream(super.getRow(iRow)).boxed().toArray(Integer[]::new));
		Collections.addAll(hsUsedValues, Arrays.stream(super.getColumn(iCol)).boxed().toArray(Integer[]::new));
		Collections.addAll(hsUsedValues, Arrays.stream(this.getRegion(iCol, iRow)).boxed().toArray(Integer[]::new));

		hsCellRange.removeAll(hsUsedValues);

		if (this.getPuzzle()[iRow][iCol] != 0) {
			hsCellRange.add(this.getPuzzle()[iRow][iCol]);
		}

		return hsCellRange;
	}

	/**
	 * getPuzzle - return the Sudoku puzzle
	 * 
	 * @version 1.2
	 * @since Lab #2
	 * @return - returns the LatinSquare instance
	 */
	public int[][] getPuzzle() {
		return super.getLatinSquare();
	}

	/**
	 * hasDuplicates - Overload hasDuplicates() from LatinSquare, add region check
	 * 
	 * @version 1.3
	 * @since Lab #3
	 * @return - returns false if there are any duplicates in row, column or region
	 */
	@Override
	protected boolean hasDuplicates() {
		if (super.hasDuplicates())
			return true;

		for (int k = 0; k < this.getPuzzle().length; k++) {
			if (super.hasDuplicates(getRegion(k))) {
				super.AddPuzzleViolation(new PuzzleViolation(ePuzzleViolation.DupRegion, k));
			}
		}

		return (super.getPV().size() > 0);
	}

	/**
	 * getRegionNbr - Return region number based on given column and row
	 * 
	 * 
	 * Example, the following Puzzle:
	 * 
	 * 0 1 2 3 <br>
	 * 1 2 3 4 <br>
	 * 3 4 1 2 <br>
	 * 4 1 3 2 <br>
	 * 
	 * getRegionNbr(3,0) should return a value of 1
	 * 
	 * @param iCol - Given column number
	 * @param iRow - Given row number
	 * @version 1.3
	 * @since Lab #3
	 * 
	 * @return - return region number based on given column and row
	 */
	private int getRegionNbr(int iCol, int iRow) {

		int i = (iCol / iSqrtSize) + ((iRow / iSqrtSize) * iSqrtSize);

		return i;
	}

	/**
	 * getRegion - figure out what region you're in based on iCol and iRow and call
	 * getRegion(int)<br>
	 * 
	 * Example, the following Puzzle:
	 * 
	 * 0 1 2 3 <br>
	 * 1 2 3 4 <br>
	 * 3 4 1 2 <br>
	 * 4 1 3 2 <br>
	 * 
	 * getRegion(0,3) would call getRegion(1) and return [2],[3],[3],[4]
	 * 
	 * @version 1.2
	 * @since Lab #2
	 * @param iCol given column
	 * @param iRow given row
	 * @return - returns a one-dimensional array from a given region of the puzzle
	 */
	private int[] getRegion(int iCol, int iRow) {

		int i = (iCol / iSqrtSize) + ((iRow / iSqrtSize) * iSqrtSize);

		return getRegion(i);
	}

	/**
	 * getRegion - pass in a given region, get back a one-dimensional array of the
	 * region's content<br>
	 * 
	 * Example, the following Puzzle:
	 * 
	 * 0 1 2 3 <br>
	 * 1 2 3 4 <br>
	 * 3 4 1 2 <br>
	 * 4 1 3 2 <br>
	 * 
	 * getRegion(2) and return [3],[4],[4],[1]
	 * 
	 * @version 1.2
	 * @since Lab #2
	 * @param r given region
	 * @return - returns a one-dimensional array from a given region of the puzzle
	 */

	public int[] getRegion(int r) {

		int[] reg = new int[super.getLatinSquare().length];

		int i = (r % iSqrtSize) * iSqrtSize;
		int j = (r / iSqrtSize) * iSqrtSize;
		int iMax = i + iSqrtSize;
		int jMax = j + iSqrtSize;
		int iCnt = 0;

		for (; j < jMax; j++) {
			for (i = (r % iSqrtSize) * iSqrtSize; i < iMax; i++) {
				reg[iCnt++] = super.getLatinSquare()[j][i];
			}
		}

		return reg;
	}

	/**
	 * isPartialSudoku - return 'true' if...
	 * 
	 * It's a LatinSquare If each region doesn't have duplicates If each element in
	 * the first row of the puzzle is in each region of the puzzle At least one of
	 * the elemnts is a zero
	 * 
	 * 
	 * @version 1.2
	 * @since Lab #2
	 * @return true if the given puzzle is a partial sudoku
	 */
	public boolean isPartialSudoku() {

		super.setbIgnoreZero(true);

		super.ClearPuzzleViolation();

		if (hasDuplicates())
			return false;

		if (!ContainsZero()) {
			super.AddPuzzleViolation(new PuzzleViolation(ePuzzleViolation.MissingZero, -1));
			return false;
		}
		return true;

	}

	/**
	 * isSudoku - return 'true' if...
	 * 
	 * Is a partialSudoku Each element doesn't a zero
	 * 
	 * @version 1.2
	 * @since Lab #2
	 * @return - returns 'true' if it's a partialSudoku, element match (row versus
	 *         column) and no zeros
	 */
	public boolean isSudoku() {

		this.setbIgnoreZero(false);

		super.ClearPuzzleViolation();

		if (hasDuplicates())
			return false;

		if (!super.isLatinSquare())
			return false;

		for (int i = 1; i < super.getLatinSquare().length; i++) {

			if (!hasAllValues(getRow(0), getRegion(i))) {
				return false;
			}
		}

		if (ContainsZero()) {
			return false;
		}

		return true;
	}

	/**
	 * isValidValue - overload isValidValue, call by Cell
	 * 
	 * @version 1.4
	 * @since Lab #4
	 * @param c
	 * @param iValue
	 * @return
	 */
	public boolean isValidValue(SudokuCell c, int iValue) {
		return this.isValidValue(c.getiRow(), c.getiCol(), iValue);
	}

	/**
	 * isValidValue - test to see if a given value would 'work' for a given column /
	 * row
	 * 
	 * @version 1.2
	 * @since Lab #2
	 * @param iCol   puzzle column
	 * @param iRow   puzzle row
	 * @param iValue given value
	 * @return - returns 'true' if the proposed value is valid for the row and
	 *         column
	 */
	public boolean isValidValue(int iRow, int iCol, int iValue) {

		if (doesElementExist(super.getRow(iRow), iValue)) {
			return false;
		}
		if (doesElementExist(super.getColumn(iCol), iValue)) {
			return false;
		}
		if (doesElementExist(this.getRegion(iCol, iRow), iValue)) {
			return false;
		}

		return true;
	}

	public ArrayList<PuzzleViolation> FindViolations(int iRow, int iCol, int iValue) {
		ArrayList<PuzzleViolation> PV = new ArrayList<PuzzleViolation>();
		if (doesElementExist(super.getRow(iRow), iValue)) {
			PV.add(new PuzzleViolation(ePuzzleViolation.DupRow, iRow));
		}
		if (doesElementExist(super.getColumn(iCol), iValue)) {
			PV.add(new PuzzleViolation(ePuzzleViolation.DupCol, iCol));
		}
		if (doesElementExist(this.getRegion(iCol, iRow), iValue)) {
			PV.add(new PuzzleViolation(ePuzzleViolation.DupRegion, this.getRegionNbr(iCol, iRow)));
		}

		return PV;
	}

	/**
	 * PrintPuzzle This method will print the puzzle to the console (space between
	 * columns, line break after row)
	 * 
	 * @version 1.3
	 * @since Lab #3
	 */
	public void PrintPuzzle() {
		for (int i = 0; i < this.getPuzzle().length; i++) {
			System.out.println("");
			for (int j = 0; j < this.getPuzzle().length; j++) {
				System.out.print(this.getPuzzle()[i][j]);
				if ((j + 1) % iSqrtSize == 0)
					System.out.print(" ");
			}
			if ((i + 1) % iSqrtSize == 0)
				System.out.println(" ");

		}
		System.out.println("");
	}

	/**
	 * FillDiagonalRegions - After the puzzle is created, set the diagonal regions
	 * with random values
	 * 
	 * @version 1.3
	 * @since Lab #3
	 */
	private void FillDiagonalRegions() {

		for (int i = 0; i < iSize; i = i + iSqrtSize) {
			SetRegion(getRegionNbr(i, i));
			ShuffleRegion(getRegionNbr(i, i));
		}
	}

	/**
	 * fillRemaining - Recursive method to fill each cell... one by one...
	 * backtracking if the given value doesn't fit in the cell.
	 * 
	 * @version 1.4
	 * @since Lab #4
	 * 
	 * @param c - Cell that you're trying to fill
	 * @return
	 */
	private boolean fillRemaining(SudokuCell c) {

		if (c == null)
			return true;

		for (int num : c.getLstValidValues()) {
			if (isValidValue(c, num)) {
				this.getPuzzle()[c.getiRow()][c.getiCol()] = num;

				if (fillRemaining(c.GetNextCell(c)))
					return true;
				this.getPuzzle()[c.getiRow()][c.getiCol()] = 0;
			}
		}
		return false;

	}

	/**
	 * SetRegion - purpose of this method is to set the values of a given region
	 * (they will be shuffled later)
	 * 
	 * Example, the following Puzzle start state:
	 * 
	 * 0 0 0 0 <br>
	 * 0 0 0 0 <br>
	 * 0 0 0 0 <br>
	 * 0 0 0 0 <br>
	 * 
	 * SetRegion(2) would transform the Puzzle to:<br>
	 * 
	 * 0 0 0 0 <br>
	 * 0 0 0 0 <br>
	 * 1 2 0 0 <br>
	 * 3 4 0 0 <br>
	 * 
	 * @version 1.3
	 * @since Lab #3
	 * @param r - Given region number
	 */
	private void SetRegion(int r) {
		int iValue = 0;

		iValue = 1;
		for (int i = (r / iSqrtSize) * iSqrtSize; i < ((r / iSqrtSize) * iSqrtSize) + iSqrtSize; i++) {
			for (int j = (r % iSqrtSize) * iSqrtSize; j < ((r % iSqrtSize) * iSqrtSize) + iSqrtSize; j++) {
				this.getPuzzle()[i][j] = iValue++;
			}
		}
	}

	/**
	 * SetRegion - purpose of this method is to set the values of a given region
	 * (they will be shuffled later)
	 * 
	 * Example, the following Puzzle start state:
	 * 
	 * 1 2 0 0 <br>
	 * 3 4 0 0 <br>
	 * 0 0 0 0 <br>
	 * 0 0 0 0 <br>
	 * 
	 * ShuffleRegion(0) might transform the Puzzle to:<br>
	 * 
	 * 2 3 0 0 <br>
	 * 1 4 0 0 <br>
	 * 0 0 0 0 <br>
	 * 0 0 0 0 <br>
	 * 
	 * @version 1.3
	 * @since Lab #3
	 * @param r - Given region number
	 */
	private void ShuffleRegion(int r) {
		int[] region = getRegion(r);
		shuffleArray(region);
		int iCnt = 0;
		for (int i = (r / iSqrtSize) * iSqrtSize; i < ((r / iSqrtSize) * iSqrtSize) + iSqrtSize; i++) {
			for (int j = (r % iSqrtSize) * iSqrtSize; j < ((r % iSqrtSize) * iSqrtSize) + iSqrtSize; j++) {
				this.getPuzzle()[i][j] = region[iCnt++];
			}
		}
	}

	/**
	 * shuffleArray this method will shuffle a given one-dimension array
	 * 
	 * @version 1.3
	 * @since Lab #3
	 * @param ar given one-dimension array
	 */
	private void shuffleArray(int[] ar) {

		Random rand = new SecureRandom();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rand.nextInt(i + 1);
			// Simple swap
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}

	/**
	 * bFirstRow - return True if it's the first row of the puzzle
	 * 
	 * @version 1.5
	 * @since Lab #5
	 * @param c
	 * @return
	 */
	public boolean bFirstRow(Cell c) {
		if (c.getiRow() == 0)
			return true;
		else
			return false;
	}

	/**
	 * bFirstCol - return True if it's the first column in the puzzle
	 * 
	 * @version 1.5
	 * @since Lab #5
	 * @param c
	 * @return
	 */
	public boolean bFirstCol(Cell c) {
		if (c.getiCol() == 0)
			return true;
		else
			return false;
	}

	/**
	 * bFirstCell - return True if it's the first cell in the puzzle
	 * 
	 * @version 1.5
	 * @since Lab #5
	 * @param c
	 * @return
	 */
	public boolean bFirstCell(Cell c) {

		if ((bFirstRow(c)) && (bFirstCol(c)))
			return true;
		else
			return false;
	}

	/**
	 * bLastRow - return True if it's the last row in the puzzle
	 * 
	 * @version 1.5
	 * @since Lab #5
	 * @param c
	 * @return
	 */
	public boolean bLastRow(Cell c) {
		if (c.getiRow() + 1 == this.iSize)
			return true;
		else
			return false;
	}

	/**
	 * bLastCol - return True if it's the last column in the puzzle
	 * 
	 * @version 1.5
	 * @since Lab #5
	 * @param c
	 * @return
	 */
	public boolean bLastCol(Cell c) {
		if (c.getiCol() + 1 == this.iSize)
			return true;
		else
			return false;
	}

	/**
	 * bLastCell - return True if it's the last cell in the puzzle
	 * 
	 * @version 1.5
	 * @since Lab #5
	 * @param c
	 * @return
	 */
	public boolean bLastCell(Cell c) {
		if ((bLastRow(c)) && (bLastCol(c)))

			return true;
		else
			return false;
	}

	/**
	 * bRegionRow - return True if it's the last row in the region
	 * 
	 * @version 1.5
	 * @since Lab #5
	 * @param c
	 * @return
	 */
	public boolean bRegionRow(Cell c) {
		if ((c.getiRow() + 1) % this.iSqrtSize == 0)

			return true;
		else
			return false;
	}

	/**
	 * bRegionCol - return True if it's the last column in the region
	 * 
	 * @version 1.5
	 * @since Lab #5
	 * @param c
	 * @return
	 */
	public boolean bRegionCol(Cell c) {
		if ((c.getiCol() + 1) % this.iSqrtSize == 0)
			return true;
		else
			return false;
	}

	/**
	 * Cell - private class that handles possible remaining values
	 * 
	 * @version 1.4
	 * @since Lab #4
	 * @author Bert.Gibbons
	 *
	 */
	private class SudokuCell extends Cell {

		private int iRow;
		private int iCol;

		/**
		 * lstValidValues - ArrayList of initial valid values. Set once, in construction
		 * of sudoku
		 * 
		 * @version 1.4
		 * @since Lab #4
		 * @author Bert.Gibbons
		 */

		private ArrayList<Integer> lstValidValues = new ArrayList<Integer>();

		/**
		 * lstRemainingValidValues - ArrayList of remaining valid values
		 * 
		 * @version 1.5
		 * @since Lab #5
		 * @author Bert.Gibbons
		 */
		private ArrayList<Integer> lstRemainingValidValues = new ArrayList<Integer>();

		public SudokuCell(int iRow, int iCol) {
			super(iRow, iCol);
		}

		@Override
		public boolean equals(Object o) {

			if (o == this)
				return true;

			if (!(o instanceof SudokuCell)) {
				return false;
			}
			SudokuCell c = (SudokuCell) o;
			return iCol == c.iCol && iRow == c.iRow;

		}

		public ArrayList<Integer> getLstValidValues() {
			return lstValidValues;
		}

		/**
		 * getLstRemainingValidValues - Return the list of remaining valid values
		 * 
		 * @version 1.5
		 * @since Lab #5
		 * @author Bert.Gibbons
		 */
		public ArrayList<Integer> getLstRemainingValidValues() {
			return lstRemainingValidValues;
		}

		public void setlstValidValues(HashSet<Integer> hsValidValues) {
			lstValidValues = new ArrayList<Integer>(hsValidValues);
		}

		/**
		 * setlstRemainingValidValues - Set the remainingValues ArrayList
		 * 
		 * @version 1.5
		 * @since Lab #5
		 * @author Bert.Gibbons
		 */
		public void setlstRemainingValidValues(HashSet<Integer> hsRemainingValues) {
			lstRemainingValidValues = new ArrayList<Integer>(hsRemainingValues);
		}

		public void ShuffleValidValues() {
			Collections.shuffle(lstValidValues);
		}

		/**
		 * 
		 * GetNextCell - get the next cell, return 'null' if there isn't a next cell to
		 * find
		 * 
		 * @param c
		 * @param iSize
		 * @return
		 */
		public SudokuCell GetNextCell(SudokuCell c) {

			int iCol = c.getiCol() + 1;
			int iRow = c.getiRow();
			int iSqrtSize = (int) Math.sqrt(iSize);

			if (iCol >= iSize && iRow < iSize - 1) {
				iRow = iRow + 1;
				iCol = 0;
			}
			if (iRow >= iSize && iCol >= iSize)
				return null;

			if (iRow < iSqrtSize) {
				if (iCol < iSqrtSize)
					iCol = iSqrtSize;
			} else if (iRow < iSize - iSqrtSize) {
				if (iCol == (int) (iRow / iSqrtSize) * iSqrtSize)
					iCol = iCol + iSqrtSize;
			} else {
				if (iCol == iSize - iSqrtSize) {
					iRow = iRow + 1;
					iCol = 0;
					if (iRow >= iSize)
						return null;
				}
			}

			return (SudokuCell) cells.get(Objects.hash(iRow, iCol));

		}

	}
}
