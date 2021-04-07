package main.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Separate thread class to allow us to input data to the console while also receiving data.
 * @author Kyle
 *
 */
public class InputHandler extends Thread {
	/**
	 * The console class that manages this InputHandler.
	 */
	private Console d_owner;
	
	/**
	 * The list of methods retrieved from the controller.
	 */
	private Method[] d_controllerMethods;
	
	/**
	 * A map of different names for methods. The key is the input command name, and the value is the name of the method to invoke.
	 */
	private HashMap<String, String> d_methodAliases;
	
	/**
	 * Inits the InputHandler with an owner.
	 * @param p_owner the new Console owner
	 */
	public InputHandler(Console p_owner) {
		setOwner(p_owner);
		d_methodAliases = new HashMap<>();
		// TODO: transfer this to the main console class?
		if (getOwner() != null && getOwner().getController() != null) {
			Class l_controllerClass = d_owner.getController().getClass();
			// Add default method aliases.
			if (l_controllerClass != null) {
				d_controllerMethods = l_controllerClass.getDeclaredMethods();
				d_methodAliases.put("assigncountries", "assignterritories");
				d_methodAliases.put("finish", "finishorders");
				d_methodAliases.put("editcountry", "editterritory");
				d_methodAliases.put("editneighbor", "editneighbour");
			}
		}
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

					// Deal with aliased names before proceeding.
					String l_convertedInput = d_methodAliases.containsKey(l_splitInput[0]) ? d_methodAliases.get(l_splitInput[0]) : l_splitInput[0];
					
					switch(l_convertedInput.toLowerCase()) {
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
											Integer.parseInt(l_splitInput[l_idx + 1]);
											Integer.parseInt(l_splitInput[l_idx + 2]);
											l_idx += 3;
										}
										else if (l_splitInput[l_idx].equalsIgnoreCase("-remove")) {
											Integer.parseInt(l_splitInput[l_idx + 1]);
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
											getOwner().getController().addContinent(Integer.parseInt(l_splitInput[l_idx + 1]), Integer.parseInt(l_splitInput[l_idx + 2]));
											l_idx += 3;
										}
										else if (l_splitInput[l_idx].equalsIgnoreCase("-remove")) {
											getOwner().getController().removeContinent(Integer.parseInt(l_splitInput[l_idx + 1]));
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
											Integer.parseInt(l_splitInput[l_idx + 1]);
											Integer.parseInt(l_splitInput[l_idx + 2]);
											l_idx += 3;
										}
										else if (l_splitInput[l_idx].equalsIgnoreCase("-remove")) {
											Integer.parseInt(l_splitInput[l_idx + 1]);
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
											getOwner().getController().addTerritory(Integer.parseInt(l_splitInput[l_idx + 1]), Integer.parseInt(l_splitInput[l_idx + 2]));
											l_idx += 3;
										}
										else if (l_splitInput[l_idx].equalsIgnoreCase("-remove")) {
											getOwner().getController().removeTerritory(Integer.parseInt(l_splitInput[l_idx + 1]));
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
										if (l_splitInput[l_idx].equalsIgnoreCase("-add") || l_splitInput[l_idx].equalsIgnoreCase("-remove")) {
											Integer.parseInt(l_splitInput[l_idx + 1]);
											Integer.parseInt(l_splitInput[l_idx + 2]);
										}
										else {
											l_areParametersInvalid = true;
											break;
										}
									}
									
									// Actually execute the actions.
									for (int l_idx = 1; l_idx < l_splitInput.length; l_idx += 3) {
										if (l_splitInput[l_idx].equalsIgnoreCase("-add")) {
											getOwner().getController().addNeighbours(Integer.parseInt(l_splitInput[l_idx + 1]), Integer.parseInt(l_splitInput[l_idx + 2]));
										}
										else if (l_splitInput[l_idx].equalsIgnoreCase("-remove")) {
											getOwner().getController().removeNeighbours(Integer.parseInt(l_splitInput[l_idx + 1]), Integer.parseInt(l_splitInput[l_idx + 2]));
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
										getOwner().getController().addPlayer(l_splitInput[l_idx + 1]);
									}
									else if (l_splitInput[l_idx].equalsIgnoreCase("-remove")) {
										getOwner().getController().removePlayer(l_splitInput[l_idx + 1]);
									}
									else {
										l_areParametersInvalid = true;
										break;
									}
								}
							}
							break;
						
						default:
							// Attempt to find and call the method via reflection. Currently handles string and integer arguments.
							boolean l_wasMethodFound = false;
							for (Method l_method : d_controllerMethods) {
								// If we found the method, go ahead and attempt to invoke it.
								if (l_method.getName().equalsIgnoreCase(l_convertedInput)) {
									l_wasMethodFound = true;
									int l_numInputParams = l_splitInput.length - 1;
									int l_numMethodParams = l_method.getParameterCount();
									// Only continue if the number of parameters match.
									if (l_numInputParams == l_numMethodParams) {
										try {
											// Attempt to convert the arguments to the right types.
											LinkedList<Object> l_parameters = new LinkedList<>();
											Class[] l_paramTypes = l_method.getParameterTypes();
											for (int l_idx = 0; l_idx < l_paramTypes.length; l_idx++) {
												if (l_paramTypes[l_idx].equals(String.class)) {
													l_parameters.add(l_splitInput[l_idx + 1]);
												}
												if (l_paramTypes[l_idx].equals(int.class)) {
													l_parameters.add(Integer.parseInt(l_splitInput[l_idx + 1]));
												}
											}
											// Only invoke if we have the right amount of arguments.
											if (l_parameters.size() == l_numMethodParams) {
												l_method.invoke(getOwner().getController(), l_parameters.toArray());
											}
											else {
												getOwner().onAddMessage("Command \"" + l_splitInput[0] + "\" cannot currently be used via the method reflection system. Please implement it manually.");
											}
										} 
										catch (IllegalArgumentException l_exception) {
											l_areParametersInvalid = true;
										}
										catch (InvocationTargetException l_exception) {
											getOwner().onAddMessage("Terminating program due to InvocationTargetException: " + l_exception.getMessage());
										}
										catch (IllegalAccessException l_exception) {
											getOwner().onAddMessage("Terminating program due to IllegalAccessException: " + l_exception.getMessage());
										} 
									}
									else {
										l_areParametersInvalid = true;
									}
									// We found the method, so no need to check any others (even if we failed to execute it).
									break;
								}
							}
							// If the method was not found, go ahead and note that it was not recognized.
							if (!l_wasMethodFound) {
								getOwner().onAddMessage("Command \"" + l_splitInput[0] + "\" not recognized.");
							}
					}
					
					if (l_areParametersInvalid) {
						getOwner().onAddMessage("Invalid parameters for command \"" + l_splitInput[0] + "\".");
					}
				}

			}
		} 
        catch (IOException l_exception) {
			// Just print error and go continue to the exit portion if we encounter an IO error.
			getOwner().onAddMessage("Terminating program due to IOException: " + l_exception.getMessage());
		}
	}
}
