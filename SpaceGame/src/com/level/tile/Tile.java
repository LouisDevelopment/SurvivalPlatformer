package com.level.tile;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.entities.item.Item;
import com.gfx.SpriteRenderer;
import com.level.tile.special.Furnace;
import com.main.Game;
import com.resources.ResourceManager;
import com.resources.Texture;

public class Tile {
	
	public Texture sprite;
	public Vector2f size;
	public Vector3f color;
	public boolean collision;
	public int height, width, id;

	public int width1 = 1, height1 = 1;
	protected int breakTime;
	protected int defaultSize = 16;
	private int colourMask;
	
	public Item drop;

	public static List<Tile> tiles = new ArrayList<Tile>();
	
	public static Tile dirt = new Tile(new Vector2f(Game.tileSize, Game.tileSize), ResourceManager.getTexture("dirt"), new Vector3f((110.0f/255.0f),(65.0f/255.0f),(49.0f/255.0f)), 3, 30, true, Item.dirt);
	public static Tile grass = new Tile(new Vector2f(Game.tileSize, Game.tileSize), ResourceManager.getTexture("grass"), new Vector3f((53.0f/255.0f),(168.0f/255.0f),(38.0f/255.0f)), 1, 30, true, Item.dirt);
	public static Tile longGrass = new Tile(new Vector2f(Game.tileSize, Game.tileSize), ResourceManager.getTexture("longGrass"), new Vector3f((53.0f/255.0f),(168.0f/255.0f),(38.0f/255.0f)), 101, 1, false, Item.seeds);
	public static Tile sapling = new Tile(new Vector2f(Game.tileSize, Game.tileSize), ResourceManager.getTexture("sapling"), new Vector3f((110.0f/255.0f),(65.0f/255.0f),(49.0f/255.0f)), 106, 1, false, Item.stick);
	public static Tile rock = new Tile(new Vector2f(Game.tileSize, Game.tileSize), ResourceManager.getTexture("rock"), new Vector3f(0.4f,0.4f,0.4f), 103, 1, false, Item.rock);
	public static Tile stone = new Tile(new Vector2f(Game.tileSize, Game.tileSize), ResourceManager.getTexture("stone"), new Vector3f(0.4f,0.4f,0.4f), 4, 50, true, Item.stone);
	public static Tile sand = new Tile(new Vector2f(Game.tileSize, Game.tileSize), ResourceManager.getTexture("sand"), new Vector3f((251.0f/255.0f),(210.0f/255.0f),(52.0f/255.0f)), 5, 30, true, Item.sand);
	public static Tile stoneBack = new Tile(new Vector2f(Game.tileSize, Game.tileSize), ResourceManager.getTexture("stoneBack"), new Vector3f(1,1,1), -4, 30, true, Item.voidItem);
	public static Tile dirtBack = new Tile(new Vector2f(Game.tileSize, Game.tileSize), ResourceManager.getTexture("dirtBack"), new Vector3f(1,1,1), -3, 30, true, Item.voidItem);
	
	public static Tile torch = new Tile(new Vector2f(Game.tileSize, Game.tileSize), ResourceManager.getTexture("torch"), new Vector3f((251.0f/255.0f),(210.0f/255.0f),(52.0f/255.0f)), 71, 30, false, Item.torch);

	public static Tile logGrass = new Tile(new Vector2f(Game.tileSize, Game.tileSize), ResourceManager.getTexture("grass"), new Vector3f((53.0f/255.0f),(168.0f/255.0f),(38.0f/255.0f)), 39, 30, true, Item.dirt);
	public static Tile log = new Tile(new Vector2f(Game.tileSize, Game.tileSize), ResourceManager.getTexture("log"), new Vector3f((110.0f/255.0f),(65.0f/255.0f),(49.0f/255.0f)), 40, 50, false, Item.log);
	public static Tile leaves = new Tile(new Vector2f(Game.tileSize, Game.tileSize), ResourceManager.getTexture("leaves"), new Vector3f((53.0f/255.0f),(168.0f/255.0f),(38.0f/255.0f)), 42, 50, false, Item.pinecone);
	public static Tile logWithLeaves = new Tile(new Vector2f(Game.tileSize, Game.tileSize), ResourceManager.getTexture("logWithLeaves"), new Vector3f((110.0f/255.0f),(65.0f/255.0f),(49.0f/255.0f)), 41, 20, false, Item.log);
	
