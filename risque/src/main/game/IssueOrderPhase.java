package main.game;

public class IssueOrderPhase extends Phase {

	/**
	 * Auto-generated constructor stub.
	 * @param p_engine The game engine context.
	 */
	IssueOrderPhase(GameEngine p_engine) {
		super(p_engine);
	}

	/**
	 * Prints a player-friendly view of the map to the screen.
	 */
	@Override
	public void showMap() {
		if (d_engine.getMap() != null) {
			d_engine.getConsole().addMessage(d_engine.getMap().toText());
		}
		d_engine.getConsole().addMessage("Printed during issue order phase.");
	}
}
