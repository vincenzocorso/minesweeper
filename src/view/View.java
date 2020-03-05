package minesweeper.view;

import java.awt.*;
import javax.swing.*;

public final class View {
	private final JFrame frame = new JFrame();
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu gameMenu = new JMenu("Game");
	private final JMenu settingsMenu = new JMenu("Settings");
	private final JMenuItem newGameMenuItem = new JMenuItem("New game");
	private final JMenuItem creditsMenuItem = new JMenuItem("Credits");
	private final JMenuItem exitMenuItem = new JMenuItem("Exit");
	private final JCheckBoxMenuItem soundMenuItem = new JCheckBoxMenuItem("Sounds");
	private final JCheckBoxMenuItem timerMenuItem = new JCheckBoxMenuItem("Timer");
	private final JMenu difficultyMenu = new JMenu("Difficulty");
	private final JMenuItem beginnerMenuItem = new JMenuItem("Beginner");
	private final JMenuItem itermediateMenuItem = new JMenuItem("Intermediate");
	private final JMenuItem expertMenuItem = new JMenuItem("Expert");
	private final JPanel mainPanel = new JPanel();
	private final JPanel gridPanel = new JPanel();
	private CellButton[][] cellButtons;
	private final JPanel statusBarPanel = new JPanel();
	private final JLabel minesStatusBarLabel = new JLabel();
	private final JLabel timerStatusBarLabel = new JLabel();
	private final JLabel difficultyStatusBarLabel = new JLabel(); // TODO: rimuovere stringa. Lasciare la gestione al controller.

	public View(String frameTitle) {
		this.setupFrame(frameTitle);
		this.setupContentPane();
		this.setupMenuBar();
		this.setupMainPanel();
		this.setupStatusBar();
	}

	private void setupFrame(String frameTitle) {
		this.frame.setTitle(frameTitle);
		this.frame.setSize(800, 800);
		this.frame.setResizable(false);
		this.frame.setLocationRelativeTo(null);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
	}

	private void setupContentPane() {
		Container contentPane = this.frame.getContentPane();
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(this.mainPanel, BorderLayout.CENTER);
		contentPane.add(this.statusBarPanel, BorderLayout.SOUTH);
	}

	private void setupMenuBar() {
		this.frame.setJMenuBar(this.menuBar);
		this.menuBar.add(this.gameMenu);
		this.menuBar.add(this.settingsMenu);

		this.gameMenu.add(this.newGameMenuItem);
		this.gameMenu.add(this.creditsMenuItem);
		this.gameMenu.add(this.exitMenuItem);

		this.settingsMenu.add(this.soundMenuItem);
		this.settingsMenu.add(this.timerMenuItem);
		this.settingsMenu.add(this.difficultyMenu);

		this.difficultyMenu.add(this.beginnerMenuItem);
		this.difficultyMenu.add(this.itermediateMenuItem);
		this.difficultyMenu.add(this.expertMenuItem);
	}

	private void setupMainPanel() {
		this.mainPanel.setLayout(new BorderLayout(20, 20));
		this.mainPanel.add(this.gridPanel);
	}

	public void setupGrid(int rowsNumber, int columnsNumber) {
		this.gridPanel.removeAll();
		this.cellButtons = new CellButton[rowsNumber][columnsNumber];
		this.gridPanel.setLayout(new GridLayout(rowsNumber, columnsNumber, 0, 0));

		for(int i = 0; i < rowsNumber; i++) {
			for(int j = 0; j < columnsNumber; j++) {
				this.cellButtons[i][j] = new CellButton();
				this.gridPanel.add(this.cellButtons[i][j]);
			}
		}

		this.gridPanel.revalidate();
		this.gridPanel.repaint();
		this.frame.pack();
	}

	private void setupStatusBar() {
		this.statusBarPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
		this.statusBarPanel.add(minesStatusBarLabel);
		this.statusBarPanel.add(timerStatusBarLabel);
		this.statusBarPanel.add(difficultyStatusBarLabel);
	}

	public void showWinDialog() {
		JOptionPane.showMessageDialog(this.frame, "You Win!");
	}

	public void showLoseDialog() {
		JOptionPane.showMessageDialog(this.frame, "You Lose!");
	}

	public void showCreditsDialog() {
		JOptionPane.showMessageDialog(this.frame, "Minesweeper by Vincenzo Corso.\nCheck also github.com/vincenzocorso");
	}

	public JMenuItem getNewGameMenuItem() {
		return this.newGameMenuItem;
	}

	public JMenuItem getCreditsMenuItem() {
		return this.creditsMenuItem;
	}

	public JMenuItem getExitMenuItem() {
		return this.exitMenuItem;
	}

	public JCheckBoxMenuItem getSoundCheckBox() {
		return this.soundMenuItem;
	}

	public JCheckBoxMenuItem getTimerCheckBox() {
		return this.timerMenuItem;
	}

	public JMenuItem getBeginnerMenuItem() {
		return this.beginnerMenuItem;
	}

	public JMenuItem getIntermediateMenuItem() {
		return this.itermediateMenuItem;
	}

	public JMenuItem getExpertMenuItem() {
		return this.expertMenuItem;
	}

	public CellButton[][] getGridButtons() {
		return this.cellButtons;
	}

	public JLabel getMinesNumberLabel() {
		return this.minesStatusBarLabel;
	}

	public JLabel getTimerLabel() {
		return this.timerStatusBarLabel;
	}

	public JLabel getDifficultyLabel() {
		return this.difficultyStatusBarLabel;
	}
}