	public static Tile ironOre = new Tile(new Vector2f(Game.tileSize, Game.tileSize), ResourceManager.getTexture("ironOre"), new Vector3f(0.4f,0.4f,0.4f), 31, 70, true, Item.ironOre);
	public static Tile coalOre = new Tile(new Vector2f(Game.tileSize, Game.tileSize), ResourceManager.getTexture("coalOre"), new Vector3f(0.05f,0.05f,0.05f), 32, 50, true, Item.coal);
	public static Tile uraniumOre = new Tile(new Vector2f(Game.tileSize, Game.tileSize), ResourceManager.getTexture("uraniumOre"), new Vector3f(0.4f,0.4f,0.4f), 33, 90, true, Item.uraniumOre);
	
	public static Tile furnace = new Furnace(new Vector2f(Game.tileSize*3, Game.tileSize*3), new Vector2f(3, 3), ResourceManager.getTexture("furnace"), new Vector3f(0.4f,0.4f,0.4f), 121, 70, false, Item.furnace);
	public static Tile spike = new MultiTile(new Vector2f(Game.tileSize*1, Game.tileSize*3), new Vector2f(1, 3), ResourceManager.getTexture("furnace"), new Vector3f(0.4f,0.4f,0.4f), 81, 70, false, Item.rock);
	
	
	public static Tile voidTile = new Tile(0);
	
	public Tile(Vector2f size, Texture sprite, Vector3f color, int id, int breakTime, boolean collision, Item drop) {
		this.color = color;
		this.sprite = sprite;
		this.drop = drop;
		this.size = size;
		width = (int) size.x;
		height = (int) size.y;
		
		this.id = id;
		this.breakTime = breakTime;
		this.collision = collision;
		tiles.add(this);
	}
	
	public Tile(int id) {
		collision = false;
		this.id = id;
		breakTime = -1;
		sprite = ResourceManager.getTexture("dirt");
		size = new Vector2f(defaultSize,defaultSize);
		color = new Vector3f(0,0,0);
		this.width = defaultSize;
		this.height = defaultSize;
		tiles.add(this);
	}
	
	public int getSize() {
		return width;
	}
	public int getBreakTime() {
		return breakTime;
	}

