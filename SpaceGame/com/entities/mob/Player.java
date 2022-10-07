package com.entities.mob;

import java.nio.DoubleBuffer;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import com.entities.item.ItemStack;
import com.entities.particles.ParticleSpawner;
import com.gfx.SpriteRenderer;
import com.hud.Health;
import com.hud.Inventory;
import com.level.tile.MultiTile;
import com.level.tile.Tile;
import com.main.Camera;
import com.main.CreateGame;
import com.main.Game;
import com.resources.Texture;

public class Player extends Mob{

	private Camera cam;
	private Inventory inv;
	private Health health;
	//private Radar radar;
	
	private int jump = 5;
	private boolean toggle = false;
	private boolean mining = true;
	private int destroy = 0;
	private int tileX = 0, tileY = 0;
	private int speed = 2;
	DoubleBuffer mouseX = BufferUtils.createDoubleBuffer(1);
	DoubleBuffer mouseY = BufferUtils.createDoubleBuffer(1);
	int mB = 0;
	
	private int mX = 0, mY = 0;
	private float xVel = 0, yVel = 0;
	
	public Player(int x, int y, Texture sprite, Camera cam) {
		super(x, y, sprite);

		inv = new Inventory();
		this.cam = cam;
		health = new Health(100);
		
		//this.radar = new Radar(0, 0, this);
	}
	
	public void tick() {
		checkMouse();
		
		if(!collision(2))
			fall();
		else {
			yVel = 0;
			if(toggle){
				toggle = false;
			}
		}
		
		collision(0);
		
		if(GLFW.glfwGetKey(CreateGame.window, GLFW.GLFW_KEY_D) == 1 && !collision(1)) {
				xVel += speed;
		}
		if(GLFW.glfwGetKey(CreateGame.window, GLFW.GLFW_KEY_A) == 1 && !collision(3)) {
				xVel -= speed;
		}
		
		if(GLFW.glfwGetKey(CreateGame.window, GLFW.GLFW_KEY_W) == 1 && !toggle) {
			yVel = -jump;
			toggle = true;
		}
		//if(GLFW.glfwGetKey(CreateGame.window, GLFW.GLFW_KEY_W) == 1 && !collision(0)) {
			//yVel -= speed;
		//}
		//if(GLFW.glfwGetKey(CreateGame.window, GLFW.GLFW_KEY_S) == 1 && !collision(2)) {
			//yVel += speed;
		//}
		
		
		move(xVel, yVel);
		
		xVel = 0;
		//yVel = 0;
		
		inv.tick();
		health.tick();
	}
	
	public void render(SpriteRenderer renderer) {
		renderer.render(sprite, new Vector2f(x, y), new Vector2f(Game.tileSize, Game.tileSize), 0, new Vector3f(1,1,1), true);

		Tile tile = Tile.getTileFromID(inv.hotbar.equipped.item.getTile());
		if(tile.id != -1 && tile.id != 0) {
			renderer.render(tile.sprite, new Vector2f((mX / Game.tileSize) * Game.tileSize, (mY / Game.tileSize)* Game.tileSize), new Vector2f(Game.tileSize, Game.tileSize), 0, new Vector3f(0.5f,1,0.5f), true);
		}
	}
	
	public void renderAboveLevel() {
		if(mining) {
			mine();
		}
	}
	
	public void renderUI(SpriteRenderer renderer) {
		inv.render(renderer);
		//radar.render();
		health.render(renderer);
	}
	
