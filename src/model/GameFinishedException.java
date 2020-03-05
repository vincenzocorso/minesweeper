package minesweeper.model;

public class GameFinishedException extends RuntimeException
{
	public GameFinishedException() {}

	public GameFinishedException(String msg) {
		super(msg);
	}
}