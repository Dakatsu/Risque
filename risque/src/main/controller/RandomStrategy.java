package main.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import main.game.Map;
import main.game.Territory;
import main.game.Continent;
import main.game.Player;

	/**
	 * this is a random player strategy
	 */
public class RandomStrategy implements PlayerStrategy{
	
	Random random = new Random();
	
	/**
	 * This is the method for deploy
	 * 
	 * @param p_map Map Object
	 * @param p_listPlayer List of Players
	 * @param p_player Current Player
	 */
	public void deploy(Map p_map, LinkedList<Player> p_listPlayer, Player p_player) {
		
		
	}
	
	/**
	 * Random deploy command
	 * 
	 * @param p_player Player Object
	 * @param p_highestDeployedArmies  Highest numbers of armies to deploy
	 * @return Random deploy command
	 */
	private String getRandomDeployCommand(Player p_player, int p_highestDeployedArmies) {
		String l_input = "Random deploy";
		
		return l_input;
	}
	
	/**
	 * Attack method
	 * 
	 * @param p_map Map Object
	 * @param p_listPlayer List of Players
	 * @param p_player Current Player
	 * @return 0 is the game is not over
	 */
	public int attack(Map p_map, LinkedList<Player> p_listPlayer, Player p_player) {
		
		
		return 0;
	}
	
	
	/**
	 * Random attack command
	 * 
	 * @param p_player Player Object
	 * @param p_map Map Object
	 * @param p_territory The territory that would be attacked
	 * @return Random attack command
	 */
	private String getRandomAttackCommand(Player p_player, Map p_map, Territory p_territory) {
		String l_input = "Random attack";
		
		return l_input;
	}
	
	/**
	 * Method for reinforcement phase
	 * 
	 * @param p_map Map Object
	 * @param p_listPlayer List of Players
	 * @param p_player Current Player
	 */
	public void reinforcement(Map p_map, LinkedList<Player> p_listPlayer, Player p_player) {
		
		
		
	}
	
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

}
