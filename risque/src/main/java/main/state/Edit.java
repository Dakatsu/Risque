package main.state;

public abstract class Edit extends Phase{

	Edit(GameEngine p_ge) {
		super(p_ge);
		// TODO Auto-generated constructor stub
	}
	public void setPlayers() {
		printInvalidCommandMessage(); 
	}

	public void assignCountries() {
		printInvalidCommandMessage(); 
	}

	public void reinforce() {
		printInvalidCommandMessage(); 
	}

	public void attack() {
		printInvalidCommandMessage(); 
	}

	public void fortify() {
		printInvalidCommandMessage(); 
	}

	public void endGame() {
		printInvalidCommandMessage(); 
	}
	
	public void showMap() {
		printInvalidCommandMessage(); 
	}
}
