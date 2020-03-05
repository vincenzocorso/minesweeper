package minesweeper.model;

public class Settings {
	private Difficulty difficulty;
	private boolean soundEnabled;
	private boolean timerEnabled;

	public Settings() {
		this.difficulty = Difficulty.BEGINNER;
		this.soundEnabled = true;
		this.timerEnabled = true;
	}

	public boolean isSoundEnabled() {
		return this.soundEnabled;
	}

	public void setSoundEnabled(boolean state) {
		this.soundEnabled = state;
	}

	public boolean isTimerEnabled() {
		return this.timerEnabled;
	}

	public void setTimerEnabled(boolean state) {
		this.timerEnabled = state;
	}

	public Difficulty getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}
}