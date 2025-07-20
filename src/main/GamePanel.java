package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.Queen;
import piece.Rook;

// Why inheritance? Cause we need a JPanel with some customized options so we extend
// the super-class adding our own specifications
public class GamePanel extends JPanel implements Runnable {

	// Game screen size, not re-sizable. Java etiquette wants constant to be
	// upper-case
	public static final int WIDTH = 1100;
	public static final int HEIGHT = 850;
	final int FPS = 60;
	Thread gameThread;
	Board board = new Board();
	Mouse mouse = new Mouse();

	// PIECES
	public static ArrayList<Piece> pieces = new ArrayList<>();
	public static ArrayList<Piece> simPieces = new ArrayList<>();
	Piece activeP;

	// COLOR
	public static final int WHITE = 0;
	public static final int BLACK = 1;

	int currentColor = WHITE;

	// Build the effective game panel
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(new Color(112, 95, 64));
		
		// Here we set the mouse reading part on game panel
		addMouseMotionListener(mouse);
		addMouseListener(mouse);

		setPieces();
		copyPieces(pieces, simPieces);
	}

	public void setPieces() {

		// White team
		pieces.add(new Pawn(WHITE, 0, 6));
		pieces.add(new Pawn(WHITE, 1, 6));
		pieces.add(new Pawn(WHITE, 2, 6));
		pieces.add(new Pawn(WHITE, 3, 6));
		pieces.add(new Pawn(WHITE, 4, 6));
		pieces.add(new Pawn(WHITE, 5, 6));
		pieces.add(new Pawn(WHITE, 6, 6));
		pieces.add(new Pawn(WHITE, 7, 6));
		pieces.add(new King(WHITE, 4, 7));
		pieces.add(new Queen(WHITE, 3, 7));
		pieces.add(new Rook(WHITE, 0, 7));
		pieces.add(new Rook(WHITE, 7, 7));
		pieces.add(new Knight(WHITE, 6, 7));
		pieces.add(new Knight(WHITE, 1, 7));
		pieces.add(new Bishop(WHITE, 2, 7));
		pieces.add(new Bishop(WHITE, 5, 7));
		
		// Black team
		pieces.add(new Pawn(BLACK, 0, 1));
		pieces.add(new Pawn(BLACK, 1, 1));
		pieces.add(new Pawn(BLACK, 2, 1));
		pieces.add(new Pawn(BLACK, 3, 1));
		pieces.add(new Pawn(BLACK, 4, 1));
		pieces.add(new Pawn(BLACK, 5, 1));
		pieces.add(new Pawn(BLACK, 6, 1));
		pieces.add(new Pawn(BLACK, 7, 1));
		pieces.add(new King(BLACK, 4, 0));
		pieces.add(new Queen(BLACK, 3, 0));
		pieces.add(new Rook(BLACK, 0, 0));
		pieces.add(new Rook(BLACK, 7, 0));
		pieces.add(new Knight(BLACK, 6, 0));
		pieces.add(new Knight(BLACK, 1, 0));
		pieces.add(new Bishop(BLACK, 2, 0));
		pieces.add(new Bishop(BLACK, 5, 0));
	}

	private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
		
		// Target list must be empty
		target.clear();
		for(int i = 0; i < source.size(); i++)
			target.add(source.get(i));
	}
	
	/*
	 * Here comes the part needed to implement a game loop: the main process driving
	 * all game rendering threads, present in all modern games. It handles input
	 * processing, internal status updates, rendering, AI, and other processes.
	 */
	public void lauchGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	// The "real" game loop is inside this method
	@Override
	public void run() {

		// GAME LOOP
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		while (gameThread != null) {
			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}

	// Handling updating
	private void update() {
		
		if(mouse.pressed) {
			if (activeP == null) {
				for (Piece piece : simPieces) {
					if (piece.color == currentColor && 
							piece.col == (mouse.x - 50)/Board.SQUARE_SIZE &&
							piece.row == (mouse.y - 50)/Board.SQUARE_SIZE) {
						activeP = piece;
					}
				}
			}
			else {
				// If the player is holding a piece, simulate the move
				simulate();
			}
		}
		
		// MOUSE BUTTON RELEASE
		if (!mouse.pressed) {
			if (activeP != null) {
				activeP.updatePosition();
				activeP = null;
			}
		}
	}

	private void simulate() {
		
		// If a piece is being held, update is position
		activeP.x = mouse.x - Board.HALF_SQUARE_SIZE;
		activeP.y = mouse.y - Board.HALF_SQUARE_SIZE;
		activeP.col = activeP.getCol(activeP.x);
		activeP.row = activeP.getRow(activeP.y);
		
		
	}

	// method inherited from JComponent
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Here we need something different from the general Graphics so we cast

		Graphics2D g2 = (Graphics2D) g;
		// Effectively draw the board
		board.draw(g2);

		// Draw pieces
		for (Piece p : simPieces) {
			p.draw(g2);
		}
		
		// Color the square on which the piece is passing changing the opacity of the square
		if (activeP != null) {
			g2.setColor(Color.white);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
			g2.fillRect(activeP.col * Board.SQUARE_SIZE - 50, activeP.row * Board.SQUARE_SIZE - 50, Board.SQUARE_SIZE,
					Board.SQUARE_SIZE);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			
			// Draw the active piece in the end so it won't be hidden 
			activeP.draw(g2);
		}
	}

}
