package com.entities.particles;

import java.util.Random;

import org.joml.Vector3f;

public class ParticleSpawner {

	private int life;
	private Vector3f colour;
	private int time = 0;
	private int x, y;
	private float xVel = 0, yVel = 0;
	private boolean randomX = false;
	private Particle[] particles;
	
	private Random r = new Random();
	
	public ParticleSpawner(int x, int y, int life, int total, Vector3f colour){
		this.x = x;
		this.y = y;
		this.colour = colour;
		this.life = life;
		xVel = r.nextFloat() - 0.5f;
		yVel = -0.8f;
		randomX = true;
		
		particles = new Particle[total];
		
		setUpParticles();
	}
	
	public ParticleSpawner(int x, int y, float xVel, float yVel, int life, int total, Vector3f colour){
		this.x = x;
		this.y = y;
		this.colour = colour;
		this.life = life;
		this.xVel = xVel;
		this.yVel = yVel;
		
		particles = new Particle[total];
		
		setUpParticles();
	}
	
	public void setUpParticles(){
		for(int i = 0; i < particles.length; i++) {
			if(randomX) {
				xVel = r.nextFloat() - 0.5f;
				particles[i] = new Particle(x + r.nextInt(8)-4, y + r.nextInt(8)-4, xVel, yVel, 2, 1, life, colour);
			}
			particles[i] = new Particle(x + r.nextInt(8)-4, y + r.nextInt(8)-4, xVel, yVel, 2, 1, life, colour);
		}
	}
	
}
