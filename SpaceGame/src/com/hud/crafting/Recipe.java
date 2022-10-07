package com.hud.crafting;

import com.entities.item.Item;

public class Recipe {
	
	private static Item[] primativeSpearItems = {Item.rock, Item.rock, Item.twineRope};
	public static Recipe primativeSpear = new Recipe(primativeSpearItems, Item.primitiveSpear);
	
	private static Item[] twineRopeItems = {Item.twine, Item.twine, Item.twine};
	public static Recipe twineRope = new Recipe(twineRopeItems, Item.twineRope);

	private static Item[] torchItems = {Item.log, Item.coal};
	public static Recipe torch = new Recipe(torchItems, Item.torch);
	
	private static Item[] furnaceItems = {Item.rock, Item.rock};
	public static Recipe furnace = new Recipe(furnaceItems, Item.furnace);
	
	private static Item[] primativeHatchetItems = {Item.rock, Item.rock};
	public static Recipe primativeHatchet = new Recipe(primativeHatchetItems, Item.primitiveHatchet);
	
	public Item[] items;
	public Item result;
	
	public Recipe(Item[] items, Item result) {
		this.items = items;
		this.result = result;
	}
	
}
