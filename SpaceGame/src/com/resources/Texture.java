package com.resources;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;

public class Texture {
	
	public int id, width, height, alpha;
	
	public Texture(IntBuffer width, IntBuffer height, IntBuffer comp, ByteBuffer data, int alpha) {
		this.alpha = alpha;
		id = GL11.glGenTextures();
		
		this.width = width.get();
		this.height = height.get();
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, alpha, this.width, this.height, 0, alpha, GL11.GL_UNSIGNED_BYTE, data);
	}
	
	public Texture(float[] data, int width, int height, int alpha) {
		this.alpha = alpha;
		id = GL11.glGenTextures();
		
		this.width = width;
		this.height = height;
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, alpha, this.width, this.height, 0, alpha, GL11.GL_UNSIGNED_BYTE, data);
	}
	
	public void update(float[] data, int width, int height, int alpha) {
		this.alpha = alpha;
		
		this.width = width;
		this.height = height;
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, this.width, this.height, 0, alpha, GL11.GL_UNSIGNED_BYTE, data);
	}
	
	
	
	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
	
	public void unbind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
}
