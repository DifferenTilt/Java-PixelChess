package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Board {
	final int MAX_COL = 8;
	final int MAX_ROW = 8;
	public static final int SQUARE_SIZE = 100;
	public static final int HALF_SQUARE_SIZE = SQUARE_SIZE / 2;
	
	// Method used to draw the effective chess board
	public void draw(Graphics2D g2) {
		// needed to switch color
		int c = 0;
		// be aware of the fact that we need to switch colors at the end of the row
		for (int row = 0; row < MAX_ROW; row++) {
			for (int col = 0; col < MAX_COL; col++) {
				// In color we got RGB numbers
				if (c == 0) {
					g2.setColor(new Color (210, 165, 125));
					if ( col != MAX_COL - 1) c = 1;
					else c = 0;
				}
				else {
					g2.setColor(new Color (175, 115, 70));
					if ( col != MAX_COL - 1) c = 0;
					else c = 1;
				}
				g2.fillRect(50 + col*SQUARE_SIZE, 50 + row*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
			}
			
			/*
			 * A cleaner version but did not came up to my mind, very important to be out
			 * the second for but inside the first!
			 * if(c == 0) {
			 * 		c = 1;
			 * }
			 * else {
			 * 		c = 0;
			 * }
			 */
		}
		
		//Letters and numbers part 
		String columns[] = {"A", "B", "C", "D", "E", "F", "G", "H"};
		String rows[] = {"1", "2", "3", "4", "5", "6", "7", "8"};
		
		g2.setFont(new Font("Sans Serif", Font.BOLD, 24));
        g2.setColor(Color.white); 
        
		for (int row = 0; row < MAX_ROW; row++) {
			g2.drawString(rows[MAX_ROW - 1 - row], HALF_SQUARE_SIZE / 2, 50 + row * SQUARE_SIZE + HALF_SQUARE_SIZE);
		}
		
		for (int col = 0; col < MAX_COL; col++) {
			g2.drawString(columns[col], 35 + col * SQUARE_SIZE + HALF_SQUARE_SIZE , HALF_SQUARE_SIZE / 2);
		}
	}
}
