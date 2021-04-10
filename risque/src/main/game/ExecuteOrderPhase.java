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
		// Loop back to the issue orders phase.
		d_engine.setPhase(new IssueOrderPhase(d_engine));
	}
}
