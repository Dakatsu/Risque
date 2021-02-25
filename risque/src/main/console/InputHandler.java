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
        	// So long as "quit" is not typed in.
			String l_input = "";
			while (!l_input.equalsIgnoreCase("quit")) {
				l_input = reader.readLine().trim();
				
				// calling corresponding functions from console class according to the input 
				String[] l_splitInput = l_input.split(" ",2);
				
				if (l_splitInput.length > 0) {
					
					boolean l_areParametersInvalid = false;
					
					switch(l_splitInput[0].toLowerCase()) {
					
						case("quit"):
							getOwner().execQuit();
							return;
					
						case("loadmap"):
							if (l_splitInput.length != 2) {
								l_areParametersInvalid = true;
							}
							else {
						 		getOwner().execLoadMap(l_splitInput[1].trim());
							}
							break;
							
						case("savemap"):
							if (l_splitInput.length != 2) {
								l_areParametersInvalid = true;
							}
							else {
								getOwner().execSaveMap(l_splitInput[1].trim());
							}
							break;
							
						case("showmap"):
							if (l_splitInput.length != 1) {
								l_areParametersInvalid = true;
							}
							else {
								getOwner().execShowMap();
							}
							break;
						
						default:
							getOwner().addMessage("Command " + l_splitInput[0] + " not recognized.");
					}
					
					if (l_areParametersInvalid) {
						getOwner().addMessage("Invalid parameters for command " + l_splitInput[0] + ".");
					}
					
					// TODO: move validated commands to switch statement above.
					if (l_splitInput[0]=="editcontinent") {
						String[] editcon= l_splitInput[1].trim().split("-");
						for (int i= 1; i<editcon.length; i++) {   
							getOwner().execEditcontinent(editcon[i].trim());
						}
					}
					if (l_splitInput[0]=="editcountry") {
						String[] editcoun= l_splitInput[1].trim().split("-");
						for (int i= 1; i<editcoun.length; i++) {
				 			getOwner().execEditcountry(editcoun[i].trim());
						}
					}
	
					if (l_splitInput[0]=="editneighbor") {
						String[] editnei= l_splitInput[1].trim().split("-");
						for (int i= 1; i<editnei.length; i++) {
							getOwner().execEditneighbor(editnei[i].trim());
						}
					}
	
					if (l_splitInput[0]=="editmap") {
						getOwner().execEditMap(l_splitInput[1].trim());
					}
	
	 				if (l_splitInput[0]=="validatemap") {
				 		getOwner().execValidatemap();
					}
	
					if (l_splitInput[0]=="gameplayer") {
				 		getOwner().execGameplayer();
					}
	
					if (l_splitInput[0]=="assigncountries") {
				 		getOwner().execAssigncountries();
					}
	
					if (l_splitInput[0]=="deploy") {
				 		getOwner().execDeploy();
					}
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
