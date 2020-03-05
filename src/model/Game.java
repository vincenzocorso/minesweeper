package minesweeper.model;

import minesweeper.util.*;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Iterator;

public class Game implements Observable {
	private Grid grid;
	private boolean[][] cellClicked;
	private boolean[][] flagPlaced;
	private Settings settings;
	private boolean gameFinished;
	private boolean gameLost;
	private int flagsNumber;

	private ArrayList<Observer> observers = new ArrayList<>();

	public Game() {
		this.settings = new Settings();
		this.newGame();
	}

	public void newGame() {
		Difficulty difficulty = this.settings.getDifficulty();
		this.grid = new Grid(difficulty.getRowsNumber(), difficulty.getColumnsNumber(), difficulty.getMinesNumber());
		this.cellClicked = new boolean[this.grid.getRowsNumber()][this.grid.getColumnsNumber()];
		this.flagPlaced = new boolean[this.grid.getRowsNumber()][this.grid.getColumnsNumber()];
		this.gameFinished = false;
		this.gameLost = false;
		this.flagsNumber = 0;
	}

	// TODO: refactor
	public void revealCell(int row, int column)
	throws IllegalArgumentException, IllegalMoveException, GameFinishedException {
		if(row < 0 || row >= this.grid.getRowsNumber() || column < 0 || column >= this.grid.getColumnsNumber())
			throw new IllegalArgumentException();
		
		if(this.gameFinished)
			throw new GameFinishedException();

		if(this.isCellClicked(row, column))
			throw new IllegalMoveException();

		if(!this.grid.isInitialised())
			this.grid.init(row, column);

		Sign sign = this.grid.getCell(row, column);

		if(sign == Sign.MINE) {
			this.revealAllMines();
			this.gameFinished = true;
			this.gameLost = true;
		}
		else {
			if(sign == Sign.ZERO)
				this.revealEmptyCellAround(row, column);
			else
				this.setCellClicked(row, column);

			if(this.checkWin()) {
				this.revealAllMines();
				this.gameFinished = true;
			}
		}
	}

	private void setCellClicked(int row, int column) {
		if(!this.isCellClicked(row, column)) {
			if(this.isFlagPlaced(row, column))
				this.toggleFlag(row, column);

			this.cellClicked[row][column] = true;
			this.notifyObservers(row, column, this.grid.getCell(row, column));
		}
	}

	private void revealAllMines() {
		int[] mines = this.grid.getMines();
		for(int i = 0; i < mines.length; i += 2)
			this.setCellClicked(mines[i], mines[i+1]);
	}

	// TODO: refactor
	private void revealEmptyCellAround(int startRow, int startColumn) {
		int rowsNumber = this.grid.getRowsNumber();
		int columnsNumber = this.grid.getColumnsNumber();
		HashSet<ArrayList<Integer>> toClick = new HashSet<>();
		toClick.add(new ArrayList<Integer>(Arrays.asList(startRow, startColumn)));
		while(!toClick.isEmpty()) {
			Iterator<ArrayList<Integer>> iterator = toClick.iterator();
			ArrayList<Integer> cellCoordinates = iterator.next();
			int row = cellCoordinates.get(0);
			int column = cellCoordinates.get(1);
			iterator.remove();
			this.setCellClicked(row, column);
			if(this.grid.getCell(row, column) == Sign.ZERO) {
				for(int i = row - 1; i <= row + 1; i++) {
					for(int j = column - 1; j <= column + 1; j++) {
						if(i >= 0 && i < rowsNumber && j >= 0 && j < columnsNumber) {
							if(!this.isCellClicked(i, j))
								toClick.add(new ArrayList<Integer>(Arrays.asList(i, j)));
						}
					}
				}
			}
		}
	}

	public void toggleFlag(int row, int column)
	throws IllegalMoveException, GameFinishedException {
		if(this.isCellClicked(row, column))
			throw new IllegalMoveException();

		if(this.gameFinished)
			throw new GameFinishedException();
		
		this.flagPlaced[row][column] = !this.flagPlaced[row][column];
		this.flagsNumber = (this.flagPlaced[row][column]) ? this.flagsNumber + 1 : this.flagsNumber - 1;
		this.notifyObservers(row, column);
	}

	public boolean isFlagPlaced(int row, int column) {
		return this.flagPlaced[row][column];
	}

	public boolean isCellClicked(int row, int column) {
		return this.cellClicked[row][column];
	}

	public boolean hasPlayerLost() {
		if(this.gameLost)
			return true;
		else
			return false;
	}

	public boolean hasPlayerWon() {
		if(this.gameFinished && !this.gameLost)
			return true;
		else
			return false;
	}

	public Settings getSettings() {
		return this.settings;
	}

	public int getGridRowsNumber() {
		return this.grid.getRowsNumber();
	}

	public int getGridColumnsNumber() {
		return this.grid.getColumnsNumber();
	}

	public int getGridMinesNumber() {
		return this.grid.getMinesNumber();
	}

	public int getGridFlagsNumber() {
		return this.flagsNumber;
	}

	private boolean checkWin() {
		int rows = this.grid.getRowsNumber();
		int columns = this.grid.getColumnsNumber();
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				if(!this.isCellClicked(i, j) && this.grid.getCell(i, j) != Sign.MINE)
					return false;
			}
		}
		return true;
	}

	@Override
	public void addObserver(Observer observer) {
		this.observers.add(observer);
	}

	@Override
	public void deleteObserver(Observer observer) {
		ListIterator<Observer> listIterator = this.observers.listIterator();
		while(listIterator.hasNext()) {
			Observer o = listIterator.next();
			if(o.equals(observer))
				listIterator.remove();
		}
	}

	@Override
	public void deleteObservers() {
		this.observers.clear();
	}

	@Override
	public int getObserversNumber() {
		return this.observers.size();
	}

	@Override
	public void notifyObservers() {
		for(Observer o : this.observers)
			o.update(this);
	}

	@Override
	public void notifyObservers(Object... args) {
		for(Observer o : this.observers)
			o.update(this, args);
	}
}