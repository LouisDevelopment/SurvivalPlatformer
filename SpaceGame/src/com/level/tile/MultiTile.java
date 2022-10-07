package com.level.tile;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.entities.item.Item;
import com.gfx.SpriteRenderer;
import com.main.Game;
import com.resources.ResourceManager;
import com.resources.Texture;

public class MultiTile extends Tile{
	
	public Vector2f structureSize;

	public MultiTile(Vector2f size, Vector2f structureSize, Texture sprite, Vector3f color, int id, int breakTime, boolean collision,Item drop) {
		super(size, sprite, color, id, breakTime, collision, drop);
		
		this.structureSize = structureSize;
	}

	public void render(int x, int y, SpriteRenderer r, float lightLevel, int layer) {
		if(id != 0) {
			r.render(sprite, new Vector2f((x - (structureSize.x-1)/2)*Game.tileSize, (y - (structureSize.y-1)/2)*Game.tileSize + 2), size, 0, new Vector3f(lightLevel/8.0f, lightLevel/8.0f, lightLevel/8.0f), true, ResourceManager.getTexture("center"));
		}
	}
	
}