	public void render(int x, int y, SpriteRenderer r, float lightLevel, int layer) {
		if(layer == 1) {
			boolean left = false, right = false, up = false, down = false;
			int leftID = Game.level.getTile(x-1, y, 1).id;
			int rightID = Game.level.getTile(x+1, y, 1).id;
			int topID = Game.level.getTile(x, y-1, 1).id;
			int bottomID = Game.level.getTile(x, y+1, 1).id;
			if(leftID < 1 || leftID > 100) left = true;
			if(rightID < 1 || rightID > 100) right = true;
			if(topID < 1 || topID > 100) up = true;
			if(bottomID < 1 || bottomID > 100) down = true;
			
			if(up && down && right && left){
				render(x, y, r, lightLevel, ResourceManager.getTexture("ltrb"));
			} else if(left&&up&&down) {
				render(x, y, r, lightLevel, ResourceManager.getTexture("blt"));
			} else if(left && up && right){
				render(x, y, r, lightLevel, ResourceManager.getTexture("ltr"));
			} else if(left && down && right){
				render(x, y, r, lightLevel, ResourceManager.getTexture("lbr"));
			} else if(up && down && right){
				render(x, y, r, lightLevel, ResourceManager.getTexture("trb"));
			} else if(left&&up) {
				render(x, y, r, lightLevel, ResourceManager.getTexture("tl"));
			} else if(right&&up) {
				render(x, y, r, lightLevel, ResourceManager.getTexture("tr"));
			} else if(left&&down) {
				render(x, y, r, lightLevel, ResourceManager.getTexture("bl"));
			} else if(right&&down) {
				render(x, y, r, lightLevel, ResourceManager.getTexture("br"));
			} else if(left) {
				render(x, y, r, lightLevel, ResourceManager.getTexture("l"));
			} else if(right) {
				render(x, y, r, lightLevel, ResourceManager.getTexture("r"));
			} else if(up) {
				render(x, y, r, lightLevel, ResourceManager.getTexture("t"));
			} else if(down) {
				render(x, y, r, lightLevel, ResourceManager.getTexture("b"));
			} else {
				render(x, y, r, lightLevel, ResourceManager.getTexture("center"));
			}
		} else {
			boolean left = false, right = false, up = false, down = false;
			int leftID = Game.level.getTile(x-1, y, -1).id;
			int rightID = Game.level.getTile(x+1, y, -1).id;
			int topID = Game.level.getTile(x, y-1, -1).id;
			int bottomID = Game.level.getTile(x, y+1, -1).id;
			if(leftID == 0 || leftID > 100) left = true;
			if(rightID == 0 || rightID > 100) right = true;
			if(topID == 0 || topID > 100) up = true;
			if(bottomID == 0 || bottomID > 100) down = true;
			
			if(up && down && right && left){
				render(x, y, r, lightLevel, ResourceManager.getTexture("ltrb"));
			} else if(left&&up&&down) {
				render(x, y, r, lightLevel, ResourceManager.getTexture("blt"));
			} else if(left && up && right){
				render(x, y, r, lightLevel, ResourceManager.getTexture("ltr"));
			} else if(left && down && right){
				render(x, y, r, lightLevel, ResourceManager.getTexture("lbr"));
			} else if(up && down && right){
				render(x, y, r, lightLevel, ResourceManager.getTexture("trb"));
			} else if(left&&up) {
				render(x, y, r, lightLevel, ResourceManager.getTexture("tl"));
			} else if(right&&up) {
				render(x, y, r, lightLevel, ResourceManager.getTexture("tr"));
			} else if(left&&down) {
				render(x, y, r, lightLevel, ResourceManager.getTexture("bl"));
			} else if(right&&down) {
				render(x, y, r, lightLevel, ResourceManager.getTexture("br"));
			} else if(left) {
				render(x, y, r, lightLevel, ResourceManager.getTexture("l"));
			} else if(right) {
				render(x, y, r, lightLevel, ResourceManager.getTexture("r"));
			} else if(up) {
				render(x, y, r, lightLevel, ResourceManager.getTexture("t"));
			} else if(down) {
				render(x, y, r, lightLevel, ResourceManager.getTexture("b"));
			} else {
				render(x, y, r, lightLevel, ResourceManager.getTexture("center"));
			}
		}
	}
	
	public void render(int x, int y, SpriteRenderer renderer, float lightLevel, Texture mask) {
		if(id != 0) {
			if(id > 100) {
				renderer.render(sprite, new Vector2f(x*Game.tileSize, y*Game.tileSize + 2), size, 0, new Vector3f(lightLevel/8.0f, lightLevel/8.0f, lightLevel/8.0f), true, mask);
			} else
				renderer.render(sprite, new Vector2f(x*Game.tileSize, y*Game.tileSize), size, 0, new Vector3f(lightLevel/8.0f, lightLevel/8.0f, lightLevel/8.0f), true, mask);
		}
	}
	
	public static Tile getTileFromID(int id) {
		for(int i = 0; i < tiles.size(); i++) {
			if(tiles.get(i).id == id) {
				return tiles.get(i);
			} else if(tiles.get(i).id == -id) {
				if(Tile.getTileFromID(-id) instanceof MultiTile) {
					return tiles.get(i);
				}	
			}
		}
		return voidTile;
	}
	
	public int getID() {
		return id;
	}
	
}
