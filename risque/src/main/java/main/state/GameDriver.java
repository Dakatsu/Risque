package main.state;

public class GameDriver {
	public static void main(String args[]) {
		GameEngine gameEngine = new GameEngine();
		gameEngine.startNew();
		gameEngine.start();
	}
}
