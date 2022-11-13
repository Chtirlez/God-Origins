package tile;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.UtilityTool;

public class TileManager {
	GamePanel gp;
public	Tile[] tile;
	public int mapTileNum[][]; //
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[1000];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/WORLD1.txt");
	}
	
	public void getTileImage() {
		setup(0, "plant", false);
		setup(1, "plant", true);
		setup(2, "plant", true);
		setup(3, "plant", false);
		setup(4, "plant", true);
		setup(5, "plant", false);
		setup(6, "plant", true);
		setup(7, "plant", true);
		setup(8, "plant", true);
		setup(9, "plant", true);
		
		
		setup(10, "sandMain", false);
		setup(11, "stone", true);
		setup(12, "water", true);
		setup(13, "earth", false);
		setup(14, "PalmMain", true);
		setup(15, "sandroad", false);
		
		
		//Market
		setup(16, "leftbottom", true);
		setup(17, "leftTop", true);
		setup(18, "rightTop", true);
		setup(19, "rightbottom", true);
		setup(20, "leftbottom2", true);
		setup(21, "leftTop2", true);
		setup(22, "midBottom", true);
		setup(23, "midTop", true);
		setup(24, "rightTop2", true);
		setup(25, "rightbottom2", true);
		
		//CANAL
		setup(26, "Canal/canal1", true);
		setup(27, "Canal/canal2", true);
		setup(28, "Canal/canal3", true);
		setup(29, "Canal/canal4", true);
		setup(30, "Canal/canal5", true);
		
		//Corners
		setup(31, "BigCorner/BigCorner_leftBottom", false);
		setup(32, "BigCorner/BigCorner_leftMiddle", false);
		setup(33, "BigCorner/BigCorner_leftTop", false);
		setup(34, "BigCorner/BigCorner_MiddleTop", false);
		setup(35, "BigCorner/BigCorner_rightTop", false);
		setup(36, "BigCorner/BigCorner_rightMiddle", false);
		setup(37, "BigCorner/BigCorner_rightBottom", false);
		setup(38, "BigCorner/BigCorner_MiddleBottom", false);
		
		//BUILDING
		setup(39, "Building/1", true);
		setup(40, "Building/2", true);
		setup(41, "Building/3", true);
		setup(42, "Building/4", true);
		setup(43, "Building/5", true);
		//
		setup(44, "Building/1_1", true);
		setup(45, "Building/1_2", true);
		setup(46, "Building/1_3", true);
		setup(47, "Building/1_4", true);
		setup(48, "Building/1_5", true);
		//
		setup(49, "Building/2_1", true);
		setup(50, "Building/2_2", true);
		setup(51, "Building/2_3", true);
		setup(52, "Building/2_4", true);
		setup(53, "Building/2_5", true);
		
		
		//River
		setup(54, "river/left", true);
		setup(55, "river/middle", true);
		setup(56, "river/right", true);
		//bridge
		setup(57, "bridge/left", false);
		setup(58, "bridge/middle", false);
		setup(59, "bridge/right", false);
		/* try { */
			/*
			 * tile[0] = new Tile(); tile[0].image =
			 * ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			 * 
			 * 
			 * tile[1] = new Tile(); tile[1].image =
			 * ImageIO.read(getClass().getResourceAsStream("/tiles/sandstone.png"));
			 * tile[1].collision = true;
			 * 
			 * tile[2] = new Tile(); tile[2].image =
			 * ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			 * tile[2].collision = true;
			 * 
			 * tile[3] = new Tile(); tile[3].image =
			 * ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			 * 
			 * 
			 * tile[4] = new Tile(); tile[4].image =
			 * ImageIO.read(getClass().getResourceAsStream("/tiles/palm1.png"));
			 * tile[4].collision = true;
			 * 
			 * tile[5] = new Tile(); tile[5].image =
			 * ImageIO.read(getClass().getResourceAsStream("/tiles/plant.png"));
			 */
			
			
			
		/*}catch(IOException e) {
			e.printStackTrace();
		}*/
		
	}
	
	
	public void setup(int index, String imageName, boolean collision) {
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName +".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void loadMap(String filePath) {
		
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int worldCol = 0;
			int worldRow = 0;
			
			while(worldCol < gp.maxWorldCol && worldRow  < gp.maxWorldRow) {
				String line  = br.readLine();
				
				while(worldCol < gp.maxWorldCol) {
					String numbers[] = line.split(" ");//Splits this string around match of given regular expression
					
					int num = Integer.parseInt(numbers[worldCol]); //Use col as an index for numbers[] array
				
					mapTileNum[worldCol][worldRow]= num;//Store the extracted number in the mapTileNum
					worldCol++;
					//Continue this until everything in the numbers is stored in the mapTileNum
				}
				if(worldCol == gp.maxWorldCol) {
					worldCol = 0;
					worldRow++;
				}
				
			}
			br.close();
			
		}
		catch(Exception e) {
			
		}
	}
	
	public void draw(Graphics g2) {
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			//Extract a tile number which is stored in mapTileNum[][]
			int tileNum = mapTileNum[worldCol][worldRow]; //The map data has been stored in the mapTileNum[][]
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize>gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize <gp.player.worldY + gp.player.screenY) {
				
			g2.drawImage(tile[tileNum].image, screenX, screenY,  null);
															//  | here has to be gp.tileSize, gp.tileSize, for previous version
			}
			
			worldCol++;	
			
			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				
				worldRow++;
				
			}
		}
	}
	
	
}
