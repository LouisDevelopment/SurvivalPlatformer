package com.entities.item;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.gfx.SpriteRenderer;
import com.main.Game;
import com.resources.ResourceManager;
import com.resources.Texture;

public class Item{

	public String name;
	public Texture sprite;
	public int tile;
	public boolean placable = true;
	
	public static Item grass = new Item(ResourceManager.loadTexture("items/grass.png", true, "grassItem"), "Grass", 1);
	public static Item stone = new Item(ResourceManager.loadTexture("items/stone.png", true, "stoneItem"), "Stone", 4);
	public static Item sand = new Item(ResourceManager.getTexture("sand"), "Sand", 5);
	public static Item dirt = new Item(ResourceManager.loadTexture("items/dirt.png", true, "dirtItem"), "Dirt", 3);
	//public static Item snowyGrass = new Item(ResourceManager.loadTexture("items/grass.png", true, "grassItem"), "Wet Grass", 2);
	public static Item seeds = new Item(ResourceManager.loadTexture("items/seeds.png", true, "seeds"), "Seeds", 101);
	public static Item twine = new Item(ResourceManager.loadTexture("items/twine.png", true, "twine"), "Twine", -1);
	public static Item twineRope = new Item(ResourceManager.loadTexture("items/twineRope.png", true, "twineRope"), "Rope", -1);
	public static Item rock = new Item(ResourceManager.loadTexture("items/rock.png", true, "rockItem"), "Rock", 103);
	public static Item stick = new Item(ResourceManager.loadTexture("items/stick.png", true, "stickItem"), "Stick", 106);
	public static Item primitiveHatchet = new Item(ResourceManager.loadTexture("items/primitiveHatchet.png", true, "primativeHatchet"), "Primative Hatchet", -1);
	public static Item primitiveSpear = new Item(ResourceManager.loadTexture("items/primitiveSpear.png", true, "primativeSpear"), "Primative Spear", -1);
	public static Item furnace = new Item(ResourceManager.loadTexture("items/furnace.png", true, "furnaceItem"), "Furnace", 121);
	public static Item planks = new Item(ResourceManager.loadTexture("items/planks.png", true, "planksItem"), "Planks", 41);

	public static Item log = new Item(ResourceManager.loadTexture("items/log.png", true, "logItem"), "Log", 40);
	public static Item pinecone = new Item(ResourceManager.loadTexture("items/acorn.png", true, "grassItem"), "Pinecone", -1);
	
	public static Item ironOre = new Item(ResourceManager.loadTexture("items/ironOre.png", true, "ironOreItem"), "Iron Ore", -1);
	public static Item coal = new Item(ResourceManager.loadTexture("items/coal.png", true, "coalItem"), "Coal", -1);
	public static Item uraniumOre = new Item(ResourceManager.loadTexture("items/uraniumOre.png", true, "uraniumOreItem"), "Uranium Ore", -1);

	public static Item torch = new Item(ResourceManager.loadTexture("items/torch.png", true, "torchItem"), "Torch", 71);
	
	public static Item voidItem = new Item(-1);
	
	public Item(Texture sprite, String name, int tile) {
		this.name = name;
		this.tile = tile;
		this.sprite = sprite;
	}
	
	public Item(int tile) {
		this.sprite = ResourceManager.getTexture("void");
		this.name = "";
		this.tile = tile;
	}

	public void render(int x, int y, SpriteRenderer r) {
		r.render(sprite, new Vector2f(x, y), new Vector2f(8, 8), 0, new Vector3f(1,1,1), false);
	}
	
	public void renderInWorld(int x, int y, SpriteRenderer r) {
		r.render(sprite, new Vector2f(x, y), new Vector2f(8, 8), 0, new Vector3f(1,1,1), true);
	}
	
	public int getTile() {
		return tile;
	}
	
}
