package com.level.tile.special;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.entities.item.Item;
import com.gfx.SpriteRenderer;
import com.level.tile.MultiTile;
import com.main.Game;
import com.resources.ResourceManager;
import com.resources.Texture;

public class Furnace extends MultiTile{

	private boolean open = true;
	private Texture UI;

	public Item input;
	public Item fuel;
	public Item result;
	
	public Furnace(Vector2f size, Vector2f structureSize, Texture sprite, Vector3f color, int id, int breakTime, boolean collision, Item drop) {
		super(size, structureSize, sprite, color, id, breakTime, collision, drop);
		
		UI = ResourceManager.getTexture("furnaceInterface");
	}
	
	public void tick() {
		if(Game.mouseOver(108, 108, 16, 16)) {
			System.out.println("Input slot");
		} else if(Game.mouseOver(108, 132, 16, 16)) {
			System.out.println("Fuel slot");
		}
	}
	
	@Override
	public void render(int x, int y, SpriteRenderer r, float lightLevel, int layer) {
		if(id != 0) {
			r.render(sprite, new Vector2f((x - (structureSize.x-1)/2)*Game.tileSize, (y - (structureSize.y-1)/2)*Game.tileSize + 2), size, 0, new Vector3f(lightLevel/8.0f, lightLevel/8.0f, lightLevel/8.0f), true, ResourceManager.getTexture("center"));
		}
		renderInterface(r);
	}
	
	public void renderInterface(SpriteRenderer r) {
		if(open) {
			tick();
			r.render(UI, new Vector2f(500, 100), new Vector2f(128, 56), 0, new Vector3f(1,1,1), false);
		}
	}

}
