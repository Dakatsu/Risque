package main.controller;

import java.util.*;

import main.game.Map;
import main.game.Territory;
import main.game.Continent;
import main.game.Player;
	/**
	 * This is cheater type player strategy
	 */
public class CheaterStrategy extends PlayerStrategy {
	
	/**
	 * Default constructor.
	 * @param p_player The player for this strategy.
	 */
	public CheaterStrategy(Player p_player) {
		super(p_player);
	}
	
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
	 * Method for reinforcement phase
	 * 
	 * @param p_map Map Object
	 * @param p_listPlayer List of Players
	 * @param p_player Current Player
	 */
	public void reinforcement(Map p_map, LinkedList<Player> p_listPlayer, Player p_player) {
		
		
		
	}

	@Override
	public int calculateDeployedArmies(Map p_map, Player p_player) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * Called by the Player class when the game engine notifies it of the player's turn starting.
	 */
	public void onNotifyTurn() {
		// TODO: Logic.
	}
}
