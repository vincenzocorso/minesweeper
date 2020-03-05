package minesweeper.view;

import java.awt.*;
import javax.swing.*;

public class CellButton extends JButton {
	private static final Dimension defaultCellSize = new Dimension(30, 30);
	private static final Font defaultCellFont = new Font("Arial", Font.PLAIN, 20);
	private static final ImageIcon scaledMineImageIcon = new ImageIcon(new ImageIcon("img/mine.png").getImage().getScaledInstance((int)defaultCellSize.getWidth() - 10, (int)defaultCellSize.getHeight() - 10, Image.SCALE_SMOOTH));
	private static final ImageIcon scaledFlagImageIcon = new ImageIcon(new ImageIcon("img/flag.png").getImage().getScaledInstance((int)defaultCellSize.getWidth() - 10, (int)defaultCellSize.getHeight() - 10, Image.SCALE_SMOOTH));
	private static final Color coveredBackgroundColor = new Color(232, 232, 232);

	public CellButton() {
		super();
		this.setPreferredSize(defaultCellSize);
		this.setupDefaultStyle();
		this.setupCellAsCovered();
	}

	private void setupDefaultStyle() {
		this.setOpaque(true);
		this.setBorderPainted(true);
		this.setFocusPainted(false);
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
	}

	public void setupCellAsCovered() {
		this.setBackground(coveredBackgroundColor);
		this.setIcon(null);
	}

	public void setupCellAsFlag() {
		this.setIcon(scaledFlagImageIcon);
	}

	public void setupCellAsNumber(int number) {
		Color[] numberColors = new Color[] {
			Color.BLACK, Color.BLUE, Color.GREEN, Color.RED,
			new Color(11, 0, 94), new Color(128, 75, 0), Color.CYAN,
			Color.MAGENTA, Color.GRAY
		};

		this.setBackground(Color.LIGHT_GRAY);
		if(number != 0) {
			this.setText("" + number);
			this.setFont(defaultCellFont);
			this.setForeground(numberColors[number]);
		}
	}

	public void setupCellAsMine() {
		this.setIcon(scaledMineImageIcon);
	}
}