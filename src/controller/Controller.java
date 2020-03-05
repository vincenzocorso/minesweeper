package minesweeper.controller;

import minesweeper.model.*;
import minesweeper.view.*;
import minesweeper.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Controller {
	private final Game game;
	private final View view;

	private Timer timer;
	private Chronometer chronometer;

	public Controller(Game game, View view) {
		this.game = game;
		this.view = view;
		this.initController();
		this.initObservers();
	}

	private void initController() {
		this.setupMenu();
		this.initGame();
	}

	private void setupMenu() {
		this.view.getNewGameMenuItem().addActionListener(e -> this.initGame());
		this.view.getCreditsMenuItem().addActionListener(e -> this.view.showCreditsDialog());
		this.view.getExitMenuItem().addActionListener(e -> this.exitGame());
		this.view.getSoundCheckBox().setSelected(true);
		this.view.getTimerCheckBox().setSelected(true);
		this.view.getBeginnerMenuItem().addActionListener(e -> this.game.getSettings().setDifficulty(Difficulty.BEGINNER));
		this.view.getIntermediateMenuItem().addActionListener(e -> this.game.getSettings().setDifficulty(Difficulty.INTERMEDIATE));
		this.view.getExpertMenuItem().addActionListener(e -> this.game.getSettings().setDifficulty(Difficulty.EXPERT));
	}

	private void initGame() 
	{
		this.game.newGame();
		
		this.updateFlagsNumberOnStatusBar();

		this.stopUpdatingChronometer();
		if(this.view.getTimerCheckBox().isSelected())
			this.startUpdatingChronometer();
		else
			this.view.getTimerLabel().setText("");
		
		this.view.getDifficultyLabel().setText("Difficulty: " + this.game.getSettings().getDifficulty().getName());

		this.setupGrid();
	}

	private void initObservers() {
		this.game.deleteObservers();
		this.game.addObserver(new CellObserver(this.game, this.view));
	}

	private void exitGame() {
		this.stopUpdatingChronometer();
		System.exit(0);
	}

	private void updateFlagsNumberOnStatusBar() {
		String minesNumberText = this.game.getGridFlagsNumber() + "/" + this.game.getGridMinesNumber();
		this.view.getMinesNumberLabel().setText("Mines: " + minesNumberText);
	}

	private void startUpdatingChronometer() {
		this.view.getTimerLabel().setText("Timer: 00:00");
		this.chronometer = new Chronometer();
		this.timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				long time = chronometer.getElapsedTimeInSeconds();
				String stringTimer = String.format("Timer: %02d:%02d", time / 60, time % 60);
				view.getTimerLabel().setText(stringTimer);
			}
		});
		this.timer.start();
	}

	private void stopUpdatingChronometer() {
		if(this.timer != null)
			this.timer.stop();
		this.timer = null;
		this.chronometer = null;
	}

	private void setupGrid() {
		Difficulty difficulty = this.game.getSettings().getDifficulty();
		this.view.setupGrid(difficulty.getRowsNumber(), difficulty.getColumnsNumber());
		CellButton[][] buttons = this.view.getGridButtons();
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[0].length; j++) {
				buttons[i][j].addActionListener(new ButtonsActionListener(i, j));
				buttons[i][j].addMouseListener(new ButtonsMouseAdapter(i, j));
			}
		}
	}

	private void playErrorSound() {
		if(this.view.getSoundCheckBox().isSelected())
			Toolkit.getDefaultToolkit().beep();
	}

	class ButtonsActionListener implements ActionListener {
		private final int row;
		private final int column;

		public ButtonsActionListener(int row, int column) {
			this.row = row;
			this.column = column;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				game.revealCell(this.row, this.column);

				if(game.hasPlayerWon()) {
					stopUpdatingChronometer();
					view.showWinDialog();
				}
				else if(game.hasPlayerLost()) {
					stopUpdatingChronometer();
					view.showLoseDialog();
				}
			}
			catch(IllegalMoveException ex) {
				playErrorSound();
			}
			catch(GameFinishedException ex) {
				initGame();
			}
		}
	}

	class ButtonsMouseAdapter extends MouseAdapter {
		private final int row;
		private final int column;

		public ButtonsMouseAdapter(int row, int column) {
			this.row = row;
			this.column = column;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(SwingUtilities.isRightMouseButton(e)) {
				try {
					game.toggleFlag(this.row, this.column);
				}
				catch(RuntimeException ex) {
					playErrorSound();
				}
			}
		}
	}

	class CellObserver implements Observer {
		private final Game game;
		private final View view;

		public CellObserver(Game game, View view) {
			this.game = game;
			this.view = view;
		}

		@Override
		public void update(Observable observed) {}

		@Override
		public void update(Observable observed, Object... args) {
			int row = (Integer)args[0];
			int column = (Integer)args[1];

			if(game.isCellClicked(row, column))
				this.cellClicked(row, column, (Sign)args[2]);
			else if(game.isFlagPlaced(row, column))
				this.cellFlagged(row, column);
			else
				this.cellCovered(row, column);
		}

		private void cellClicked(int row, int column, Sign sign) {
			CellButton button = this.view.getGridButtons()[row][column];

			if(sign == Sign.MINE)
				button.setupCellAsMine();
			else
				button.setupCellAsNumber(sign.ordinal());
		}

		private void cellFlagged(int row, int column) {
			CellButton button = this.view.getGridButtons()[row][column];
			button.setupCellAsFlag();
			updateFlagsNumberOnStatusBar();
		}

		private void cellCovered(int row, int column) {
			CellButton button = this.view.getGridButtons()[row][column];
			button.setupCellAsCovered();
			updateFlagsNumberOnStatusBar();
		}
	}
}