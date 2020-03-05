import minesweeper.model.*;
import minesweeper.view.*;
import minesweeper.controller.*;
import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Game game = new Game();
				View view = new View("Minesweeper");
				Controller controller = new Controller(game, view);
			}
		});
	}
}