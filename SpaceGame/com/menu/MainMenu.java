package com.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.gfx.SpriteRenderer;
import com.resources.ResourceManager;

public class MainMenu {

	private List<Star> stars = new ArrayList<Star>();
	private Random r = new Random();
	
	public MainMenu(int totalStars) {
		for(int i = 0; i < totalStars; i++) {
			stars.add(new Star(r.nextInt(64)*10, r.nextInt(64)*6, r.nextInt(4) + 5, r.nextInt(90), r.nextFloat()));
		}
	}
	
	public void tick() {
		for(int i = 0; i < stars.size(); i++) {
			stars.get(i).tick();
		}
	}
	
	public void render(SpriteRenderer r) {
		for(int i = 0; i < stars.size(); i++) {
			stars.get(i).render(r);
			r.render(ResourceManager.getTexture("title"), new Vector2f(140, 0), new Vector2f(360, 180), 0, new Vector3f(1, 1, 1), false);
		}
	}
	
}
