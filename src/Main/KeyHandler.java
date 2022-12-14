package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//KeyLsitener - The Listener interface for receiving keyboard events (keystrokes)

public class KeyHandler implements KeyListener {
	
	
	GamePanel gp;
	
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
	//DEBUG
	boolean checkDrawTime = false;
	public KeyHandler(GamePanel  gp) {
		this.gp = gp;
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {	
	}
	@Override 
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode(); //Returns the integer keyCode associated with the key in this event.
		
		
		
		//Title STATE
		if(gp.gameState ==gp.titleState) {
			if(code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum <0) {
					gp.ui.commandNum =1;
				}
			}
			if(code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum >1) {
					gp.ui.commandNum =0;
				}
			}
			
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.commandNum == 0) {
					gp.gameState = gp.playState;
					gp.playMusic(0);
				}
				if(gp.ui.commandNum == 1) {
					System.exit(0);
				}
			}
		}
		
		//PLAY STATE
			if (gp.gameState == gp.playState) {
		
			if(code == KeyEvent.VK_W) {
				upPressed = true;
			}
			if(code == KeyEvent.VK_S) {
				downPressed = true;
			}
			if(code == KeyEvent.VK_A) {
				leftPressed = true;
			}
			if(code == KeyEvent.VK_D) {
				rightPressed = true;
			}	
			if(code == KeyEvent.VK_P) {
				gp.gameState = gp.pauseState;
			}	
			if(code == KeyEvent.VK_SPACE) {
				enterPressed = true;
			}	
			
				/*
				 * if(gp.gameState == gp.playState) {
				 * 
				 * } else if (gp.gameState == gp.pauseState) { gp.gameState = gp.playState; }
				 */
			
			//DEBUG
			if(code == KeyEvent.VK_T) {
				if(checkDrawTime == false) {
					checkDrawTime=true;
					
				}
				else if(checkDrawTime == true) {
					checkDrawTime = false;
				}
			}	
		}
		
			//PAUSE STATE
		else	if(gp.gameState == gp.pauseState) {
			if(code == KeyEvent.VK_P) {
				gp.gameState = gp.playState;
			}
		}
		//DIALOG STATE
		else if(gp.gameState == gp.dialogState) {
			if (code == KeyEvent.VK_SPACE) {
				gp.gameState = gp.playState;
			}
			
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}	
	}
}
