package com.main;

public class Camera {
	
	public float xOffset, yOffset;
	
	public Camera() {
		xOffset = CreateGame.WIDTH/2;
		yOffset = CreateGame.HEIGHT/2;
	}
	
	public void moveCamera(float x, float y) {
		xOffset = x - CreateGame.WIDTH/2;
		yOffset = y - CreateGame.HEIGHT/2;
		
		
	}
	
}
