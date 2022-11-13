package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.KeyHandler;
import Main.UtilityTool;

//Entity class
//This stores variables that used in player, monster and NPC classes
public class Player extends Entity{
	//GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	public int hasKey = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp); //need after create constructor in Entity.java
		
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2- (gp.tileSize/2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		solidArea.width = 32;
		solidArea.height = 32;
		
		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() {
		worldX =gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed=4;
		direction = "down";
	}
	
	//WAS in getPlayerImage()
	/*
	 * try { up1 =
	 * ImageIO.read(getClass().getResourceAsStream("/player/anubis_up1.png")); up2 =
	 * ImageIO.read(getClass().getResourceAsStream("/player/anubis_up2.png")); down1
	 * = ImageIO.read(getClass().getResourceAsStream("/player/anubis_walk1.png"));
	 * down2 =
	 * ImageIO.read(getClass().getResourceAsStream("/player/anubis_walk2.png"));
	 * left1 =
	 * ImageIO.read(getClass().getResourceAsStream("/player/anubis_left.png"));
	 * left2 =
	 * ImageIO.read(getClass().getResourceAsStream("/player/walk_left.png")); right1
	 * = ImageIO.read(getClass().getResourceAsStream("/player/anubis_right.png"));
	 * right2 =
	 * ImageIO.read(getClass().getResourceAsStream("/player/walk_right.png")); }
	 * catch(IOException e) { e.printStackTrace(); }
	 */
	public void getPlayerImage() {
	
		
		up1 = setup("/player/up1");
		up2 = setup("/player/u2");
		down1= setup("/player/down1");
		down2 = setup("/player/down2");
		left1 = setup("/player/left1");
		left2 = setup("/player/left2");
		right1 = setup("/player/right1");
		right2 = setup("/player/right2");
	}
	
	
	
	
	public void update() {
		if(keyH.upPressed ==true || keyH.downPressed ==true ||
				keyH.leftPressed ==true|| keyH.rightPressed ==true) {
			if(keyH.upPressed ==true) {
				direction ="up";
				
			}
			else if(keyH.downPressed == true) {
				direction = "down";
				
			}
			else if(keyH.leftPressed == true) {
				direction = "left";
				
			}
			else if(keyH.rightPressed == true) {
				direction ="right";
				
			}
			
			//CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			//CHECK  OBJECT COLLECTION
			int objIndex =	gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			
			//CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			//IF COLLISION IS FALSE, PLAYER CAN MOVE
			
			if(collisionOn == false) {
				switch(direction) {
				case "up":
					worldY -= speed;
					break;
				case"down":
					worldY += speed;
					break;
				case"left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}

			spriteCounter++;
			if(spriteCounter>12) {
				if(spriteNum == 1) {
					spriteNum =2;
				}
				else if(spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
		
	}
	
	public void pickUpObject(int i) {
		
		if(i!= 999) {
			
			  String objectName=gp.obj[i].name;
			  
			  switch(objectName) { case "Key": gp.playSE(1); hasKey++; gp.obj[i] = null;
			  gp.ui.showMessage("You got a key!"); System.out.println("Key:" +hasKey);
			  break; case "Door": if(hasKey > 0) { gp.playSE(2); gp.obj[i] = null;
			  gp.ui.showMessage("You opened the door!"); hasKey--; } else {
			  gp.ui.showMessage("You need a key!"); } System.out.println("Key;" +hasKey);
			  break; case "Boots": gp.playSE(1); speed+= 2; gp.obj[i] = null;
			  gp.ui.showMessage("Speed Up!"); break;
			  
			  case "Chest": gp.ui.gameFinished = true; gp.stopMusic(); gp.playSE(3); break;
			  }
			 
		}
	}
	
	
	
	public void interactNPC(int i) {
		
		if(i != 999) {
			if(gp.keyH.enterPressed == true) {
				gp.gameState = gp.dialogState;
				gp.npc[i].speak();
			}
			//System.out.println("you are hitting an npc!");
		
		}
		gp.keyH.enterPressed = false;
	}
	
	
	
	
	public void draw(Graphics2D g2) {
		/*
		 * g2.setColor(Color.white);
		 * 
		 * g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		 */
		
		BufferedImage image = null;
		switch(direction) {
		case "up":
			if(spriteNum ==1) {
				image = up1;
			}
			if(spriteNum ==2) {
				image = up2;
			}
			break;
		case "down":
			if(spriteNum ==1) {
				image = down1;
			}
			if(spriteNum ==2) {
				image = down2;
			}
			break;
		case "left":
			if(spriteNum ==1) {
				image = left1;
			}
			if(spriteNum ==2) {
				image = left2;
			}
			break;
		case "right":
			if(spriteNum ==1) {
				image = right1;
			}
			if(spriteNum ==2) {
				image = right2;
			}
			break;
		}
		g2.drawImage(image, screenX, screenY,  null);
	}									//   | gp.tileSize, gp.tileSize,
}
