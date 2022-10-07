package com.entities.particles;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.gfx.SpriteRenderer;
import com.level.tile.Tile;
import com.main.Game;
import com.resources.ResourceManager;

public class Particle {

	public float x, y;
	public int width, height, life;
	public Vector3f colour;
	public float xVel, yVel;
	private int current = 0;
	
	public Particle(int x, int y, float xVel, float yVel, int width, int height, int life, Vector3f colour) {
		this.x = x;
		this.y = y;
		this.width = height;
		this.height = height;
		this.xVel = xVel;
		this.yVel = yVel;
		this.colour = colour;
		this.life = life;
		
		Game.level.addParticle(this);
	}
	
	public void tick() {
		current++;
		
		x += xVel;
		
		
		if(!collision(2)) {
			yVel += Game.level.gravity;
		} else {
			yVel = -yVel/4;
			xVel /= 2;
		}
		y+= yVel;
		if(current >= life) {
			Game.level.removeParticle(this);
		}
	}
	
	public void render(SpriteRenderer renderer) {
		renderer.render(ResourceManager.getTexture("colourTexture"), new Vector2f(x, y), new Vector2f(width, height), 0, colour, true);
	}
	
	public boolean collision(int dir) {
		if(dir == 1) {
			if(Game.level.getTile((int)(x+1)/16,(int) y/16, 1).collision == true) {
				return true;
			}
		} else if(dir == 3) {
			if(Game.level.getTile((int)(x-2)/16, (int)y/16, 1).collision == true) {
				return true;
			}
		} else if(dir == 2) {
			Tile tile1 = Game.level.getTile((int)x/16, (int)(y)/16, 1);
			if(tile1.collision == true) {
				y /= 16;
				y *= 16;
				if(yVel != 0) {
					y -= yVel;
				}
				return true;
			}
		}
		
		return false;
	}
	
}
