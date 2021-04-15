package main.controller;

import java.util.LinkedList;
import java.util.Scanner;

import main.game.Map;
import main.game.Territory;
import main.game.Continent;
import main.game.Player;
/**
 * Class for Benevolent Strategy
 * @author Protim
 *
 */
public class BenevolentStrategy implements PlayerStrategy{
	
	
	/**
	 * Territory object stores weakest territory
	 */
	public Territory weakest;
	
	/**
	 * The number of deployed armies would be calculated in this method
	 * 
	 * @param p_map Map object
	 * @param p_player Player object
	 * @return Number of deployed armies
	 */
	
	public int calculateDeployedArmies(Map p_map, Player p_player) {
		//int l_deployedArmies //the initialization and implementation is required
		
		return 0; // this would be return l_deployedArmies;
	};
	
	/**
	 * The weakest Territory of a player should contain lowest number
	 * of armies.
	 * 
	 * @param p_map Map Object
	 * @param p_listPlayer List of Players
	 * @param p_player Current Player
	 * 
	 */
	
	public void weakestTerritory(Map p_map, LinkedList<Player> p_listPlayer, Player p_player) {
		
		
		
	}
	
	/**
	 * This method is for deploy on player's weakest Territory
	 * 
	 * @param p_map Map Object
	 * @param p_listPlayer List of Players
	 * @param p_player Current Player
	 */
	
	public void deploy(Map p_map, LinkedList<Player> p_listPlayer, Player p_player) {
		
		
		
	}
	
	
	/**
	 * This method is for reinforcement phase
	 * 
	 * @param p_map Map Object
	 * @param p_listPlayer List of Players
	 * @param p_player Current Player
	 */
	
	public void reinforcement(Map p_map, LinkedList<Player> p_listPlayer, Player p_player) {
		
		
		
	}

	@Override
	public int attack(Map p_map, LinkedList<Player> p_listPlayer, Player p_player) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
}
