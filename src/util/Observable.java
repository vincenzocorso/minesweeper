package minesweeper.util;

public interface Observable {
	public abstract void addObserver(Observer observer);
	public abstract void deleteObserver(Observer observer);
	public abstract void deleteObservers();
	public abstract int getObserversNumber();
	public abstract void notifyObservers();
	public abstract void notifyObservers(Object... args);
}