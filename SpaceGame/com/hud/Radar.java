package com.hud;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import com.entities.mob.Player;
import com.gfx.SpriteRenderer;
import com.level.tile.Tile;
import com.main.Game;
import com.resources.ResourceManager;

public class Radar {

	public int x, y, xPos, yPos;
	public int size = 74;
	private Player player;
	private float[] pixels;
	
	
	private int playerVisionRange = 10;
	
	public Radar(int x, int y, Player player) {
		xPos = x; 
		yPos = y;
		this.player = player;
		pixels = new float[size*size];
		
		ResourceManager.loadTexture(pixels, size, size, false, "radar");
	}
	
	public void tick() {
		pixels = new float[size*size];
		x = player.x;
		y = player.y;
		for(int y = -(size/2); y < size/2; y++) {
			for(int x = -(size/2); x < size/2; x++) {
				int temp;
				if(this.x/16+x > 0 && this.y/16+y > 0) {
					temp = Game.level.getTile((this.x/16) + x, (this.y/16) + y, 1).id;
					if(temp < 1) {
						temp = Game.level.getTile((this.x/16) + x, (this.y/16) + y, -1).id;
					}
				} else {
					temp = 0;
				}
				if(this.x/16+x > 0 && this.y/16+y > 0) {
					if(Game.level.getLightLevel(this.x/16 + x, this.y/16 + y) > 0 || Game.level.discovered[this.x/16 + x][this.y/16 + y]) {
						pixels[(x+size/2) + ((y+size/2) * size)] = Game.level.getTileColour(temp);
					} else{
						float d = (float)((Math.sqrt((0 - x)*(0 - x) + (0 - y)*(0 - y))));
						if(d < playerVisionRange) {
							pixels[(x+size/2) + ((y+size/2) * size)] = Game.level.getTileColour(temp);
						} else pixels[(x+size/2) + (y+size/2) * size] = 0;
					}
				}
			}
		}
		
		pixels[(size/2)+(size/2)*size] = 0xffffff;
		
		ResourceManager.getTexture("radar").update(pixels, size, size, GL11.GL_RGBA);
	}
	
	public void render(SpriteRenderer render) {
		render.render(ResourceManager.getTexture("radar"), new Vector2f(0,0), new Vector2f(size, size), 0, new Vector3f(1,1,1), false);
	}
	
}
