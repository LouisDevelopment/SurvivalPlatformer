package com.hud;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.gfx.SpriteRenderer;
import com.resources.ResourceManager;

public class Health {

	public float max, current;
	private Vector3f colour = new Vector3f(0, 1, 0.1f);
	
	
	public Health(int max) {
		this.max = max;
		current = max;
	}
	
	public void tick() {
		if(current > 0) {
			current--;
		}
		colour.x = 1 - current/max;
		colour.y = current/max;
	}
	
	public void render(SpriteRenderer r) {
		r.render(ResourceManager.getTexture("colourTexture"), new Vector2f(8, 8), new Vector2f(current/max * 128, 24), 0, colour, false);
	}
	
	public float getHealth() {
		return current;
	}
	
}
