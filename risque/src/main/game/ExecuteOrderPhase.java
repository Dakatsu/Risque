package main.game;

/**
 * Phase that represents the game executing the issued orders.
 * @author Kyle
 *
 */
public class ExecuteOrderPhase extends Phase {

	/**
	 * Auto-generated constructor for this phase.
	 * @param p_engine The game engine context.
	 */
	public ExecuteOrderPhase(GameEngine p_engine) {
		super(p_engine);
	}

	/**
	 * Execute all orders for this phase, and then 
	 */
	@Override
	public void onPhaseStart(Phase p_prevPhase) {
		d_engine.broadcastMessage("Executing orders.");
		boolean l_areThereUnexecutedOrders = true;
		while (l_areThereUnexecutedOrders) {
			l_areThereUnexecutedOrders = false;
			for (Player l_player : d_engine.getPlayers()) {
				if (l_player.hasOrdersLeftToExecute()) {
					l_player.nextOrder().execute();
					if (l_player.hasOrdersLeftToExecute()) {
						l_areThereUnexecutedOrders = true;
					}
				}
			}
		}
		// Check for a winner; end the game if someone won, otherwise go back to issuing orders.
		Player l_winner = checkForWinner();
		if (l_winner == null) {
			// Loop back to the issue orders phase.
			d_engine.setPhase(new IssueOrderPhase(d_engine));
		}
		else {
			d_engine.broadcastMessage(l_winner.getName().toUpperCase() + " HAS CONQUERED THE WORLD!\nReturning to startup phase.");
			d_engine.setPhase(new StartupPhase(d_engine));
		}
	}
	
	/**
	 * Checks if a player has one, and returns them if so.
	 * @return The winning player, or null if nobody's won yet.
	 */
	public Player checkForWinner() {
		Player l_winner = null;
		for (Player l_player : d_engine.getPlayers()) {
			if (l_player.getNumTerritoriesOwned() > 0) {
				if (l_winner == null) {
					l_winner = l_player;
				}
				else {
					return null;
				}
			}
		}
		return l_winner;
	}
}
