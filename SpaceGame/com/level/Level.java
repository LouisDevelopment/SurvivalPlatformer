package com.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joml.Vector3f;

import com.entities.item.ItemStack;
import com.entities.mob.Player;
import com.entities.particles.Particle;
import com.entities.particles.ParticleSpawner;
import com.gfx.SpriteRenderer;
import com.level.tile.MultiTile;
import com.level.tile.Tile;
import com.main.CreateGame;
import com.main.Game;
import com.util.Noise;

public class Level {

	public int type, width, height;
	public float gravity = 0.3f;
	public int time = 0, dayCycle = 0, dayLength = 50000;
	public boolean caves = true;
	public float[][] caveData;
	
	public List<ItemStack> items = new ArrayList<ItemStack>();
	public List<Particle> particles = new ArrayList<Particle>();
	public List<Player> players = new ArrayList<Player>();
	//public List<Liquid> liquids = new ArrayList<Liquid>();

	public int[][] liquidLayer;
	public int[][] background;
	public int[][] mainLayer;
	public boolean[][] discovered;
	
	public int[][] lightLevel;
	public int[][] ambientLight;
	
	public Random r = new Random();
	
	private boolean levelFlipped = false;
	
	public static Level test = new TestLevel(30, 24);
	public static Level random = new RandomLevel(4000, 528);
	
