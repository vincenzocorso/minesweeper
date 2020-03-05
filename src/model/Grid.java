package minesweeper.model;

public class Grid {
	private final int rowsNumber;
	private final int columnsNumber;
	private final int minesNumber;
	private final Sign[][] grid;
	private final int[] mines;
	private boolean initialised;

	public Grid(int rowsNumber, int columnsNumber, int minesNumber)
	throws IllegalArgumentException {
		if(rowsNumber <= 0 || columnsNumber <= 0 || minesNumber <= 0)
			throw new IllegalArgumentException();

		this.rowsNumber = rowsNumber;
		this.columnsNumber = columnsNumber;
		this.minesNumber = minesNumber;
		this.grid = new Sign[rowsNumber][columnsNumber];
		this.mines = new int[2 * minesNumber];
		this.initialised = false;
	}

	public int getRowsNumber() {
		return this.rowsNumber;
	}

	public int getColumnsNumber() {
		return this.columnsNumber;
	}

	public int getMinesNumber() {
		return this.minesNumber;
	}

	public Sign getCell(int row, int column)
	throws IllegalArgumentException {
		if(row < 0 || row >= this.rowsNumber || column < 0 || column >= this.columnsNumber)
			throw new IllegalArgumentException();

		return this.grid[row][column];
	}

	public int[] getMines() {
		return this.mines;
	}

	public boolean isInitialised() {
		return this.initialised;
	}

	public void init(int startRow, int startColumn)
	throws IllegalArgumentException, IllegalStateException {
		if(startRow < 0 || startRow >= rowsNumber || startColumn < 0 || startColumn >= columnsNumber)
			throw new IllegalArgumentException();
		
		if(this.isInitialised())
			throw new IllegalStateException();

		this.setupMines(startRow, startColumn);
		this.setupNumbers();
		this.initialised = true;
	}

	private void setupMines(int startRow, int startColumn) {
		for(int i = 0; i < this.minesNumber; i++) {
			int row, column;
			do {
				row = (int)(Math.random() * this.rowsNumber);
				column =(int)(Math.random() * this.columnsNumber);
			}
			while(this.grid[row][column] != null || (row == startRow && column == startColumn));

			this.grid[row][column] = Sign.MINE;
			this.mines[2 * i] = row;
			this.mines[2 * i + 1] = column;
		}
	}

	private void setupNumbers() {
		Sign[] values = Sign.values();
		for(int i = 0; i < this.rowsNumber; i++) {
			for(int j = 0; j < this.columnsNumber; j++) {
				if(this.grid[i][j] != Sign.MINE)
					this.grid[i][j] = values[this.getMinesNumberAroundCell(i, j)];
			}
		}
	}

	private int getMinesNumberAroundCell(int row, int column) {
		int minesNumber = 0;
		for(int i = row - 1; i <= row + 1; i++) {
			for(int j = column - 1; j <= column + 1; j++) {
				if(i >= 0 && i < this.rowsNumber && j >= 0 && j < this.columnsNumber) {
					if(this.grid[i][j] == Sign.MINE)
						minesNumber++;
				}
			}
		}
		return minesNumber;
	}
}