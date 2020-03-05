package minesweeper.model;

public class IllegalMoveException extends RuntimeException
{
	public IllegalMoveException() {}

	public IllegalMoveException(String msg) {
		super(msg);
	}
}