	private void checkMouse() {
		if(!inv.open) {
		int state = GLFW.glfwGetMouseButton(CreateGame.window, GLFW.GLFW_MOUSE_BUTTON_LEFT);
			mX = (int) (Game.cursorX.get(0)/2 + cam.xOffset);
			mY = (int) (Game.cursorY.get(0)/2 + cam.yOffset);
			
			if(mX-x < 64 && mX-x > -48) {
				if(mY-y < 64 && mY-y > -48) {
					if(state == GLFW.GLFW_PRESS) {
						checkMine(mX, mY);
					} else destroy = 0;
					
					if(Game.mB == 2) {
						checkBackgroundBuild(mX, mY);
					}
					if(Game.mB == 3) {
						checkBuild(mX, mY);
					}
				}
			}
			
		}
	}
	
	
	public void checkBuild(int x, int y) {
		int tileX = (int)(x/16);
		int tileY = (int)(y/16);
		if(inv.hotbar.equipped.item.getTile() != -1) {
			System.out.println(inv.hotbar.equipped.item.getTile());
			Tile t = Game.level.getTile(tileX, tileY, 1);
			if(t == Tile.voidTile && !(t instanceof MultiTile)) {
				Game.level.setTile(tileX, tileY, inv.hotbar.equipped.item.getTile());
				inv.hotbar.equipped.removeItem(1);
			} else if(t == Tile.voidTile){
				boolean canPlace = true;
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						if(Game.level.getTile(tileX + i, tileY + j, 1) != Tile.voidTile) {
							canPlace = false;
						}
					}
				}
				if(canPlace) {
					Game.level.setMultiTile(tileX, tileY, inv.hotbar.equipped.item.getTile());
					inv.hotbar.equipped.removeItem(1);
				}
			}
		}
	}
	
	public void checkBackgroundBuild(int x, int y) {
		int tileX = (int)(x/16);
		int tileY = (int)(y/16);
		if(inv.hotbar.equipped.item.getTile() != -1) {
			System.out.println(inv.hotbar.equipped.item.getTile());
			Tile t = Game.level.getTile(tileX, tileY, -1);
			if(t == Tile.voidTile) {
				Game.level.setBackground(tileX, tileY, inv.hotbar.equipped.item.getTile());
				inv.hotbar.equipped.removeItem(1);
			}
		}
	}
	
	private Tile currentTile = Tile.voidTile;
	public void checkMine(int x, int y) {
		tileX = (int)(x/Game.tileSize);
		tileY = (int)(y/Game.tileSize);
		currentTile = Game.level.getTile(tileX, tileY, 1);
		if(currentTile != Tile.voidTile) {
			mining = true;
			destroy++;
		} else {
			destroy = 0;
			mining = false;
		}
	}
	
	public void mine() {

		if(currentTile != Tile.voidTile) {
			if(destroy >= (float)(currentTile.getBreakTime())) {
				Tile current = Game.level.getTile(tileX, tileY, 1);
				if(current.id >0) {
					if(current.id == 40 || current.id == 41) {
						Game.level.destroyTree(tileX, tileY);
					} else {
						if(!Game.level.checkForStack(tileX, tileY)) {
							ItemStack e = new ItemStack(tileX, tileY, current.drop);
							Game.level.addItem(e);
						} else if(Game.level.getStack(tileX, tileY).item == current.drop) {
							Game.level.getStack(tileX, tileY).addItem(1);
						}

						ParticleSpawner particle = new ParticleSpawner(tileX*Game.tileSize + 8, tileY*Game.tileSize, 30, 20, Game.level.getTileColour(Game.level.getTile(tileX, tileY, 1)));
						Game.level.setTile(tileX, tileY, 0);
					}
				}
				
				if(current == Tile.grass) {
					if(Game.level.getTile(tileX, tileY-1, 1) == Tile.longGrass) {
						Game.level.setTile(tileX, tileY-1, 0);
					}
				}
				mining = false;
				destroy = 0;
			} else if(destroy >= (float)((currentTile.getBreakTime() / 5) * 4)) {
				//Game.drawSprite(tileX*16, tileY*16, Sprite.break4, 1);
			} else if(destroy >= (float)((currentTile.getBreakTime() / 5) * 3)) {
				//Game.drawSprite(tileX*16, tileY*16, Sprite.break3, 1);
			} else if(destroy >= (float)((currentTile.getBreakTime() / 5) * 2)) {
				//Game.drawSprite(tileX*16, tileY*16, Sprite.break2, 1);
			} else if(destroy >= (float)(currentTile.getBreakTime() / 5)) {
				//Game.drawSprite(tileX*16, tileY*16, Sprite.break1, 1);
			}
		}
	}
	
	public void fall() {
		yVel += Game.level.gravity;
	}
	
	public boolean collision(int dir) {
		if(dir == 1) {
			if(Game.level.getTile((x+16)/16, y/16, 1).collision == true) {
				return true;
			}
			if(!collision(2)) {
				if(Game.level.getTile((x+16)/16, y/16 + 1, 1).collision == true) {
					return true;
				}
			}
		} else if(dir == 3) {
			if(Game.level.getTile((x-2)/16, y/16, 1).collision == true) {
				return true;
			}
			if(!collision(2)) {
				if(Game.level.getTile((x-2)/16, y/16 + 1, 1).collision == true) {
					return true;
				}
			}
		} else if(dir == 2) {
			Tile tile1 = Game.level.getTile(x/16, (y+16)/16, 1);
			Tile tile2 = Game.level.getTile((x-2)/16 + 1, (y+16)/16, 1);
			if(tile1.collision == true) {
				y /= 16;
				y *= 16;
				y += 2;
				return true;
			} else if(tile2.collision == true) {
				y /= 16;
				y *= 16;
				y += 2;
				return true;
			}
		} else if(dir == 0) {
			if(Game.level.getTile(x/16, (y)/16, 1).collision == true) {
				y /= 16;
				y *= 16;
				return true;
			} else if(Game.level.getTile((x-2)/16 + 1, (y-2)/16, 1).collision == true) {
				y /= 16;
				y *= 16;
				return true;
			}
		}
		
		return false;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
}
