package main.game;

import java.util.LinkedList;
import java.util.Random;

/**
 * Phase that represents the game executing the issued orders.
 * @author Kyle
 *
 */
public class ExecuteOrderPhase extends Phase {
	
	private LinkedList<Player> d_playersToAwardCards;

	/**
	 * Auto-generated constructor for this phase.
	 * @param p_engine The game engine context.
	 */
	public ExecuteOrderPhase(GameEngine p_engine) {
		super(p_engine);
		d_playersToAwardCards = new LinkedList<>();
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
					int l_numTerritoriesBeforeOrder = l_player.getNumTerritoriesOwned();
					l_player.nextOrder().execute();
					if (l_player.hasOrdersLeftToExecute()) {
						l_areThereUnexecutedOrders = true;
					}
					// Check whether we should be awarded a card for gaining a territory.
					if (l_player.getNumTerritoriesOwned() > l_numTerritoriesBeforeOrder && !d_playersToAwardCards.contains(l_player)) {
						d_playersToAwardCards.add(l_player);
					}
				}
			}
		}
		// Check for a winner; end the game if someone won, otherwise go back to issuing orders.
		Player l_winner = checkForWinner();
		if (l_winner == null) {
			awardCardsToPlayers();
			// Loop back to the issue orders phase.
			d_engine.setPhase(new IssueOrderPhase(d_engine));
		}
		else {
			d_engine.broadcastMessage(l_winner.getName().toUpperCase() + " HAS CONQUERED THE WORLD!\nReturning to startup phase.");
			d_engine.setPhase(new StartupPhase(d_engine));
		}
	}
	
	/**
	 * Can an attack on a territory proceed?
	 * If not, broadcasts the reason why it cannot.
	 * @param p_territory The territory to attack.
	 * @param p_attacker The player attacking the territory.
	 * @return True if the attack can proceed.
	 */
	public boolean canAttackTerritory(Territory p_territory, Player p_attacker) {
		Player l_territoryOwner = d_engine.getTerritoryOwner(p_territory);
		if (p_attacker != null && l_territoryOwner != null && (p_attacker.isAllyWith(l_territoryOwner) || l_territoryOwner.isAllyWith(p_attacker))) {
			d_engine.broadcastMessage("An attack on " + p_territory.getName() + " (" + l_territoryOwner.getName() + ") by " + p_attacker.getName() + " cannot proceed due to a cease-fire.");
			return false;
		}
		return true;
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
	
	/**
	 * Give every player owed a card a random one.
	 */
	public void awardCardsToPlayers() {
		for (Player l_player : d_playersToAwardCards) {
			Random l_rand = new Random();
			LinkedList<String> l_cardOptions = d_engine.getCardOptions();
			String l_randCard = l_cardOptions.get(l_rand.nextInt(l_cardOptions.size() - 1));
			l_player.addCard(l_randCard);
			d_engine.broadcastMessage(l_player.getName() + " was awarded a " + l_randCard + " card.");
		}
	}
}
