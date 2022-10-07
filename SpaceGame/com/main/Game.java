package com.main;

import java.nio.DoubleBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.entities.mob.Player;
import com.gfx.SpriteRenderer;
import com.hud.Radar;
import com.level.Level;
import com.menu.MainMenu;
import com.resources.ResourceManager;

public class Game {
	
	public enum State{
		ACTIVE,
		MENU,
		PAUSE,
		DEAD
	};
	
	State current = State.ACTIVE;
	
	public boolean keys[] = new boolean[1024];
	
	public static int totalTextures = 0;
	
	SpriteRenderer render;
	
	public static int tileSize = 16;
	
	public static DoubleBuffer cursorX = BufferUtils.createDoubleBuffer(1), cursorY = BufferUtils.createDoubleBuffer(1);
	public static int mX, mY;
	public static float r = 0.3f, g = 0.62f, b = 0.9f;
	public static int mB;
	public static Level level;
	public static Player player;
	public MainMenu menu;
	public Radar ra;
	public static Camera cam;
	
	public static int viewDistance = 10;

	public Matrix4f projection;
	
	public Game() {
		
		ResourceManager.loadShader("shaders/vertex.vs", "shaders/fragment.fs", null, "sprite");

		projection = new Matrix4f();
		projection = projection.ortho(0, CreateGame.WIDTH, CreateGame.HEIGHT, 0, -1.0f, 1.0f);
		ResourceManager.getShader("sprite").use().setInt("image", 0);
		ResourceManager.getShader("sprite").use().setInt("mask", 1);
		ResourceManager.getShader("sprite").setMatrix4("projection", projection, true);
		
		render = new SpriteRenderer(ResourceManager.getShader("sprite"));
	    ResourceManager.loadTexture("dirt.png", true, "dirt");
	    ResourceManager.loadTexture("stone.png", true, "stone");
	    ResourceManager.loadTexture("log.png", true, "log");
	    ResourceManager.loadTexture("leaves.png", true, "leaves");
	    ResourceManager.loadTexture("logWithLeaves.png", true, "logWithLeaves");
	    ResourceManager.loadTexture("longGrass.png", true, "longGrass");
	    ResourceManager.loadTexture("rock.png", true, "rock");
	    ResourceManager.loadTexture("grass.png", true, "grass");
	    ResourceManager.loadTexture("ironOre.png", true, "ironOre");
	    ResourceManager.loadTexture("coalOre.png", true, "coalOre");
	    ResourceManager.loadTexture("uraniumOre.png", true, "uraniumOre");
	    ResourceManager.loadTexture("stoneBack.png", true, "stoneBack");
	    ResourceManager.loadTexture("dirtBack.png", true, "dirtBack");
	    ResourceManager.loadTexture("hotbar.png", true, "hotbar");
	    ResourceManager.loadTexture("torch.png", true, "torch");
	    ResourceManager.loadTexture("sand.png", true, "sand");
	    ResourceManager.loadTexture("sapling.png", true, "sapling");
	    ResourceManager.loadTexture("Inventory.png", true, "inventory");
	    ResourceManager.loadTexture("furnace.png", true, "furnace");
	    ResourceManager.loadTexture("furnaceInterface.png", true, "furnaceInterface");
	    ResourceManager.loadTexture("voidSprite.png", true, "void");
	    ResourceManager.loadTexture("menu/star.png", true, "star");
	    ResourceManager.loadTexture("menu/title.png", true, "title");
	    ResourceManager.loadTexture("selected.png", true, "selected");
	    ResourceManager.loadTexture("colourTile.png", true, "colourTexture");
	    
	    ResourceManager.loadTexture("masks/t.png", true, "t");
	    ResourceManager.loadTexture("masks/tl.png", true, "tl");
	    ResourceManager.loadTexture("masks/tr.png", true, "tr");
	    ResourceManager.loadTexture("masks/l.png", true, "l");
	    ResourceManager.loadTexture("masks/center.png", true, "center");
	    ResourceManager.loadTexture("masks/r.png", true, "r");
	    ResourceManager.loadTexture("masks/bl.png", true, "bl");
	    ResourceManager.loadTexture("masks/b.png", true, "b");
	    ResourceManager.loadTexture("masks/br.png", true, "br");
	    ResourceManager.loadTexture("masks/blt.png", true, "blt");
	    ResourceManager.loadTexture("masks/trb.png", true, "trb");
	    ResourceManager.loadTexture("masks/ltr.png", true, "ltr");
	    ResourceManager.loadTexture("masks/lbr.png", true, "lbr");
	    ResourceManager.loadTexture("masks/ltrb.png", true, "ltrb");

	    menu = new MainMenu(50);
		cam = new Camera();
	    level = Level.random;
	    
	    player = new Player(600*16, 0, ResourceManager.getTexture("colourTexture"), cam);
	    level.addPlayer(player);
	    
	    ra = new Radar(0, 0, player);
	    
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	    
	}
	
	public static boolean mouseOver(int x, int y, int width, int height) {
		double cX = mX, cY = mY;
		if(x < cX && x+width > cX) {
			if(y < cY && y+height > cY) {
				return true;
			}
		}
		
		return false;
	}
	

	public void handleInput() {
		
	}
	
	public static int numberPressed() {
		if(GLFW.glfwGetKey(CreateGame.window, GLFW.GLFW_KEY_1) == 1) return 1;
		else if(GLFW.glfwGetKey(CreateGame.window, GLFW.GLFW_KEY_2) == 1) return 2;
		else if(GLFW.glfwGetKey(CreateGame.window, GLFW.GLFW_KEY_3) == 1) return 3;
		else if(GLFW.glfwGetKey(CreateGame.window, GLFW.GLFW_KEY_4) == 1) return 4;
		else if(GLFW.glfwGetKey(CreateGame.window, GLFW.GLFW_KEY_5) == 1) return 5;
		else if(GLFW.glfwGetKey(CreateGame.window, GLFW.GLFW_KEY_6) == 1) return 6;
		else if(GLFW.glfwGetKey(CreateGame.window, GLFW.GLFW_KEY_7) == 1) return 7;
		return -1;
	}
	
	public static float xScroll;
	public static float yScroll;
	public void tick() {
		GLFW.glfwGetCursorPos(CreateGame.window, cursorX, cursorY);
		mX = (int)cursorX.get(0) / 2;
		mY = (int)cursorY.get(0) / 2;
		if(GLFW.glfwGetMouseButton(CreateGame.window, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS) {
			mB = 1;
		} else if(GLFW.glfwGetMouseButton(CreateGame.window, GLFW.GLFW_MOUSE_BUTTON_MIDDLE) == GLFW.GLFW_PRESS) {
			mB = 2;
		}
		else if(GLFW.glfwGetMouseButton(CreateGame.window, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS) {
			mB = 3;
		} else mB = -1;
		
		
		if(current == State.ACTIVE) {
			level.tick();
			ra.tick();
			xScroll = cam.xOffset;
			yScroll = cam.yOffset;
		} else if(current == State.MENU) {
			r = 0.0f;
			g = 0.0f;
			b = 0.0f;
			menu.tick();
		}
	}
	
	public void render() {
		if(current == State.ACTIVE) {
			level.render(render, xScroll, yScroll);
			ra.render(render);
		} else if(current == State.MENU) {
			menu.render(render);
		}
	}
	
}
