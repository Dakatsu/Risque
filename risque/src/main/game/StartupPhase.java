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
	
	@Override
	public void assignTerritories() {
		// Print out an error message if we cannot start the game.
		if (d_engine.getMap() == null || !d_engine.getMap().validateMap()) {
			d_engine.broadcastMessage("Error: a valid map must be loaded.");
		}
		else if (d_engine.getNumPlayers() < 2) {
			d_engine.broadcastMessage("Error: cannot start a game with just one player.");
		}
		else if (d_engine.getMap().getNumTerritories() < d_engine.getNumPlayers()) {
			d_engine.broadcastMessage("Error: there must be at least one territory per player.");
		}
		// Otherwise, divvy up the territories between the players.
		// TODO: Make this actually random and have some balance calculations.
		for (int l_idx = 1; l_idx <= d_engine.getMap().getNumTerritories(); l_idx++) {
			d_engine.getPlayerByID(l_idx % d_engine.getNumPlayers()).addOwnedTerritory(d_engine.getMap().getTerritory(l_idx));
		}
		d_engine.broadcastMessage("Territories have been assigned and the game has started. Good luck!");
		d_engine.setPhase(new IssueOrderPhase(this.d_engine));
	}
}
