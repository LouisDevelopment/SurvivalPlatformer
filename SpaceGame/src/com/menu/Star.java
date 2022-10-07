package com.menu;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.gfx.SpriteRenderer;
import com.resources.ResourceManager;

public class Star {

	public float size, brightness, rotate;
	public float currentSize;
	public boolean toggled = false;
	
	public int x, y;
	public int timer = 0;
	
	public Star(int x, int y, float size, float rotate, float startBrightness) {
		this.size = size;
		this.x = x;
		this.y = y;
		this.rotate = rotate;
		currentSize = size;
		brightness = startBrightness;
	}
	
	public void tick() {
		
		if(!toggled) {
			brightness += 0.016f;
			currentSize += 0.08f;
		}
		if(toggled) {
			brightness -= 0.016f;
			currentSize -= 0.08f;
		}
		
		if(brightness <= 0.02f) {
			toggled = false;
		}
		if(brightness >= 0.98f) {
			toggled = true;
		}
	}
	
	public void render(SpriteRenderer r) {
		r.render(ResourceManager.getTexture("star"), new Vector2f(x - currentSize/2, y-currentSize/2), new Vector2f(currentSize, currentSize), rotate, new Vector3f(brightness, brightness, brightness), false);
	}
	
}
