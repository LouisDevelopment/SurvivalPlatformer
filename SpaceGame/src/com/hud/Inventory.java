package com.hud;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import com.entities.item.Item;
import com.entities.item.ItemStack;
import com.entities.item.StackInInv;
import com.gfx.SpriteRenderer;
import com.hud.crafting.Crafting;
import com.level.Level;
import com.main.CreateGame;
import com.main.Game;
import com.resources.ResourceManager;

public class Inventory {

	public int size = 32;
	private int selected = -1;
	public boolean open = false, moving = false;
	public boolean reset = true;
	public boolean pressed = false, keyPressed = false;
	public int startX = 128, startY = 64, x = 128, y = 64;
	public int xMouseWhenMoved = -1, yMouseWhenMoved = -1;
	public int width= 240, height = 208;
	
	public List<StackInInv> items = new ArrayList<StackInInv>();
	
	public Hotbar hotbar;
	public Crafting crafting;
	
	public Inventory() {
		hotbar = new Hotbar();
		crafting = new Crafting(x+58, y+112, this);
		crafting.addAllRecipes();
		
		for(int i = 0; i < size-1; i++) {
			items.add(StackInInv.voidStack);
		}

		items.add(new StackInInv(Item.torch));
		
	}
	
	private int timer = 0;
	public void tick() {
		hotbar.tick();
		if(GLFW.glfwGetKey(CreateGame.window, GLFW.GLFW_KEY_TAB) == 1 && reset) {
			open = !open;
			reset = false;
		}
		
		if(GLFW.glfwGetKey(CreateGame.window, GLFW.GLFW_KEY_TAB) != 1) {
			reset = true;
		}
		
		checkForItem();

		if(open) {
			crafting.tick();
			if(Game.mouseOver(hotbar.x + 28*3 + 4, hotbar.y + 44, 24, 24)) {
				if(Game.mB == -1 && selected != -1) {
					StackInInv temp = hotbar.equipped;
					hotbar.equipped = items.get(selected);
					items.set(selected, temp);
					selected = -1;
					pressed = false;
				}
			}
			
			if(Game.mouseOver(this.x, this.y, 208, 10) && Game.mB == 1 && !moving) {
				xMouseWhenMoved = Game.mX;
				yMouseWhenMoved = Game.mY;
				startX = x;
				startY = y;
				moving = true;
			}
			if(Game.mB == 1 && moving) {
				if(startX + Game.mX - xMouseWhenMoved >= 0 && startX + Game.mX - xMouseWhenMoved <= CreateGame.WIDTH - width) {
					this.x = startX + Game.mX - xMouseWhenMoved;
					crafting.updateX(x+58);
				} else if(startX + Game.mX - xMouseWhenMoved < 0){
					this.x = 0;
					crafting.updateX(x+58);
				} else if(startX + Game.mX - xMouseWhenMoved > CreateGame.WIDTH - width){
					this.x = CreateGame.WIDTH - width;
					crafting.updateX(x+58);
				}
				if(startY + Game.mY - yMouseWhenMoved >= 0 && startY + Game.mY - yMouseWhenMoved <= CreateGame.HEIGHT - height) {
					this.y = startY + Game.mY - yMouseWhenMoved;
					crafting.updateY(y+112);
				} else if(startY + Game.mY - yMouseWhenMoved < 0){
					this.y = 0;
					crafting.updateY(y+112);
				} else if(startY + Game.mY - yMouseWhenMoved > CreateGame.HEIGHT - height){
					this.y = CreateGame.HEIGHT - height;
					crafting.updateY(y+112);
				}

			} else if(moving && Game.mB != 1) {
				moving = false;
			}
		}
		
		for(int i = 0; i < hotbar.size; i++) {
			if(Game.mouseOver(hotbar.x + 14*i + 18, hotbar.y + 8, 8, 8)) {
				if(Game.mB == -1 && selected != -1) {
					StackInInv temp = hotbar.items.get(i);
					hotbar.items.set(i, items.get(selected));
					items.set(selected, temp);
					selected = -1;
					pressed = false;
				} else if(Game.mB == 3 && selected == -1) {
					for(int j = 0; j < size; j++) {
						if(items.get(j) == StackInInv.voidStack) {
							items.set(j, hotbar.items.get(i));
							hotbar.items.set(i, StackInInv.voidStack);
						}
					}
				}
			}
		}
		
	}
	
