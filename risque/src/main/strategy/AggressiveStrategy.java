package main.strategy;

import java.util.LinkedList;
import java.util.Scanner;

import main.game.Map;
import main.game.Territory;
import main.game.Continent;
import main.game.Player;

/**
 * Class for Aggressive Strategy
 * @author Protim
 *
 */
public class AggressiveStrategy implements PlayerStrategy{
	
	/**
	 * Territory object stores strongest territory
	 */
	public Territory strongest;
	
	/**
	 * The number of deployed armies would be calculated in this method
	 * 
	 * @param p_map Map object
	 * @param p_player Player object
	 * @return Number of deployed armies
	 */
	
	public int calculateDeployedArmies(Map p_map, Player p_player) {
		//int l_deplyedArmies //the initialization and implementation is required
		
		return 0; // this would be return l_deplyedArmies;
	};
	
	/**
	 * The strongest Territory of a player should contain highest number
	 * of armies.
	 * 
	 * @param p_map Map Object
	 * @param p_listPlayer List of Players
	 * @param p_player Current Player
	 * 
	 */
	public void strongestTerritory(Map p_map, LinkedList<Player> p_listPlayer, Player p_player) {
		
		
		
	}
	
	/**
	 * This method is to know if it is workable an attack on a Territory
	 * 
	 * @param p_map Map Object
	 * @param p_listPlayer List of Players
	 * @param p_player Current Player
	 * @param p_territory Current Territory
	 * @return 1 if success otherwise 0
	 */
	public int workableAttack(Map p_map, LinkedList<Player> p_listPlayer, Player p_player, Territory p_territory) {
		
		
		return 0; 
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
	 * This method is for attack
	 * 
	 * @param p_map Map Object
	 * @param p_listPlayer List of Players
	 * @param p_player Current Player
	 * @return 1 if the game is over otherwise 0
	 * 
	 */
	public int attack(Map p_map, LinkedList<Player> p_listPlayer, Player p_player) {
		
		
		return 0;
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

}
