package console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Separate thread class to allow us to input data to the console while also receiving data.
 * @author Kyle
 *
 */
public class InputHandler extends Thread {
	private Console d_owner;
	
	/**
	 * Inits the InputHandler with an owner.
	 * @param p_owner the new Console owner
	 */
	public InputHandler(Console p_owner) {
		setOwner(p_owner);
	}
	
	/**
	 * Gets the owning Console for this.
	 * @return the owning Console
	 */
	public Console getOwner() {
		return d_owner;
	}
	
	/**
	 * Sets the owning Console for this.
	 * @param p_owner the new owner.
	 */
	public void setOwner(Console p_owner) {
		d_owner = p_owner;
	}
	
	/**
	 * Entry point for the InputHandler. The object is deleted once this method finishes.
	 */
	public void run() {
        try {
        	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
		
			// Just keep looping and reading input for now until we use the quit command.
			// So far we have just one command: quit
			String input = "";
			while (!input.trim().equalsIgnoreCase("quit")) {
				input = reader.readLine();
				
				if (input=="editcontinent"){
			 //TODO}
				
				if (input=="editcountry"){
			 //TODO}

                                if (input=="editneighbor"){
			 //TODO}

                                if (input=="showmap"){
			 //TODO}

				if (input=="savemap"){
			 //TODO}

				if (input=="editmap"){
			 //TODO}

 				if (input=="validatemap"){
			 //TODO}
				
				if (input=="loadmap"){
			 //TODO}

				if (input=="gameplayer"){
			 //TODO}

				if (input=="assigncountries"){
			 //TODO}

				if (input=="deploy"){
			 //TODO}

			}
		} catch (IOException e) {
			// Just print error and go continue to the exit portion if we encounter an error.
			System.err.println("Terminating program due to IOException: " + e.getMessage());
		}
        // Do the quit command if we entered quit.
		if (getOwner() != null) {
			getOwner().execQuit();
		}
	}
}
