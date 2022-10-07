package com.hud;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.entities.item.StackInInv;
import com.gfx.SpriteRenderer;
import com.main.CreateGame;
import com.main.Game;
import com.resources.ResourceManager;

public class Hotbar {
	
	public List<StackInInv> items = new ArrayList<StackInInv>();
	
	public StackInInv equipped;
	public boolean pressed, switched = false;
	public int selected = -1;
	
	public int size = 7;
	public int x = CreateGame.WIDTH/2 - 64, y = CreateGame.HEIGHT - 48;
	
	public Hotbar() {
		equipped = StackInInv.voidStack;
		
		for(int i = 0; i < size; i++) {
			items.add(StackInInv.voidStack);
		}
	}
	
	public void tick() {

		if(selected != -1 && Game.mB == -1) {
			if(Game.mouseOver(x + 14*2 + 14, y + 20, 12, 12)) {
				StackInInv temp = equipped;
				equipped = items.get(selected);
				items.set(selected, temp);
				selected = -1;
				pressed = false;
			}
		}
		if(Game.mB == 3) {
			if(Game.mouseOver(x + 14*2 + 14, y + 20, 12, 12)) {
				if(items.contains(StackInInv.voidStack)) {
					items.set(items.indexOf(StackInInv.voidStack), equipped);
					equipped = StackInInv.voidStack;
				}
			}
		}
		for(int i = 0; i < size-1; i++) {
			if(Game.mouseOver(x + 14*i + 18, y + 8, 8, 8)) {
				if(Game.mB == 1 && !pressed) {
					if(selected == -1) {
						selected = i;
						pressed = true;
					}
				} else if(Game.mB == -1 && pressed) {
					System.out.println("Working");
					StackInInv temp = items.get(selected);
					items.set(selected, items.get(i));
					items.set(i, temp);
					selected = -1;
					pressed = false;
				}
			}
		}
		
		/*int pressed = Game.numberPressed();
		
		if(pressed != -1 && !switched) {
			StackInInv temp = items.get(pressed-1);
			items.set(pressed-1, equipped);
			equipped = temp;
			switched = true;
		}
		
		if(pressed == -1 && switched) {
			switched = false;
		}*/
	}
	
	public void render(SpriteRenderer r) {
		r.render(ResourceManager.getTexture("hotbar"), new Vector2f(x, y), new Vector2f(128, 36), 0, new Vector3f(1,1,1), false);
		for(int i = 0; i < size; i++) {
			r.render(items.get(i).item.sprite, new Vector2f(x + 14*i + 18, y + 8), new Vector2f(8, 8), 0, new Vector3f(1,1,1), false);
		}
		r.render(equipped.item.sprite, new Vector2f(x + 14*2 + 18, y + 24), new Vector2f(8, 8), 0, new Vector3f(1,1,1), false);
		
		
	}
	
	
	public boolean isEquipped(StackInInv item) {
		if(equipped == item) return true;
		return false;
	}
	
}