	public static int backgroundColour = 0;
	public int defaultBackgroundColour = 0;
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		
		background = new int[height][width];
		liquidLayer = new int[height][width];
		mainLayer = new int[height][width];
		lightLevel = new int[height][width];
		ambientLight = new int[height][width];
		
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				background[y][x] = 0;
				liquidLayer[y][x] = 0;
				mainLayer[y][x] = 0;
				lightLevel[y][x] = 0;
				ambientLight[y][x] = 0;
			}
		}
		discovered = new boolean[width][height];
		
	}
	
	public void tick() {
		for(int i = 0; i < items.size(); i++) {
			items.get(i).tick();
		}
		
		increaseTime();

		for(int i = 0; i < players.size(); i++) {
			players.get(i).tick();
		}
		
		//l.tick();
		
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).tick();
		}
		//for(int i = 0; i < liquids.size(); i++) {
		//	if(time%liquids.get(i).flowRate == 0) {
		//		System.out.println("working");
		//		liquids.get(i).updateFlow();
		//	}
		//}
		
		/*if(players.get(0).x/16 < 100) {
			if(!levelFlipped) {
				int[][] tempLevel = mainLayer;
				int[][] tempLiquid = liquidLayer;
				int[][] tempBackground = background;
				int[][] tempLight = lightLevel;
				for(int x = 0; x < width; x++) {
					for(int y = 0; y < height; y++) {
						if(x < width/2) {
							tempLevel[y][x] = mainLayer[y][x + width/2];
							tempLiquid[y][x] = liquidLayer[y][x + width/2];
							tempBackground[y][x] = background[y][x + width/2];
							tempLight[y][x] = lightLevel[y][x + width/2];
						} else {
							tempLevel[y][x] = mainLayer[y][x - width/2];
							tempLiquid[y][x] = liquidLayer[y][x - width/2];
							tempBackground[y][x] = background[y][x - width/2];
							tempLight[y][x] = lightLevel[y][x - width/2];
						}
					}
				}
				players.get(0).setX(players.get(0).x + width/2*16);
				mainLayer = tempLevel;
				liquidLayer = tempLiquid;
				background = tempBackground;
				levelFlipped = true;
			}
		} else if(players.get(0).x/16 > width - 100) {
			if(!levelFlipped) {
				int[][] tempLevel = mainLayer;
				int[][] tempLiquid = liquidLayer;
				int[][] tempBackground = background;
				for(int x = 0; x < width; x++) {
					for(int y = 0; y < height; y++) {
						if(x < width/2) {
							tempLevel[y][x] = mainLayer[y][x + width/2];
							tempLiquid[y][x] = liquidLayer[y][x + width/2];
							tempBackground[y][x] = background[y][x + width/2];
						} else {
							tempLevel[y][x] = mainLayer[y][x - width/2];
							tempLiquid[y][x] = liquidLayer[y][x - width/2];
							tempBackground[y][x] = background[y][x - width/2];
						}
					}
				}
				players.get(0).setX(players.get(0).x - width/2*16);
				mainLayer = tempLevel;
				liquidLayer = tempLiquid;
				background = tempBackground;
				levelFlipped = true;
			}
		} else {
			levelFlipped = false;
		}*/
		
		
	}
	//42a4f5
	int colour = 0xff070B24;
	public void increaseTime() {
		time += 1;
		
		if(time%10 <= 0) {
			if(time < dayLength/2) {
				if(Game.r > 0.0f) {
					Game.r -= 0.001f;
				}
				if(Game.g > 0.0f) {
					Game.g -= 0.002f;
				}
				if(Game.b > 0.1f) {
					Game.b -= 0.00175f;
				}
			} else {
				if(Game.r < 0.3f) {
					Game.r += 0.001f;
				}
				if(Game.g < 0.62f) {
					Game.g += 0.002f;
				}
				if(Game.b < 0.9f) {
					Game.b += 0.00175f;
				}
			}
		}
		
		if(time == dayLength) {
			dayCycle++;
			time = 0;
		}
	}
	
	protected void generate(int[][] thisLevel) {
		mainLayer = thisLevel;
	}

	public void render(SpriteRenderer render, float xScroll, float yScroll) {
		
		drawTilesAndPlayer(xScroll, yScroll, render);
		for(int i = 0; i < items.size(); i++) {
			items.get(i).render(render);
		}
		renderLights();

		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).render(render);
		}
		//for(int i = 0; i < liquids.size(); i++) {
		//	liquids.get(i).render();
		//}
		
		for(int i = 0; i < players.size(); i++) {
			players.get(i).renderAboveLevel();

			players.get(i).renderUI(render);
		}
		
	}
	
	public void renderLights() {
		//l.render();
		
	}
	
	private void drawTilesAndPlayer(float xScroll, float yScroll, SpriteRenderer render){
		int x0 = (int)(xScroll-Game.tileSize) >> 4;
		int x1 = (int)((xScroll + CreateGame.WIDTH + Game.tileSize)) >> 4;
		int y0 = (int)(yScroll-Game.tileSize) >> 4;
		int y1 = (int)((yScroll + CreateGame.HEIGHT + Game.tileSize)) >> 4;

		for(int x = x0; x < x1; x++) {
			for(int y = y0; y < y1; y++) {
				if(x > 0 && x < width && y > 0 && y < height) {
					
					float distFromPlayer = (float)((Math.sqrt(((players.get(0).x+8)/16 - x)*((players.get(0).x+8)/16 - x) + ((players.get(0).y+8)/16 - y)*((players.get(0).y+8)/16 - y))));		
					int edge = isEdgeTile(x, y);
					Tile tile0 = getTile(x, y, 1);
					Tile tile1 = getTile(x, y, -1);
						if(tile0.id <= 0 || edge != 0 || tile0.collision == false  && tile1 != Tile.voidTile) {
							if(distFromPlayer < Game.viewDistance) {
								if((Game.viewDistance-distFromPlayer)/Game.viewDistance * 8.0f > ambientLight[y][x]) {
									tile1.render(x, y, render, (Game.viewDistance-distFromPlayer)/Game.viewDistance * 8.0f, -1);
								} else {
									tile1.render(x, y, render, ambientLight[y][x], -1);
								}
							} else {
								tile1.render(x, y, render, ambientLight[y][x], -1);
							}
						}
						
						if(tile0.id != 0) {
							
						}
						
						if(getTile(x, y, 1) == Tile.torch) {
							
							if(lightLevel[y][x] != 8) {
								lightLevel[y][x] = 8;
								for(int yI = -8; yI < 8; yI++) {
									for(int xI = -8; xI < 8; xI++) {
										float dist = (float)((Math.sqrt(((x+8)/16 - xI)*((x+8)/16 - xI) + ((y+8)/16 - yI)*((y+8)/16 - yI))));	
										if(lightLevel[y+yI][x+xI] < (int) ((8.0f-dist)/8.0f * 8.0f)) {
											lightLevel[y+yI][x+xI] = (int) ((8.0f-dist)/8.0f * 8.0f);
										}
									}
								}
							}
						}
						
						if(getLightLevel(x, y) < 8) {
							int l = getLightLevel(x, y);
							if(getLightLevel(x-1, y) > l) {
								l = getLightLevel(x-1, y)-1;
							}
							if(getLightLevel(x+1, y) > l) {
								l = getLightLevel(x+1, y)-1;
							}
							if(getLightLevel(x, y-1) > l) {
								l = getLightLevel(x, y-1)-1;
							}
							if(getLightLevel(x, y+1) > l) {
								l = getLightLevel(x, y+1)-1;
							}
							ambientLight[y][x] = l;
						}
				}
			}
		}
		
		for(int i = 0; i < players.size(); i++) {
			players.get(i).render(render);
		}
		
		for(int x = x0; x < x1; x++) {
			for(int y = y0; y < y1; y++) {
				if(x > 0 && x < width && y > 0 && y < height) {
					float distFromPlayer = (float)((Math.sqrt(((players.get(0).x+8)/16 - x)*((players.get(0).x+8)/16 - x) + ((players.get(0).y+8)/16 - y)*((players.get(0).y+8)/16 - y))));		
				
					Tile tile0 = getTile(x, y, 1);
					if(tile0.id > 0) {
						if(distFromPlayer < Game.viewDistance) {
							if((Game.viewDistance-distFromPlayer)/Game.viewDistance * 8.0f > ambientLight[y][x]) {
								tile0.render(x, y, render, (Game.viewDistance-distFromPlayer)/Game.viewDistance * 8.0f, 1);
							} else {
								tile0.render(x, y, render, ambientLight[y][x], 1);
								
							}
						} else {
							tile0.render(x, y, render, ambientLight[y][x], 1);
						}
					}
					
				}
			}
		}
		
	}
	
	public Tile getTile(int x, int y, int layer) {
		if(x >= 0 && x < mainLayer[0].length && y >= 0 && y < mainLayer.length) {
			for(int i = 0; i < Tile.tiles.size(); i++) {
				if(layer == 1) {
					if(mainLayer[y][x] == Tile.tiles.get(i).id) {
						return Tile.tiles.get(i);
					}
				} else if(layer == 0) {
					if(liquidLayer[y][x] == Tile.tiles.get(i).id) {
						return Tile.tiles.get(i);
					}
				} else if(layer == -1) {
					if(background[y][x] == Tile.tiles.get(i).id) {
						return Tile.tiles.get(i);
					}
				}
			}
		}
		return Tile.voidTile;
	}
	
	public void setTile(int x, int y, int type) {
		mainLayer[y][x] = type;
	}
	
	public void setMultiTile(int x, int y, int type) {
		MultiTile t = (MultiTile) Tile.getTileFromID(type);
		for(int y1 = 0; y < t.structureSize.y; y++) {
			for(int x1 = 0; x < t.structureSize.x; x++) {
				if(mainLayer[y+y1][x+x1] == 0) {
					mainLayer[y+y1][x+x1] = -type;
				}
			}
		}
		mainLayer[y+1][x+1] = type;
	}
	
	public void setLiquid(int x, int y, int type) {
		liquidLayer[y][x] = type;
	}
	
	public void setBackground(int x, int y, int type) {
		background[y][x] = type;
	}
	
	public Vector3f getTileColour(Tile type) {
		return type.color;
	}
	
	public void addItem(ItemStack e) {
		items.add(e);
	}
	
	public int isEdgeTile(int x, int y) {
		boolean left = false, right = false, up = false, down = false;
		if(Game.level.getTile(x-1, y, 1).id < 1) left = true;
		if(Game.level.getTile(x+1, y, 1).id < 1) right = true;
		if(Game.level.getTile(x, y-1, 1).id < 1) up = true;
		if(Game.level.getTile(x, y+1, 1).id < 1) down = true;
		
		if(up && down && right && left){
			return 13;
		} else if(left&&up&&down) {
			return 9;
		} else if(left && up && right){
			return 11;
		} else if(left && down && right){
			return 12;
		} else if(up && down && right){
			return 10;
		} else if(left&&up) {
			return 5;
		} else if(right&&up) {
			return 6;
		} else if(left&&down) {
			return 7;
		} else if(right&&down) {
			return 8;
		} else if(left) {
			return 1;
		} else if(right) {
			return 3;
		} else if(up) {
			return 2;
		} else if(down) {
			return 4;
		} else {
			return 0;
		}
	}
	
	public int getTileColour(int type) {
		if(type == 0) {
			return backgroundColour;
		} else if(type == Tile.grass.getID()) {
			return 0xffffff;
		} else if(type == Tile.dirt.getID()) {
			return 0x964B00;
		} else if(type == Tile.dirtBack.getID()) {
			return 0x762B00;
		} else if(type == Tile.stone.getID()) {
			return 0xaaaaaa;
		} else if(type == Tile.stoneBack.getID()) {
			return 0x484848;
		} else if(type == Tile.log.getID()) {
			return 0xf664417;
		} else if(type == Tile.leaves.getID()) {
			return 0x2D7A32;
		} else if(type == Tile.ironOre.getID()) {
			return 0xFFDBCC;
		} else if(type == Tile.coalOre.getID()) {
			return 0x1C1D1E;
		} else if(type == Tile.uraniumOre.getID()) {
			return 0x0D9910;
		} else if(type == Tile.torch.getID()) {
			return 0xffff00;
		} else if(type == Tile.longGrass.getID()) {
				return 0x629632;
			}
		
		return backgroundColour;
	}
	
	public boolean checkForStack(int tileX, int tileY) {
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).x == tileX && items.get(i).y == tileY) {
				return true;
			}
		}
		
		return false;
	}
	
	public ItemStack getStack(int tileX, int tileY) {
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).x == tileX && items.get(i).y == tileY) {
				return items.get(i);
			}
		}
		return null;
	}
	
	public void removeItem(int tileX, int tileY) {
		boolean done = false;
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i).x == tileX && items.get(i).x == tileX && !done) {
				items.remove(i);
				done = true;
			}
		}
	}
	
	public void addPlayer(Player p) {
		players.add(p);
	}
	
	//public void addLiquid(Liquid l) {
	//	liquids.add(l);
	//}
	
	//public void removeLiquid(Liquid l) {
	//	liquids.add(l);
	//}
	
	public int getLightLevel(int x, int y) {
		if(x < width && x >= 0 && y >= 0 && y < height) {
			return ambientLight[y][x];
		}
		return -1;
	}
	
	public void generateCaves(int offset) {
		caveData = Noise.generateOctavedSimplexNoise(width, height - offset, 5, 0.5f, 0.03f);
		for(int x = 0; x < caveData.length-1; x++) {
			for(int y = 0; y < caveData[0].length-1; y++) {
				if(caveData[x][y] > 0.3f) {
					caveData[x][y] = 1f;
				} else {
					caveData[x][y] = 0f;
				}
				if(caveData[x][y] == 1f) {
					if(getTile(x, y + offset, 1) != Tile.voidTile) {
						setBackground(x, y + offset, -4);
						setTile(x, y + offset, 0);
					}
				}
			}
		}
		generateCaveAmbiance();
		
	}
	
	public void generateCaveAmbiance() {
		for(int x = 0; x < caveData.length-1; x++) {
			for(int y = 0; y < caveData[0].length-1; y++) {
				if(r.nextInt(15) == 11) {
					if(getTile(x, y, 1) == Tile.voidTile) {
						if(getTile(x, y, -1) == Tile.stoneBack) {
							if(getTile(x, y+1, 1) == Tile.stone) {
								setTile(x, y-1, Tile.spike.id);
							}
						}
					}
				}
			}
		}
	}
	
	public void generateOres() {
		for(int x = 0; x < mainLayer.length; x++) {
			for(int y = 0; y < mainLayer[0].length; y++) {
				if(x > 36) {
					if(mainLayer[x][y] == 4) {
						int chance = r.nextInt(1048);
						if(chance == 4 || chance == 5) {
							int size = r.nextInt(8) + 2;
							if(x < mainLayer.length - size && y < mainLayer[0].length - size) {
								for(int i = 0; i < size; i++) {
									int move = r.nextInt(10);
									if(move == 0 && getTile((int) (x+(i%(Math.sqrt(size))))-1, ((int) (y+(i/(Math.sqrt(size))))), 1) == Tile.stone) {
										mainLayer[(int) (x+(i%(Math.sqrt(size))))-1][(int) (y+(i/(Math.sqrt(size))))] = Tile.ironOre.id;
									} else if(move == 1 &&  getTile((int) (x+(i%(Math.sqrt(size))))+1,(int) (y+(i/(Math.sqrt(size)))), 1) == Tile.stone) {
										mainLayer[(int) (x+(i%(Math.sqrt(size))))+1][(int) (y+(i/(Math.sqrt(size))))] = Tile.ironOre.id;
									} else if(move == 2 && getTile((int) (x+(i%(Math.sqrt(size)))), (int) (y+(i/(Math.sqrt(size)))) - 1, 1) == Tile.stone) {
										mainLayer[(int) (x+(i%(Math.sqrt(size))))][(int) (y+(i/(Math.sqrt(size)))) - 1] = Tile.ironOre.id;
									} else if(move == 3 && getTile((int) (x+(i%(Math.sqrt(size)))), (int) (y+(i/(Math.sqrt(size)))) + 1, 1) == Tile.stone) {
										mainLayer[(int) (x+(i%(Math.sqrt(size))))][(int) (y+(i/(Math.sqrt(size)))) + 1] = Tile.ironOre.id;
									} else mainLayer[(int) (x+(i%(Math.sqrt(size))))][(int) (y+(i/(Math.sqrt(size))))] = Tile.ironOre.id;
								}
							}
						} else if(chance == 6 || chance == 7 || chance == 8) {
							int size = r.nextInt(16) + 2;
							if(x < mainLayer.length - size && y < mainLayer[0].length - size) {
								for(int i = 0; i < size; i++) {
									int move = r.nextInt(6);
									if(move == 0 && getTile((int) (x+(i%(Math.sqrt(size))))-1, ((int) (y+(i/(Math.sqrt(size))))), 1) == Tile.stone) {
										mainLayer[(int) (x+(i%(Math.sqrt(size))))-1][(int) (y+(i/(Math.sqrt(size))))] = Tile.coalOre.id;
									} else if(move == 1 && getTile((int) (x+(i%(Math.sqrt(size))))+1,(int) (y+(i/(Math.sqrt(size)))), 1) == Tile.stone) {
										mainLayer[(int) (x+(i%(Math.sqrt(size))))+1][(int) (y+(i/(Math.sqrt(size))))] = Tile.coalOre.id;
									} else if(move == 2 && getTile((int) (x+(i%(Math.sqrt(size)))), (int) (y+(i/(Math.sqrt(size)))) - 1, 1) == Tile.stone) {
										mainLayer[(int) (x+(i%(Math.sqrt(size))))][(int) (y+(i/(Math.sqrt(size)))) - 1] = Tile.coalOre.id;
									} else if(move == 3 && getTile((int) (x+(i%(Math.sqrt(size)))), (int) (y+(i/(Math.sqrt(size)))) + 1, 1) == Tile.stone) {
										mainLayer[(int) (x+(i%(Math.sqrt(size))))][(int) (y+(i/(Math.sqrt(size)))) + 1] = Tile.coalOre.id;
									} else mainLayer[(int) (x+(i%(Math.sqrt(size))))][(int) (y+(i/(Math.sqrt(size))))] = Tile.coalOre.id;
								}
							}
						} else if(chance == 9) {
							if(x > 150) {
								int size = r.nextInt(16) + 2;
								if(x < mainLayer.length - size && y < mainLayer[0].length - size) {
									for(int i = 0; i < size; i++) {
										int move = r.nextInt(6);
										if(move == 0 && getTile((int) (x+(i%(Math.sqrt(size))))-1, ((int) (y+(i/(Math.sqrt(size))))), 1) == Tile.stone) {
											mainLayer[(int) (x+(i%(Math.sqrt(size))))-1][(int) (y+(i/(Math.sqrt(size))))] = Tile.uraniumOre.id;
										} else if(move == 1 && getTile((int) (x+(i%(Math.sqrt(size))))+1,(int) (y+(i/(Math.sqrt(size)))), 1) == Tile.stone) {
											mainLayer[(int) (x+(i%(Math.sqrt(size))))+1][(int) (y+(i/(Math.sqrt(size))))] = Tile.uraniumOre.id;
										} else if(move == 2 && getTile((int) (x+(i%(Math.sqrt(size)))), (int) (y+(i/(Math.sqrt(size)))) - 1, 1) == Tile.stone) {
											mainLayer[(int) (x+(i%(Math.sqrt(size))))][(int) (y+(i/(Math.sqrt(size)))) - 1] = Tile.uraniumOre.id;
										} else if(move == 3 && getTile((int) (x+(i%(Math.sqrt(size)))), (int) (y+(i/(Math.sqrt(size)))) + 1, 1) == Tile.stone) {
											mainLayer[(int) (x+(i%(Math.sqrt(size))))][(int) (y+(i/(Math.sqrt(size)))) + 1] = Tile.uraniumOre.id;
										} else mainLayer[(int) (x+(i%(Math.sqrt(size))))][(int) (y+(i/(Math.sqrt(size))))] = Tile.uraniumOre.id;
									}
								}
							}
						} else if(chance == 18) {
							if(x > 40) {
								int size = r.nextInt(30) + 14;
								if(x < mainLayer.length - size && y < mainLayer[0].length - size) {
									for(int i = 0; i < size; i++) {
										int move = r.nextInt(6);
										if(move == 0 && getTile((int) (x+(i%(Math.sqrt(size))))-1, ((int) (y+(i/(Math.sqrt(size))))), 1) == Tile.stone) {
											mainLayer[(int) (x+(i%(Math.sqrt(size))))-1][(int) (y+(i/(Math.sqrt(size))))] = Tile.dirt.id;
										} else if(move == 1 && getTile((int) (x+(i%(Math.sqrt(size))))+1,(int) (y+(i/(Math.sqrt(size)))), 1) == Tile.stone) {
											mainLayer[(int) (x+(i%(Math.sqrt(size))))+1][(int) (y+(i/(Math.sqrt(size))))] = Tile.dirt.id;
										} else if(move == 2 && getTile((int) (x+(i%(Math.sqrt(size)))), (int) (y+(i/(Math.sqrt(size)))) - 1, 1) == Tile.stone) {
											mainLayer[(int) (x+(i%(Math.sqrt(size))))][(int) (y+(i/(Math.sqrt(size)))) - 1] = Tile.dirt.id;
										} else if(move == 3 && getTile((int) (x+(i%(Math.sqrt(size)))), (int) (y+(i/(Math.sqrt(size)))) + 1, 1) == Tile.stone) {
											mainLayer[(int) (x+(i%(Math.sqrt(size))))][(int) (y+(i/(Math.sqrt(size)))) + 1] = Tile.dirt.id;
										} else mainLayer[(int) (x+(i%(Math.sqrt(size))))][(int) (y+(i/(Math.sqrt(size))))] = Tile.dirt.id;
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void generateTrees(int chance) {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width-8; x++) {
				if(mainLayer[y][x] == 1) {
					int a = r.nextInt(chance);
					
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
	
	
	public void destroyTree(int x, int y) {
		int size = 0;
		for(int i = 0; i < 16; i++) {
			if(Game.level.getTile(x, y-i, 1).id == 40 || Game.level.getTile(x, y-i, 1).id == 41) {
				size++;
			}
		}
		
		ItemStack e = new ItemStack(x, y, Tile.log.drop);
		Game.level.addItem(e);

		ParticleSpawner particle = new ParticleSpawner(x*16 + 8, y*16, 30, 20, Game.level.getTileColour(Game.level.getTile(x, y, 1)));
		Game.level.setTile(x, y, -40);
		Game.level.getStack(x, y).addItem(size);
		
		if(size > 1) {
			destroyTree(x, y-1);
		} else {
		}

	}
	public void addParticle(Particle particle) {
		particles.add(particle);
	}
	
	public void removeParticle(Particle particle) {
		particles.remove(particle);
	}
	
	
	
}