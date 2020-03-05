package minesweeper.model;

public class Difficulty {
	public static final Difficulty BEGINNER = new Difficulty("Beginner", 9, 9, 10);
	public static final Difficulty INTERMEDIATE = new Difficulty("Intermediate", 16, 16, 40);
	public static final Difficulty EXPERT = new Difficulty("Expert", 16, 30, 99);

	private String name;
	private int rowsNumber;
	private int columnsNumber;
	private int minesNumber;

	public Difficulty(String name, int rowsNumber, int columnsNumber, int minesNumber) {
		this.name = name;
		this.setRowsNumber(rowsNumber);
		this.setColumnsNumber(columnsNumber);
		this.setMinesNumber(minesNumber);
	}

	public String getName() {
		return this.name;
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

	private void setRowsNumber(int rowsNumber)
	throws IllegalArgumentException {
		if(rowsNumber < 9 || rowsNumber > 24)
			throw new IllegalArgumentException();
		
		this.rowsNumber = rowsNumber;
	}

	private void setColumnsNumber(int columnsNumber)
	throws IllegalArgumentException {
		if(columnsNumber < 9 || columnsNumber > 30)
			throw new IllegalArgumentException();
		
		this.columnsNumber = columnsNumber;
	}

	private void setMinesNumber(int minesNumber)
	throws IllegalArgumentException {
		if(minesNumber < 10 || minesNumber > 667)
			throw new IllegalArgumentException();
		
		this.minesNumber = minesNumber;
	}
}