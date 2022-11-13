package Main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
	
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//This lets the window properly close when user clicks the close button
		window.setResizable(false); //so we can not resize the window to get any errors because of it
		window.setTitle("Origins");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack(); //Causes this Window to be sized to fit the preferred size and layouts of its subcomponents (=GamePanel)
		
		window.setLocationRelativeTo(null); // The window will be displayed at the center of the screen
		window.setVisible(true);//to make window visible
		
		
		gamePanel.setupGame();
		gamePanel.startGameThread();

	}

}
