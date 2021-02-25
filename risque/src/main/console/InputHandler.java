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
				String[] l_splitInput = l_input.split(" ");
				
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
						 		getOwner().execLoadMap(l_splitInput[1]);
							}
							break;
							
						case("savemap"):
							if (l_splitInput.length != 2) {
								l_areParametersInvalid = true;
							}
							else {
								getOwner().execSaveMap(l_splitInput[1]);
							}
							break;
							
						// TODO: Make the map display different for map editing and playing!!!
						case("showmap"):
							if (l_splitInput.length != 1) {
								l_areParametersInvalid = true;
							}
							else {
								getOwner().execShowMap();
							}
							break;
							
						case("validatemap"):
							if (l_splitInput.length != 1) {
								l_areParametersInvalid = true;
							}
							else {
								getOwner().execValidateMap();
							}
							break;
							
						/**
						 * TODO: Most of these commands below use copy-pasted loops to check parameters due to lack of time.
						 * These need to be heavily refactored!
						 */
						// TODO: Valid parameters are invalid.
						case("editcontinent"):
							// Input must be at least three words.
							if (l_splitInput.length <= 2) {
								l_areParametersInvalid = true;
							}
							else {

								try {
									/**
									 * This loop checks that the parameters are valid.
									 * E.g. there are two words after -add and one after -delete.
									 * An exception will be thrown if the parameters are not integers.
									 * TODO: This is done very inefficiently. Refactor!
									 */
									for (int l_idx = 1; l_idx < l_splitInput.length; ) {
										if (l_splitInput[l_idx].equalsIgnoreCase("-add")) {
											Integer.parseInt(l_splitInput[l_idx] + 1);
											Integer.parseInt(l_splitInput[l_idx] + 2);
											l_idx += 3;
										}
										else if (l_splitInput[l_idx].equalsIgnoreCase("-remove")) {
											Integer.parseInt(l_splitInput[l_idx] + 1);
											l_idx += 2;
										}
										else {
											l_areParametersInvalid = true;
											break;
										}
									}
									
									// Actually execute the actions.
									for (int l_idx = 1; l_idx < l_splitInput.length; ) {
										if (l_splitInput[l_idx].equalsIgnoreCase("-add")) {
											getOwner().execAddContinent(Integer.parseInt(l_splitInput[l_idx + 1]), Integer.parseInt(l_splitInput[l_idx + 2]));
											l_idx += 3;
										}
										else if (l_splitInput[l_idx].equalsIgnoreCase("-remove")) {
											getOwner().execRemoveContinent(Integer.parseInt(l_splitInput[l_idx + 1]));
											l_idx += 2;
										}
									}
								}
								catch (NumberFormatException l_exception) {
									l_areParametersInvalid = true;
								}
								catch (ArrayIndexOutOfBoundsException l_exception) {
									l_areParametersInvalid = true;
								}
							}
							break;
							
						// TODO: Valid parameters are invalid.
						case("editterritory"):
						case("editcountry"):
							// Input must be at least three words.
							if (l_splitInput.length <= 2) {
								l_areParametersInvalid = true;
							}
							else {

								try {
									/**
									 * This loop checks that the parameters are valid.
									 * E.g. there are two words after -add and one after -delete.
									 * An exception will be thrown if the parameters are not integers.
									 * TODO: This is done very inefficiently. Refactor!
									 */
									for (int l_idx = 1; l_idx < l_splitInput.length; ) {
										if (l_splitInput[l_idx].equalsIgnoreCase("-add")) {
											Integer.parseInt(l_splitInput[l_idx] + 1);
											Integer.parseInt(l_splitInput[l_idx] + 2);
											l_idx += 3;
										}
										else if (l_splitInput[l_idx].equalsIgnoreCase("-remove")) {
											Integer.parseInt(l_splitInput[l_idx] + 1);
											l_idx += 2;
										}
										else {
											l_areParametersInvalid = true;
											break;
										}
									}
									
									// Actually execute the actions.
									for (int l_idx = 1; l_idx < l_splitInput.length; ) {
										if (l_splitInput[l_idx].equalsIgnoreCase("-add")) {
											getOwner().execAddTerritory(Integer.parseInt(l_splitInput[l_idx + 1]), Integer.parseInt(l_splitInput[l_idx + 2]));
											l_idx += 3;
										}
										else if (l_splitInput[l_idx].equalsIgnoreCase("-remove")) {
											getOwner().execRemoveTerritory(Integer.parseInt(l_splitInput[l_idx + 1]));
											l_idx += 2;
										}
									}
								}
								catch (NumberFormatException l_exception) {
									l_areParametersInvalid = true;
								}
								catch (ArrayIndexOutOfBoundsException l_exception) {
									l_areParametersInvalid = true;
								}
							}
							break;
							
						// TODO: Valid parameters are invalid.
						case("editneighbour"):
						case("editneighbor"):
							// Input must be at least three words.
							if (l_splitInput.length <= 2 || (l_splitInput.length - 1) % 3 != 0) {
								l_areParametersInvalid = true;
							}
							else {

								try {
									/**
									 * This loop checks that the parameters are valid.
									 * E.g. there are two words after -add or -delete.
									 * An exception will be thrown if the parameters are not integers.
									 * TODO: This is done very inefficiently. Refactor!
									 */
									for (int l_idx = 1; l_idx < l_splitInput.length; l_idx += 3) {
										if (l_splitInput[l_idx].equalsIgnoreCase("-add")) {
											Integer.parseInt(l_splitInput[l_idx] + 1);
											Integer.parseInt(l_splitInput[l_idx] + 2);
										}
										else {
											l_areParametersInvalid = true;
											break;
										}
									}
									
									// Actually execute the actions.
									for (int l_idx = 1; l_idx < l_splitInput.length; l_idx += 3) {
										if (l_splitInput[l_idx].equalsIgnoreCase("-add")) {
											getOwner().execAddNeighbour(Integer.parseInt(l_splitInput[l_idx + 1]), Integer.parseInt(l_splitInput[l_idx + 2]));
										}
										else if (l_splitInput[l_idx].equalsIgnoreCase("-remove")) {
											getOwner().execRemoveNeighbour(Integer.parseInt(l_splitInput[l_idx + 1]), Integer.parseInt(l_splitInput[l_idx + 2]));
										}
									}
								}
								catch (NumberFormatException l_exception) {
									l_areParametersInvalid = true;
								}
								catch (ArrayIndexOutOfBoundsException l_exception) {
									l_areParametersInvalid = true;
								}
							}
							break;
							
						case("gameplayer"):
							// Input must be at least three words, and it must be an odd number to be valid.
							if (l_splitInput.length <= 2 || (l_splitInput.length) % 2 != 1) {
								l_areParametersInvalid = true;
							}
							else {
								for (int l_idx = 1; l_idx < l_splitInput.length; l_idx += 2) {
									if (l_splitInput[l_idx].equalsIgnoreCase("-add")) {
										getOwner().execAddPlayer(l_splitInput[l_idx + 1]);
									}
									else if (l_splitInput[l_idx].equalsIgnoreCase("-remove")) {
										getOwner().execRemovePlayer(l_splitInput[l_idx + 1]);
									}
									else {
										l_areParametersInvalid = true;
										break;
									}
								}
							}
							break;
						
						case("assignterritories"):
						case("assigncountries"):
							if (l_splitInput.length != 1) {
								l_areParametersInvalid = true;
							}
							else {
								getOwner().execAssignTerritories();
							}
							break;
							
						case("deploy"):
							if (l_splitInput.length != 3) {
								l_areParametersInvalid = true;
							}
							else {
								try {
									getOwner().execDeploy(Integer.parseInt(l_splitInput[1]), Integer.parseInt(l_splitInput[2]));
								}
								catch (NumberFormatException l_exception) {
									l_areParametersInvalid = true;
								}
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
				}

			}
		} 
        catch (IOException e) {
			// Just print error and go continue to the exit portion if we encounter an error.
			System.err.println("Terminating program due to IOException: " + e.getMessage());
		}
	}
}
