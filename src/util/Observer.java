package minesweeper.util;

public interface Observer {
	public abstract void update(Observable observed);
	public abstract void update(Observable observed, Object... args);
}