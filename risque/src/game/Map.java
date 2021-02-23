package game;

import java.util.LinkedList;

/**
 * Represents a map file. It may be valid and playable, or in the midst of being created.
 * @author Kyle
 */
public class Map {
	
	/**
	 * A pair of territory IDs that constitute a link.
	 * Note that the 
	 * @author Kyle
	 *
	 */
	private class Border {
		/**
		 * The two IDs that signify the linked territories.
		 */
		int[] d_tIDs;
		
		/** 
		 * Creates a new border between two territories.
		 * @param p_firstID The first territory's ID.
		 * @param p_secondID The second territory's ID.
		 */
		public Border(int p_firstID, int p_secondID) {
			d_tIDs = new int[2];
			if (p_secondID < p_firstID) {
				d_tIDs[0] = p_secondID;
				d_tIDs[1] = p_firstID;
			}
			else {
				d_tIDs[0] = p_firstID;
				d_tIDs[1] = p_secondID;
			}
		}
		
		/**
		 * Basic override of equals to compare two borders for equality.
		 * Returns true so long as the indices are the same; their order does not matter.
		 */
		@Override
		public boolean equals(Object p_border) {
			if (p_border == this) {
				return true;
			}
			if (!(p_border instanceof Border)) {
				return false;
			}
			Border l_border = (Border)p_border;
			return (d_tIDs[0] == l_border.d_tIDs[0] && d_tIDs[1] == l_border.d_tIDs[1]) || (d_tIDs[0] == l_border.d_tIDs[1] && d_tIDs[1] == l_border.d_tIDs[0]);
		}
	}
	
	/**
	 * The list of territories in this map.
	 * Their ID is equivalent to their index in this list + 1.
	 */
	LinkedList<Territory> d_territories;
	
	/** 
	 * The list of continents in this map.
	 * Their ID is equivalent to their index in this list + 1.
	 */
	LinkedList<Continent> d_continents;
	
	/**
	 * The list of borders that connect two territories.
	 */
	LinkedList<Border> d_borders;
	
	/**
	 * Default constructor that creates an empty map.
	 */
	public Map() {
		d_territories = new LinkedList<>();
		d_continents = new LinkedList<>();
		d_borders = new LinkedList<>();
	}
	
	/**
	 * Checks the map for correctness (i.e. is a connected graph).
	 * @return whether the map is valid.
	 */
	public boolean validateMap() {
		// TODO: Validate the territories consist of a connected graph.
		// TODO: Validate each continent is itself a connected graph.
		// TODO: Validate that every territory belongs to a continent.
		return false;
	}
}
