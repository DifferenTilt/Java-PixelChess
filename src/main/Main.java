package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// this create and object that is a window with a title "Chess".
		JFrame window = new JFrame("Chess");
		// setting a shutdown when the window is closed
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// you could not resize the window and the window is in center of the screen
		window.setResizable(false);

		// Add Game Panel to the window
		GamePanel gp = new GamePanel();
		window.add(gp);
		window.pack(); // smart command for sizing window

		window.setLocationRelativeTo(null);
		// window is visible
		window.setVisible(true);
		
		gp.lauchGame();

	}

}
