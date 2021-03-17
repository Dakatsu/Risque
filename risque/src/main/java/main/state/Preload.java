package main.state;

import main.game.Map;

public class Preload extends Edit{
	
	protected Map map;

	Preload(GameEngine p_ge) {
		super(p_ge);
		// TODO Auto-generated constructor stub
	}
	
	public void loadMap() {
		this.ge.map.loadFromFile("myMap.map");
		System.out.println("map has been loaded");
		ge.setPhase(new PostLoad(ge));
	}

	public void editCountry() {
		printInvalidCommandMessage(); 
	}

	public void saveMap() {
		printInvalidCommandMessage(); 
	}

	public void next() {
		System.out.println("must load map");
	}

}