	public void render(SpriteRenderer r) {
		hotbar.render(r);
		if(open) {
			int overX = 0;
			int overY = 0;
			r.render(ResourceManager.getTexture("inventory"), new Vector2f(this.x, this.y), new Vector2f(width, height), 0, new Vector3f(1,1,1), false);
			for(int i = 0; i < items.size(); i++) {
				int x = this.x+141 + i%4*22;
				int y = this.y+25 + i/4*22;
				int offset = 4;
				
				if(Game.mouseOver(x, y, 16, 16)) {
					timer++;
					overX = x;
					overY = y;
					if(Game.mB == -1 && pressed) {
						System.out.println("Working");
						StackInInv temp = items.get(selected);
						items.set(selected, items.get(i));
						items.set(i, temp);
						selected = -1;
						pressed = false;
					} else if(Game.mB == 1 && !pressed) {
						if(selected == -1) {
							selected = i;
							pressed = true;
						}
					} else if(Game.mB == 3 && selected == -1) {
						ItemStack e = new ItemStack(Game.player.x/16, Game.player.y/16 - 1, 2.5f, -1f, items.get(i).item);
						items.set(i, StackInInv.voidStack);
						Game.level.addItem(e);
					}
					
					if(Game.numberPressed() != -1 && !keyPressed) {
						System.out.println("Working");
						StackInInv temp = items.get(i);
						items.set(i, hotbar.items.get(Game.numberPressed()-1));
						hotbar.items.set(Game.numberPressed()-1, temp);
						selected = -1;
						keyPressed = true;
					} else if(Game.numberPressed() == -1) {
						keyPressed = false;
					}
					
					if(timer >= 30) {
						if(x == overX && y == overY) {
							//Text.drawPhrase(x, y-16, 10, 10, items.get(i).item.name);
						}
					}
				} else {
					//if(i == selected && Game.mB == -1 && pressed) {
						//selected = -1;
						//pressed = false;
					//}
				}
				
				if(i != selected) {
					items.get(i).item.render(x + offset, y + offset, r);
				} else {
					items.get(i).item.render((int)Game.mX - 4, (int)Game.mY - 4, r);
				}
			}

			crafting.render(r);
			if(selected != -1) {
				r.render(ResourceManager.getTexture("selected"), new Vector2f(this.x+141 + selected%4*22, this.y+25 + selected/4*22), new Vector2f(16, 16), 0, new Vector3f(1,1,1), false);
			}
		}
		
	}
	
	public void checkForItem() {
		for(int i = 0; i < 4; i++) {
			if(Level.random.checkForStack(Game.player.getX()/16 + (i%2), Game.player.getY()/16 + (i/2) )) {
				boolean done = false;
				ItemStack current = Game.level.getStack(Game.player.getX()/16 + (i%2), Game.player.getY()/16 + (i/2));
				if(current.xVel == 0) {
					for(int j = 0; j < size; j++) {
						if(items.get(j).item == current.item) {
							items.get(j).addItem(current.item);
							done = true;
						}
						if(items.get(j) == StackInInv.voidStack && !done) {
							StackInInv temp = new StackInInv(current.item);
							for(int k = 0; k < current.stack.size()-1; k++) {
								temp.addItem(current.item);
							}
							items.set(j, temp);
							done = true;
						}
					}
					if(done) {
						Game.level.removeItem(Game.player.getX()/16 + (i%2), Game.player.getY()/16 + (i/2));
					}
				}
			}
		}
	}
	
}
