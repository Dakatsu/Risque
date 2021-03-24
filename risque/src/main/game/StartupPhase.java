package main.game;

/**
 * Represents the start of the game, before gameplay has begun.
 *
 */
public class StartupPhase extends Phase {

	/**
	 * Auto-generated constructor stub.
	 * @param p_engine The game engine context.
	 */
	StartupPhase(GameEngine p_engine) {
		super(p_engine);
	}
	
	/**
	 * Prints the map file to the screen.
	 */
	@Override
	public void showMap() {
		if (d_engine.getMap() != null) {
			d_engine.getConsole().addMessage(d_engine.getMap().toText());
		}
	}
}
