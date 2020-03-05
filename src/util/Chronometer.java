package minesweeper.util;

public class Chronometer {
	private long startTime;

	public Chronometer() {
		startTime = System.currentTimeMillis();
	}

	public long getElapsedTimeInSeconds() {
		return (System.currentTimeMillis() - startTime) / 1000;
	}
}