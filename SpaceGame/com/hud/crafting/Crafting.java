package com.hud.crafting;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.entities.item.StackInInv;
import com.gfx.SpriteRenderer;
import com.hud.Inventory;
import com.main.Game;

public class Crafting {

	public static List<Recipe> recipes = new ArrayList<Recipe>();
	
	public int x, y;
	private Inventory inv;
	private boolean pressed = false;
	
	public Crafting(int x, int y, Inventory inv) {
		this.x = x;
		this.y = y;
		this.inv = inv;
	}
	
	public void tick() {
		int mouseButton = Game.mB;
		
		for(int i = 0; i < recipes.size(); i++) {
			if(Game.mouseOver(x+20, (y+ 16*(i)) - 4, 24, 24) && mouseButton == 1 && !pressed) {
				craft(recipes.get(i));
				pressed = true;
			}
		}
		
		if(mouseButton != 1) {
			pressed = false;
		}
	}
	
	public void render(SpriteRenderer r) {
		//Game.drawLockedSprite(x-48, y-24, Sprite.crafting, 2);
		for(int i = 0; i < recipes.size(); i++) {
			for(int j = 0; j < recipes.get(i).items.length; j++) {
				r.render(recipes.get(i).items[j].sprite, new Vector2f(x-16*recipes.get(i).items.length + 16*(j+1), y + 16*(i)), new Vector2f(8, 8), 0, new Vector3f(1,1,1), false);
			}
			r.render(recipes.get(i).result.sprite, new Vector2f(x + 24, y + 16*(i)), new Vector2f(8, 8), 0, new Vector3f(1,1,1), false);
		}
	}
	
	public void craft(Recipe r) {
		int canCraft = 0;
		Inventory tempInv = inv;
		for(int i = 0; i < r.items.length; i++) {
			boolean haveItem = false;
			for(int j = 0; j < tempInv.items.size(); j++) {
				if(tempInv.items.get(j).item == r.items[i]) {
					if(!haveItem) {
						tempInv.items.get(j).removeItem(1);
					}
					haveItem = true;
				}
			}
			if(haveItem) canCraft++;
		}
		if(canCraft == r.items.length) {
			System.out.print("Working");
			boolean done = false;
			for(int j = 0; j < inv.items.size(); j++) {
				if(!done) {
					if(tempInv.items.get(j) == StackInInv.voidStack) {
						tempInv.items.set(j, new StackInInv(r.result));
						done = true;
					}
				}
			}
			inv.items = tempInv.items;
		}
	}
	
	public void addRecipe(Recipe r) {
		recipes.add(r);
	}
	
	public void addAllRecipes() {
		recipes.add(Recipe.furnace);
		recipes.add(Recipe.primativeHatchet);
		recipes.add(Recipe.primativeSpear);
		recipes.add(Recipe.torch);
		recipes.add(Recipe.twineRope);
	}
	
	public void updateX(int x) {
		this.x = x;
	}
	public void updateY(int y) {
		this.y = y;
	}
	
}
