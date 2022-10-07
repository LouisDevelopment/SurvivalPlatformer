package com.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.level.tile.Tile;
import com.util.Noise;

public class RandomLevel extends Level{

	public Random r = new Random();
	
	double xFreq = 0.1;
	double multiplier = 20;
	int offset = 50;
	float[][] noise;
	
	public RandomLevel(int width, int height) {
		super(width, height);
		
		
		mainLayer = new int[height][width];
		noise = new float[width][0];
		noise = Noise.generateOctavedSimplexNoise(width, 1, 5, 0.4f, 0.01f);
		createCurve();
		generateBiomes();
		generateCaves(offset + 20);
		generateOres();
		generateTrees(100);
		
	}
	
	public void createCurve() {	
		
		for(int x = 0; x < width; x++) {
			int val = offset;
			noise[x][0] *= 15;
			val += noise[x][0];
			
			if(val < height && val > 0) {
			mainLayer[val][x] = Tile.grass.id;
			background[val][x] = -Tile.dirt.id;
			ambientLight[val][x] = 7;
				
				int var = r.nextInt(2) + 6;
				for(int i = 0; i < height; i++) {
					if(i <= val) {
						ambientLight[i][x] = 8;
					}
					if(mainLayer[i][(x)] != 1) {
						if(i < var+val && i > val) {
							mainLayer[i][x] = Tile.dirt.id;
							background[i][x] = -Tile.dirt.id;
						} else if(i > val){
							mainLayer[i][x] = Tile.stone.id;
							background[i][x] = -Tile.stone.id;
						}
					}
				}
			}
		}
		
		for(double x = 1; x < width; x++) {
			for(int y = 1; y < height-1; y++) {
				if(mainLayer[y-1][(int)(x)] == Tile.grass.id || mainLayer[y-1][(int)x] == Tile.dirt.id) {
					if(mainLayer[y+1][(int)(x)] != 4) {
						mainLayer[y][(int)(x)] = Tile.dirt.id;
					}
				}
				if(mainLayer[y][(int)(x)] == Tile.grass.id) {
					if(r.nextInt(6) == 1) {
						mainLayer[y-1][(int)(x)] = Tile.longGrass.id;
					} else if(r.nextInt(12) == 2) {
						mainLayer[y-1][(int)(x)] = Tile.rock.id;
					}
				}
			}	
		}
		
		for(double x = 1; x < (width/10)/xFreq; x++) {
			for(int y = 1; y < height-1; y++) {
				if(mainLayer[y-1][(int)(x)] == Tile.grass.id || mainLayer[y-1][(int)x] == Tile.dirt.id) {
					if(mainLayer[y+1][(int)(x)] != 4) {
						mainLayer[y][(int)(x)] = Tile.dirt.id;
					}
				}
				if(mainLayer[y][(int)(x)] == 1) {
					if(r.nextInt(6) == 1) {
						mainLayer[y-1][(int)(x)] = Tile.longGrass.id;
					} else if(r.nextInt(6) == 2) {
						mainLayer[y-1][(int)(x)] = Tile.rock.id;
					}
				}
			}	
		}
	}
	
	public void generateBiomes() {
		int startX = 0;
		for(int i = 0; i < width / 156; i++) {
			int size = r.nextInt(128) + 64;
			int type = r.nextInt(4);
			
			//meadows
			//forest
			//desert
			//hills
			if(type == 0) {
				
			} else if(type == 1) {
				createForest(size, startX);
			} else if(type == 2) {
				createDesert(size, startX);
			} else if(type == 3) {
				
			}
			
			startX += size;
		}
		
	}
	
	public void createForest(int size, int startX) {
		for(int y = 0; y < height; y++) {
			for(int x = startX; x < startX+size-8; x++) {
				if(mainLayer[y][x] == 1) {
					int a = r.nextInt(9);
					
					if(a == 0) {
						int b = r.nextInt(4)+8;
						
						mainLayer[y][x] = 39;
						for(int i = 0; i < b; i++) {
							if(y-1-i > 0) {
								mainLayer[y-1-i][x] = Tile.log.id;
								if(i > b-2) {
									for(int j = -1; j < 2; j++) {
										if(y-1-i >= 0 && y-1-i < height && x+j >= 0 && x+j < width) {
											mainLayer[y-1-i][x+j] = Tile.leaves.id;
										}
									}
								} else if(i > b-5) {
									for(int j = -3; j < 4; j++) {
										if(y-1-i >= 0 && y-1-i < height && x+j >= 0 && x+j < width) {
											if(j != 0 && x+j < width) {
												mainLayer[y-1-i][x+j] = Tile.leaves.id;
											} else mainLayer[y-1-i][x+j] = Tile.logWithLeaves.id;
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void createDesert(int size, int startX) {
		for(int y = 0; y < height; y++) {
			for(int x = startX; x < startX+size; x++) {
				if(mainLayer[y][x] == 1 || mainLayer[y][x] == 3) {
					mainLayer[y][x] = 5;
				} else if(mainLayer[y][x] == Tile.longGrass.id) {
					mainLayer[y][x] = 106;
				}
			}
		}
	}
	
	public void createHills(int size, int startX) {
		float[][] newNoise = Noise.generateOctavedSimplexNoise(size, 1, 5, 0.4f, 0.01f);
		for(int i = 0; i < size; i++) {
			float distFromCenter = (float)Math.sqrt((size/2 - i) * (size/2 - i));
			
			newNoise[i][0] -= distFromCenter/size;
			newNoise[i][0] *= 4;
		}

		for(int x = startX; x < startX + size; x++) {
			for(int y = 0; y < height; y++) {
				if(mainLayer[y][x] == 1 || mainLayer[y][x] == 3) {
					mainLayer[y][x] = 4;
					background[y][x] = -4;
				}
			}
			
			int val = offset;
			if(newNoise[x-startX][0] > 0) {
				val += newNoise[x-startX][0] * 60;
			} else {
				val += newNoise[x-startX][0] * 10;
			}
			if(val > 0 && val < height) {
				mainLayer[val][x] = 1;
			}

			int var = r.nextInt(2) + 6;
			for(int y = 0; y < height; y++) {
				if(y <= val) {
					lightLevel[y][x] = 8;
				}
				if(mainLayer[y][(x)] != 1) {
					if(y < var+val && y > val) {
						mainLayer[y][x] = Tile.dirt.id;
						background[y][x] = -Tile.dirt.id;
					} else if(y > val){
						mainLayer[y][x] = 4;
						background[y][x] = -4;
					}
				}
			}
		}
		
		
		
	}

}
