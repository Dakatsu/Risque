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
			d_engine.broadcastMessage(d_engine.getMap().toText());
		}
	}
	
	@Override
	public void addContinent(int p_cID, int p_cValue) {
		if (d_engine.getMap() == null) {
			d_engine.broadcastMessage("That command cannot be used without a loaded map.");
		}
		else if (d_engine.getMap().createContinent(p_cID, p_cValue)) {
			d_engine.broadcastMessage("New continent created. The number of continents is now " + d_engine.getMap().getNumContinents());
		}
		else {
			d_engine.broadcastMessage("The continent could not be created.");
		}
	}
	
	@Override
	public void removeContinent(int p_cID) {
		if (d_engine.getMap() == null) {
			d_engine.broadcastMessage("That command cannot be used without a loaded map.");
		}
		else if (d_engine.getMap().deleteContinent(p_cID)) {
			d_engine.broadcastMessage("Continent removed. The number of continents is now " + d_engine.getMap().getNumContinents());
		}
		else {
			d_engine.broadcastMessage("No continents were removed. Please ensure a valid ID was entered.");
		}
	}
	
	@Override
	public void addTerritory(int p_tID, int p_cID) {
		if (d_engine.getMap() == null) {
			d_engine.broadcastMessage("That command cannot be used without a loaded map.");
		}
		else if (d_engine.getMap().createTerritory(p_tID, p_cID)) {
			d_engine.broadcastMessage("New territory created. The number of territories is now " + d_engine.getMap().getNumTerritories());
		}
		else {
			d_engine.broadcastMessage("Territory was not added. Please check your parameters.");
		}
	}
	
	@Override
	public void removeTerritory(int p_tID) {
		if (d_engine.getMap() == null) {
			d_engine.broadcastMessage("That command cannot be used without a loaded map.");
		}
		else if (d_engine.getMap().deleteTerritory(p_tID)) {
			d_engine.broadcastMessage("The territory was removed.");
		}
		else {
			d_engine.broadcastMessage("Territory was not added. Please check the input ID.");
		}
	}
	
	@Override
	public void addNeighbours(int p_firstID, int p_secondID) {
		if (d_engine.getMap() == null) {
			d_engine.broadcastMessage("That command cannot be used without a loaded map.");
		}
		else if (d_engine.getMap().addBorder(p_firstID, p_secondID)) {
			d_engine.broadcastMessage("Neighbours successfuly linked.");
		}
		else {
			d_engine.broadcastMessage("Invalid IDs or the territories were already neighbours.");
		}
	}
	
	@Override
	public void removeNeighbours(int p_firstID, int p_secondID) {
		if (d_engine.getMap() == null) {
			d_engine.broadcastMessage("That command cannot be used without a loaded map.");
		}
		else if (d_engine.getMap().deleteBorder(p_firstID, p_secondID)) {
			d_engine.broadcastMessage("Territories are no longer neighbours.");
		}
		else {
			d_engine.broadcastMessage("Invalid IDs or the territories were not neighbours.");
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
