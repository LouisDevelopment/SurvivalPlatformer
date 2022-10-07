package com.gfx;

import java.nio.ByteBuffer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.main.Game;
import com.resources.ResourceManager;
import com.resources.Shader;
import com.resources.Texture;

public class SpriteRenderer {
	
	private Shader shader;
	private int quadVAO, EBO;

	
	byte indices[] = {
			0,1,3,
			1,2,3
	};
	
	public SpriteRenderer(Shader s) {
		shader = s;
		initRenderData();
	}
	
	public SpriteRenderer() {
		
	}
	
	public void render(Texture tex, Vector2f pos, Vector2f size, float rotate, Vector3f colour, boolean offset) {
		if(offset) {
			pos.x -= Game.xScroll;
			pos.y -= Game.yScroll;
		}
		shader.use();
		
		
		Matrix4f model = new Matrix4f();
		model = model.translate(new Vector3f(pos, 0.0f));

		model = model.translate(new Vector3f(0.5f*size.x, 0.5f*size.y, 0.0f));
		model = model.rotate((float)Math.toRadians(rotate), new Vector3f(0.0f, 0.0f, 1.0f), (model));
		model = model.translate(new Vector3f(-0.5f*size.x, -0.5f * size.y, 0.0f));
		
		model = model.scale(new Vector3f(size, 1.0f));
		
		shader.setMatrix4("model", model, false);
		shader.setVec3("spriteColor", colour.x, colour.y, colour.z);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		tex.bind();
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		ResourceManager.getTexture("center").bind();

		GL30.glBindVertexArray(quadVAO);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, EBO);
		GL11.glDrawElements(GL11.GL_TRIANGLES, indices.length, GL11.GL_UNSIGNED_BYTE, 0);

		// Put everything back to default (deselect)
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		tex.unbind();
		ResourceManager.getTexture("center").unbind();
	}
	
	public void render(Texture tex, Vector2f pos, Vector2f size, float rotate, Vector3f colour, boolean offset, Texture mask) {
		if(offset) {
			pos.x -= Game.xScroll;
			pos.y -= Game.yScroll;
		}
		shader.use();
		
		
		Matrix4f model = new Matrix4f();
		model = model.translate(new Vector3f(pos, 0.0f));

		model = model.translate(new Vector3f(0.5f*size.x, 0.5f*size.y, 0.0f));
		model = model.rotate((float)Math.toRadians(rotate), new Vector3f(0.0f, 0.0f, 1.0f), (model));
		model = model.translate(new Vector3f(-0.5f*size.x, -0.5f * size.y, 0.0f));
		
		model = model.scale(new Vector3f(size, 1.0f));
		
		shader.setMatrix4("model", model, false);
		shader.setVec3("spriteColor", colour.x, colour.y, colour.z);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		tex.bind();
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		mask.bind();

		GL30.glBindVertexArray(quadVAO);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, EBO);
		GL11.glDrawElements(GL11.GL_TRIANGLES, indices.length, GL11.GL_UNSIGNED_BYTE, 0);

		// Put everything back to default (deselect)
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		tex.unbind();
		mask.unbind();
	}
	
	private void initRenderData() {
		int VBO;
		
		float[] vertices = {
				0.0f, 1.0f,
		        0.0f, 0.0f,
		        1.0f, 0.0f,
		        1.0f, 1.0f
		};
		
		float[] tex = {
			0, 1,
			0, 0,
			1, 0,
			1, 1
		};
		
		ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indices.length);
		indicesBuffer.put(indices);
		indicesBuffer.flip();
		
		quadVAO = GL30.glGenVertexArrays();
		VBO = GL15.glGenBuffers();
		EBO = GL15.glGenBuffers();
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBO);
		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, EBO);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
		
		GL30.glBindVertexArray(quadVAO);
		GL20.glEnableVertexAttribArray(0);
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 2*Float.BYTES, 0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 2*Float.BYTES, 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}
	
}
