package main.console;

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
		
			// keep looping and reading input and converting them to function calls.
			// So far we have just one command: quit
			String input = "";
			while (!input.trim().equalsIgnoreCase("quit")) {
				input = reader.readLine();
				
			//calling corresponding functions from console class according to the input 
				String[] inputarr = input.split(" ",2);

				if (inputarr[0]=="editcontinent") {
					getOwner().execEditcontinent();
				}
				
				if (inputarr[0]=="editcountry") {
			 		getOwner().execEditcountry();
				}

                                if (inputarr[0]=="editneighbor") {
					getOwner().execEditneighbor();
				}

                                if (inputarr[0]=="showmap") {
			 		getOwner().execShowmap();
				}

				if (inputarr[0]=="savemap") {
			 		getOwner().execSavemap(inputarr[1]);
				}

				if (inputarr[0]=="editmap") {
					getOwner().execEditmap(inputarr[1]);
				}

 				if (inputarr[0]=="validatemap") {
			 		getOwner().execValidatemap();
				}
				
				if (inputarr[0]=="loadmap") {
			 		getOwner().execLoadmap(inputarr[1]);
				}

				if (inputarr[0]=="gameplayer") {
			 		getOwner().execGameplayer();
				}

				if (inputarr[0]=="assigncountries") {
			 		getOwner().execAssigncountries();
				}

				if (inputarr[0]=="deploy") {
			 		getOwner().execDeploy();
				}

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
