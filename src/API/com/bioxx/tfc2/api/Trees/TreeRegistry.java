package com.bioxx.tfc2.api.Trees;

import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

public class TreeRegistry
{
	public static TreeRegistry instance = new TreeRegistry();
	private HashMap<String, TreeConfig> treeTypeHash = new HashMap<String, TreeConfig>();
	private Vector<TreeSchemManager> treeList;

	public TreeRegistry()
	{
		treeList = new Vector<TreeSchemManager>();
	}

	public void RegisterTree(TreeSchematic treeSchematic, String name)
	{
		int index = checkValidity(name);

		if(index < 0)
		{
			System.out.println("[TFC2] Registering Tree Type \"" + name + "\" Failed! There are no trees with that name in TFC.");
		}
		else
		{
			if(treeList.size() < treeTypeHash.size())
				treeList.setSize(treeTypeHash.size());

			if(treeList.get(index) == null)
				treeList.set(index, new TreeSchemManager(index));

			treeList.get(index).addSchem(treeSchematic);
		}
	}

	public TreeSchematic getRandomTreeSchematic(Random R)
	{
		return treeList.get(R.nextInt(treeList.size())).getRandomSchematic(R);
	}

	public TreeSchematic getRandomTreeSchematic(Random R, int treeID)
	{
		if(treeID > treeList.size() - 1) return null;
		return treeList.get(treeID).getRandomSchematic(R);
	}

	public TreeSchematic getRandomTreeSchematic(Random R, int treeID, int growthStage)
	{
		if(treeID > treeList.size() - 1) return null;
		return treeList.get(treeID).getRandomSchematic(R, growthStage);
	}

	public TreeSchematic getTreeSchematic(int treeID, int schemID, int growthStage)
	{
		if(treeID > treeList.size() - 1) return null;
		return treeList.get(treeID).getSchematic(schemID, growthStage);
	}

	public void addWoodType(TreeConfig configuration)
	{
		if(!treeTypeHash.containsKey(configuration.name))
		{
			treeTypeHash.put(configuration.name, configuration);
		}
	}

	public int checkValidity(String n)
	{
		int index = indexFromString(n);
		if(index >= 0 ) return index;
		return -1;
	}

	/**
	 * @param n Name of the Tree type. Used as the Key in the hash map for lookups.
	 * @return Tree index that is unique to that tree
	 */
	public int indexFromString(String n)
	{
		if(treeTypeHash.containsKey(n))
			return ((TreeConfig) treeTypeHash.get(n)).index;
		return -1;
	}

	/**
	 * @param n Name of the Tree type. Used as the Key in the hash map for lookups.
	 * @return Full TreeConfiguration file
	 */
	public TreeConfig treeFromString(String n)
	{
		if(treeTypeHash.containsKey(n))
			return ((TreeConfig) treeTypeHash.get(n));
		return null;
	}
	/**
	 * @param id The tree type ID.
	 * @return Full TreeConfiguration file
	 */
	public TreeConfig treeFromID(int id)
	{
		for(;treeTypeHash.values().iterator().hasNext();)
		{
			TreeConfig config = (TreeConfig)treeTypeHash.values().iterator().next();
			if(config.index == id)
				return config;
		}
		return null;
	}

	public TreeConfig getRandomTree()
	{
		int id = new Random().nextInt(treeTypeHash.values().toArray().length);
		return treeFromID(id);
	}
}
