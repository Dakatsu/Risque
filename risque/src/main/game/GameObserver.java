package main.game;

/**
 * An interface that defines classes that "observe" the game engine,
 * and that the game engine should inform of updates or changes.
 * @author Kyle
 *
 */
public interface GameObserver {
	/**
	 * Called when the game engine wants to add a message for this observer.
	 * @param p_message The message to add.
	 */
	void onAddMessage(String p_message);

	/**
	 * Called by the game engine when the game wishes to shut down.
	 */
	void onQuit();
}
