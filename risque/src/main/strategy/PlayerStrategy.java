package main.strategy;

import java.util.LinkedList;

import main.game.Map;
import main.game.Territory;
import main.game.Continent;
import main.game.Player;

/**
* This interface is used to implement PlayerStrategy
*
*/

public interface PlayerStrategy {
	
	void deploy(Map p_map, LinkedList<Player> p_listPlayer, Player p_player);
	
	int attack(Map p_map, LinkedList<Player> p_listPlayer, Player p_player);
	
	void reinforcement(Map p_map, LinkedList<Player> p_listPlayer, Player p_player);
	
	int calculateDeployedArmies(Map p_map, Player p_player);
}
