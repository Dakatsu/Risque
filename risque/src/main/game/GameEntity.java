package main.game;

/**
 * The superclass of any entity created by the game engine and can therefore can access it.
 * @author Kyle
 *
 */
public class GameEntity {
	
	/**
	 * The game engine.
	 */
	GameEngine d_engine;
	
	/**
	 * Creates a new entity linked to a game engine.
	 * @param p_engine
	 */
	public GameEntity(GameEngine p_engine) {
		setEngine(p_engine);
	}
	
	/**
	 * Creates a new entity with no game engine.
	 */
	GameEntity() {
		this(null);
	}
	
	/**
	 * Gets the game engine.
	 * @return The game engine.
	 */
	public GameEngine getEngine() {
		return d_engine;
	}
	
	/**
	 * Sets the game engine to a new reference.
	 * @param p_engine The new game engine.
	 */
	public void setEngine(GameEngine p_engine) {
		d_engine = p_engine;
	}
}
