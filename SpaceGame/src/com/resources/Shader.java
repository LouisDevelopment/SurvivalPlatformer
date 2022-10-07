package com.resources;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

public class Shader {

public int ID;
	
	public Shader(StringBuilder vertCode, StringBuilder fragCode, StringBuilder geoCode) {
		
		int vert, frag, geo = -1;
		vert = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(vert, vertCode);
		GL20.glCompileShader(vert);

		frag = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		GL20.glShaderSource(frag, fragCode);
		GL20.glCompileShader(frag);

		if(geoCode != null) {
			geo = GL20.glCreateShader(GL32.GL_GEOMETRY_SHADER);
			GL20.glShaderSource(geo, geoCode);
			GL20.glCompileShader(geo);
		}
		
		
		ID = GL20.glCreateProgram();
		GL20.glAttachShader(ID, vert);
		GL20.glAttachShader(ID, frag);
		if(geoCode != null) {
			GL20.glAttachShader(ID, geo);
		}
		GL20.glLinkProgram(ID);
		
		GL20.glDeleteShader(vert);
		GL20.glDeleteShader(frag);
		if(geoCode != null) {
			GL20.glDeleteShader(geo);
		}
	}
	
	public Shader use() { 
	    GL20.glUseProgram(ID);
	    return this;
	}  
	
	public void unbind() {
	    GL20.glUseProgram(0);
	}
	
	public void setBool(String name, boolean val) {
		int value;
		if(val) {
			value = GL11.GL_TRUE;
		} else value = GL11.GL_FALSE;
		GL20.glUniform1i(GL20.glGetUniformLocation(ID, name), value);
	}
	
	public void setInt(String name, int val) {
		GL20.glUniform1i(GL20.glGetUniformLocation(ID, name), val);
	}
	
	public void setFloat(String name, float val) {
		GL20.glUniform1f(GL20.glGetUniformLocation(ID, name), val);
	}
	
	public void setVec3(String name, float x, float y, float z) {
		GL20.glUniform3f(GL20.glGetUniformLocation(ID, name), x, y, z);
	}
	public void setVec2(String name, float x, float y) {
		GL20.glUniform2f(GL20.glGetUniformLocation(ID, name), x, y);
	}
	
	public void setMatrix4(String name, Matrix4f m, boolean useShader) {
		float[] val = new float[16];
		val = m.get(val);
		GL20.glUniformMatrix4fv(GL20.glGetUniformLocation(ID, name), false, val);
	}
	
}
