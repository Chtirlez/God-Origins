package Main;

import java.awt.*;
import java.util.Iterator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;


// The Game Panel have all functions of JPanel
public class GamePanel extends JPanel implements Runnable{ //Runnable is a key to use Thread
	//SCREEN SETTINGS
	
	final int originalTileSize = 16; // 16x16
	final int scale = 3;
	public final int tileSize = originalTileSize * scale; //48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; //768 pixels
	public final int screenHeight = tileSize * maxScreenRow; //576 pixels
	
	
	// WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	
	//FPS
	int FPS = 60;
	
	
	//SYSTEM
	TileManager tileM = new TileManager(this);
	
	//To come back first version of the game - delete 'this' from brackets
	public KeyHandler keyH = new KeyHandler(this); //Our Key class call
	
	
	Sound music = new Sound();
	Sound se = new Sound();
	
	
	public	CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	
	public UI ui = new UI(this);
	
	Thread gameThread; //it keeps your program running until you stop it
	
	//ENTITY AND OBJECT
public	Player player = new Player(this, keyH);
public SuperObject obj[] = new SuperObject[10];
public Entity npc[] = new Entity[10];
		//GAME STATE
public int gameState;
public final int titleState = 0;
public final int playState = 1;
public final int pauseState = 2;
public final int dialogState = 3;







	//Set player's default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));// Set the size of this class (JPanel)
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); //If set to true all the drawing from this component will be done in an offscreen painting buffer. In short, enabling this can improve game's rendering performance
		this.addKeyListener(keyH);
		this.setFocusable(true); //With this, this GamePanel can be 'focused' to receive key input
	}
	
	public void setupGame() {
		
		aSetter.setObject();
		aSetter.SetNPC();
		//playMusic(0);
		gameState = titleState;
		
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}


	
	/*
	 * public void run() {
	 * 
	 * double drawInterval = 1000000000/FPS; double nextDrawTime = System.nanoTime()
	 * + drawInterval; while(gameThread != null) { //long currentTime =
	 * System.nanoTime(); //Returns the current value of the running Java Virtual
	 * MAchine's high-resolution time source, in nanoseconds. // 1 000 000 000
	 * nanoseconds = 1 second // System.out.println("current Time: " + currentTime);
	 * //System.out.println("The game loop is running"); //1 UPDATE: update
	 * inforamation such as character positions
	 * 
	 * 
	 * update();
	 * 
	 * 
	 * //2 DRAW: draw the screen with the updated inforamtion repaint();
	 * 
	 * 
	 * try { double remainingTime = nextDrawTime - System.nanoTime(); remainingTime
	 * = remainingTime/1000000;
	 * 
	 * if(remainingTime < 0) { remainingTime = 0; }
	 * 
	 * Thread.sleep((long) remainingTime);
	 * 
	 * nextDrawTime += drawInterval;
	 * 
	 * 
	 * } catch (InterruptedException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * 
	 * }
	 */
	
	
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS; //0.01666 seconds
		double delta = 0;
		//System.nanoTime() - Returns the current value of the running Java Virtual Machine'shigh-resolution time source, in nanoseconds
		long lastTime = System.nanoTime(); 
		long currentTime;
		long timer = 0;
		long drawCount = 0;
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime)/ drawInterval; //Remaining
			timer+=(currentTime-lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
		//FPS counter
			if(timer >=1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount =0;
				timer = 0;
			}	
			
		}
	 
	}
	
	//In Java the upper left corner is X:0 Y:0
	//So X values increases to the right
	//Y values increases as they go down
	public void update() {
		if(gameState == playState) {
			player.update();
			//NPC
			for (int i = 0; i < npc.length; i++) {
				if(npc[i] != null) {
					npc[i].update();
				}
			}
		}
		if (gameState == pauseState) {
			//nothing
		}
	}
	
	
	//Its pencil or brush
	//Graphics  - a class that has many functions to draw objects on the screen
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g); // always write this to start work with the Graphics class
		
		Graphics2D g2 = (Graphics2D)g; //add more functions to g
		
		//DEBUG
		long drawStart = 0;
		if(keyH.checkDrawTime == true) {
			drawStart = System.nanoTime();
		}
	
		//TITLE SCREEN
		if (gameState == titleState) {
			ui.draw(g2);
		}
		
		//OTHERS
		else {
			//TILE
			tileM.draw(g2);
			
			
			// OBJECT
			for(int i =0; i<obj.length; i++) {
				if(obj[i]!=null) {
					obj[i].draw(g2, this);
				}
			}
			
			//NPC
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					npc[i].draw(g2);
				}
			}
			
			//PLAYER
			player.draw(g2);
			
			//UI
			ui.draw(g2);
		}
		
		
		
		
		//DEBUG	
		if(keyH.checkDrawTime ==true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Draw Time" + passed, 10, 400);
			System.out.println("Draw Time: " +passed);
			
		}
		
		g2.dispose();
	
	}
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
		
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
	
}
