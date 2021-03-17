package main.state;

public abstract class MainPlay extends Play {

	MainPlay(GameEngine p_ge) {
		super(p_ge);
	}

	public void loadMap() {
		this.printInvalidCommandMessage();
	}

	public void setPlayers() {
		this.printInvalidCommandMessage();	
	}

	public void assignCountries() {
		this.printInvalidCommandMessage();
	}
}