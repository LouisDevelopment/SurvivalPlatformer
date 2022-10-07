package com.resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.stb.STBImage;

public class ResourceManager {

	public static Map<String, Shader> shaders = new HashMap<String, Shader>();
	public static Map<String, Texture> textures = new HashMap<String, Texture>();
	
	public static Shader loadShader(String vShader, String fShader, String gShader, String name) {
		shaders.put(name, loadShaderFromFile(vShader, fShader, gShader));
		return shaders.get(name);
	}
	
	public static Shader getShader(String name) {
		return shaders.get(name);
	}

	public static Texture loadTexture(String file, boolean alpha, String name) {
		textures.put(name, loadTextureFromFile(file, alpha));
		return textures.get(name);
	}
	
	public static Texture loadTexture(float[] data, int width, int height, boolean alpha, String name) {
		textures.put(name, loadTextureFromArray(data, width, height, alpha));
		return textures.get(name);
	}
	
	public static Texture getTexture(String name) {
		return textures.get(name);
	}
	
	public static void clear() {
		for (Map.Entry<String, Shader> entry : shaders.entrySet()) {
		    Shader s = entry.getValue();

		    GL20.glDeleteProgram(s.ID);
		}

		for (Map.Entry<String, Texture> entry : textures.entrySet()) {
			Texture t = entry.getValue();
			
			GL11.glDeleteTextures(t.id);
		}
	}
	
	public static Shader loadShaderFromFile(String vShader, String fShader, String gShader) {
		StringBuilder vertCode = new StringBuilder();
		StringBuilder fragCode = new StringBuilder();
		StringBuilder geoCode = new StringBuilder();
		
		try{
            BufferedReader reader = new BufferedReader(new FileReader(vShader));
            String line;
            while((line = reader.readLine())!=null){
            	vertCode = vertCode.append(line).append(System.getProperty("line.separator"));
            }
           
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
		try{
            BufferedReader reader = new BufferedReader(new FileReader(fShader));
            String line;
            while((line = reader.readLine())!=null){
            	fragCode = fragCode.append(line).append(System.getProperty("line.separator"));
            }
           
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
		if(gShader != null) {
			try{
            	BufferedReader reader = new BufferedReader(new FileReader(gShader));
            	String line;
            	while((line = reader.readLine())!=null){
            		geoCode = geoCode.append(line).append(System.getProperty("line.separator"));
            	}
           
            	reader.close();
        	}catch(IOException e){
            	e.printStackTrace();
            	System.exit(-1);
        	}
		} else {
			geoCode = null;
		}
		
		Shader shader;
		shader = new Shader(vertCode, fragCode, geoCode);
		return shader;
	}
	
	public static Texture loadTextureFromFile(String fileName, boolean alpha) {

		Texture t;
		
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer comp = BufferUtils.createIntBuffer(1);
		
		ByteBuffer data = STBImage.stbi_load("./res/" + fileName, width, height, comp, 4);
		
		if(alpha) {
			t = new Texture(width, height, comp, data, GL11.GL_RGBA);
		} else {
			t = new Texture(width, height, comp, data, GL11.GL_RGB);
		}
		
		STBImage.stbi_image_free(data);
		
		return t;
	}
	
	public static Texture loadTextureFromArray(float[] data, int width, int height, boolean alpha) {

		Texture t;
		
		if(alpha) {
			t = new Texture(data, width, height, GL11.GL_RGBA);
		} else {
			t = new Texture(data, width, height, GL11.GL_RGB);
		}
		
		return t;
	}
	
}
