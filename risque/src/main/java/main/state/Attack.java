package main.state;


public class Attack extends MainPlay implements Order {

	Territory target_territory;
	int to_deploy;
	Player initiator;
	
	Attack(GameEngine p_ge) {
		super(p_ge);
	}
	
	public Attack(Player initiator, Territory target_territory, int to_deploy) {
		super(null);
		this.target_territory = target_territory;
		this.initiator = initiator;
		this.to_deploy = to_deploy;
		
		
	}
	
//	Attack(Player initiator, Territory target_territory, int to_deploy){
//		
//	}

	/**
	 *  Call this method to go the the next state in the sequence. 
	 */
	
	public void execute() {
		if (valid())
			// add the armies to the target territory
			this.target_territory.numArmies += to_deploy;
	}

	public boolean valid() {
		if (target_territory.owner.equals(initiator))
			// the target territory must belong to the player that created the order
			return true;
		else {
			System.out.println("invalid order");
			return false;
		}
	}

	public void printOrder() {
		System.out.println("Deploy order issued by player " + this.initiator.name);
		System.out.println("Deploy " + this.to_deploy + " to " + this.target_territory.name);
	}
	
	public void next() {
		ge.setPhase(new Fortify(ge));
	}
	
	public void reinforce() {
		printInvalidCommandMessage(); 
	}

	public void attack() {
		System.out.println("attack done");
		ge.setPhase(new Fortify(ge));
	}

	public void fortify() {
		printInvalidCommandMessage(); 
	}

//	@Override
//	public void execute() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean valid() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void printOrder() {
//		// TODO Auto-generated method stub
//		
//	}

}