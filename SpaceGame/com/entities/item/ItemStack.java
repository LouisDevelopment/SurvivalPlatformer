package com.entities.item;

import java.util.ArrayList;
import java.util.List;

import com.gfx.SpriteRenderer;
import com.main.Game;

public class ItemStack {

	public List<Item> stack = new ArrayList<Item>();
	
	public Item item = null;
	
	public int x, y;
	public int pixelX, pixelY;
	public float yVel = 0, xVel = 0;
	public float yOffset = 0;
	public boolean reset = true;
	
	public ItemStack(int x, int y, Item itemType) {
		this.x = x;
		this.y = y;
		this.pixelX = x*16;
		this.pixelY = y*16;
		item = itemType;
		addItem(1);
	}
	
	public ItemStack(int x, int y, float xVel, float yVel, Item itemType) {
		this.x = x;
		this.y = y;
		this.pixelX = x*16;
		this.pixelY = y*16;
		this.xVel = xVel;
		this.yVel = yVel;
		item = itemType;
		addItem(1);
	}
	
	public void addItem(int amount) {
		for(int i = 0; i < amount; i++) {
			stack.add(item);	
		}
		
	}
	
	public void tick() {
		x = pixelX/16;
		y = pixelY/16;

		if(yVel == 0) {
		if(!reset) {
			yOffset += 0.2f;
		}
		if(yOffset <= -8f) {
			reset = false;
		}
		if(yOffset <= 0f && reset) {
			yOffset -= 0.2f;
		}
		if(yOffset >= -0.2f) {
			reset = true;
		}
		}
		
		if(Game.level.getTile((int)x, (int)y + 1, 1).collision == false) {
			fall();
		} else if(yVel != 0){
			yVel = 0;
		}
		
		pixelX += xVel;
		if(xVel > 0) {
			xVel -= 0.04f;
		} else {
			xVel = 0;
		}
		
	}
	
	public void fall() {
		if(yVel < 1) {
			yVel += Game.level.gravity;
		}
		pixelY += yVel;
	}
	
	public void render(SpriteRenderer r) {
		if(stack.size() > 0) {
			item.renderInWorld(pixelX + 4, pixelY + 4 + (int)yOffset, r);
		}
	}
	
}
