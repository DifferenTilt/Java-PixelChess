package piece;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Board;

// This will be a super class for every piece, so a blueprint for all of them
// with some basic properties
public class Piece {

	public BufferedImage image;
	public int x, y;
	public int row, col, preRow, preCol;
	public int color;
	
	public Piece (int color, int col, int row) {
		
		this.color = color;
		this.col = col;
		this.row = row;
		x = getX(col);
		y = getY(row);
		preCol = col;
		preRow = row;
	}
	
	// Method for actually showing the piece. Importing the images
	// as buffered images and use them. 
	public BufferedImage getImage(String imagePath) {
		
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public int getX(int col) {
		return col * Board.SQUARE_SIZE + 50;
	}
	
	public int getY(int row) {
		return row * Board.SQUARE_SIZE + 50;
	}
	
	public int getCol(int x) {
		return (x + 50 + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;
	}
	
	public int getRow(int y) {
		return (y + 50 + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;
	}
	
	public void updatePosition() {
		x = getX(col);
		y = getY(row);
		preCol = getCol(x);
		preRow = getRow(y);
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
	}
}
