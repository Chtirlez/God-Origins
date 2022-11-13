package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.UtilityTool;

public class NPC_QuestMan extends Entity{

	public NPC_QuestMan(GamePanel gp) {
		super(gp);
		direction = "down";
		speed = 1;
		getImage();
		setDialogue();
	}
public void getImage() {
		up1 = setup("/npc/up1");
		up2 = setup("/npc/up2");
		down1= setup("/npc/down1");
		down2 = setup("/npc/down2");
		left1 = setup("/npc/left");
		left2 = setup("/npc/left2");
		right1 = setup("/npc/right");
		right2 = setup("/npc/right2");
	}


public void setDialogue() {
	dialogues[0] ="OH HEAVEN! Ra sent to us you, God of the \nUnderworld,  God of Erebus! Anubis!";
	  dialogues[1]
	  ="Monsters from the Dark Deserts \n are killing our people, destroying our harvest!";
	  dialogues[2] ="Help us, omnipotent!";
	  dialogues[3] ="Help us, Ubiquitous!";
	 
	  
}


	public void setAction() {
		
		actionLockCounter++;
		
		if (actionLockCounter == 120) {
		Random random = new Random();
		int i = random.nextInt(100)+1;
		if(i <= 25) {
			direction = "up";
		}
		if(i > 25 && i<=50) {
			direction ="down";
		}
		if(i>50 && i<=75) {
			direction = "left";
		}
		if(i>75 && i<= 100) {
			direction = "right";
		}
		actionLockCounter = 0;
		}
	}
	
	public void speak() {
		
		super.speak();
	}
	
}
