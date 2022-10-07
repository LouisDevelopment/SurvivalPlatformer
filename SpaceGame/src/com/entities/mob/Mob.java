package com.entities.mob;

import com.entities.Entity;
import com.main.Game;
import com.resources.Texture;

public class Mob extends Entity{

	protected Texture sprite;
	protected int speed = 2;
	
	public Mob(int x, int y, Texture sprite) {
		this.x = x;
		this.y = y;
		
		this.sprite = sprite;
	}
	
	public void move(float xVel, float yVel) {
			x+=xVel;
			y+=yVel;
			
			Game.cam.moveCamera(x, y);
			
	}

	
	
}
