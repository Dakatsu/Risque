package main.game;

/**
 * An order to deploy armies to a given territory.
 * @author Kyle
 *
 */
public class DeployOrder extends Order {
	
	/**
	 * The territory to deploy to.
	 */
	Territory d_territory;
	
	/**
	 * The number of armies to deploy.
	 */
	int d_numArmiesToDeploy;
	
	/**
	 * Constructs the deploy order.
	 * @param p_territory The territory to deploy to.
	 * @param p_numArmies The number of armies to deploy.
	 */
	public DeployOrder(Territory p_territory, int p_numArmies) {
		d_territory = p_territory;
		setNumArmies(p_numArmies);
	}
	
	/**
	 * Executes the deploy order.
	 * @return Whether the order was executed successfully.
	 */
	public boolean execute() {
		// Ensure we own the territory we are deploying to (in case it was somehow conquered before this can be executed).
		if (d_engine.getTerritoryOwner(d_territory) == getIssuer()) {
			d_territory.setNumArmies(d_territory.getNumArmies() + d_numArmiesToDeploy);
			d_engine.broadcastMessage(getIssuer().getName() +  " deployed " + d_numArmiesToDeploy + " to " + d_territory.getDisplayName() + ", for a total of " + d_territory.getNumArmies() + ".");
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the number of armies to deploy.
	 * @return The number of armies.
	 */
	public int getNumArmies() {
		return d_numArmiesToDeploy;
	}
	
	/**
	 * Sets the number of armies to deploy to a new value above zero.
	 * @param p_numArmies The number of armies to deploy.
	 * @return True if the number was successfully changed, otherwise false.
	 */
	public boolean setNumArmies(int p_numArmies) {
		if (p_numArmies > 0) {
			d_numArmiesToDeploy = p_numArmies;
			return true;
		}
		return false;
	}
}
