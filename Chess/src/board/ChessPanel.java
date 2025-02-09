package board;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import pieces.*;
import tiles.Tile;
import tiles.TileManager;

import java.util.Random;

public class ChessPanel extends JPanel implements ActionListener {
	public static final ChessPanel chessPanel = new ChessPanel(); //ensures that the ChessPanel class is a singleton
	
	//define the resolution for chess. You shouldn't need to resize this.
	static final int UNIT_SIZE = 100; //100 is default. if changed, you will need to edit ChessFrame
	static final int ROW = 8;
	static final int COL = 8;
	static final int SCREEN_WIDTH = ROW * UNIT_SIZE;
	static final int SCREEN_HEIGHT = COL * UNIT_SIZE;
	PieceColor currentTurnColor = PieceColor.WHITE;
	Graphics2D g2;
	
	private boolean initialized; // checks whether pieces have been initialized.
	
	Piece selectedPiece; 
	
	Piece[] pieces = new Piece[32]; //creates the array. Should be 32 when all pieces are implemented.
	
	public void callDraw() {
		repaint();
	}
	
	public static ChessPanel getChessPanel() {
		return chessPanel;
	}
	
	
	public void draw(Graphics2D g2) {
		for (Piece i : pieces) {
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
			g2.setColor(new Color(255,0,0));
			
			if (currentTurnColor == PieceColor.WHITE) {
				g2.drawString("WHITE", 200, 200);
			} else if (currentTurnColor == PieceColor.BLACK) {
				g2.drawString("BLACK", 200, 200);
			}
			if (i.getAlive()) {
				g2.drawImage(i.getImage(), i.getX(), i.getY(), UNIT_SIZE, UNIT_SIZE, null);
			}
		}
	}



	//If draw breaks, move g.dispose().
	public void paintComponent(Graphics g) {
		if (!initialized) {
			initialize();
		}
		super.paintComponent(g);
		drawBoard(g);
		g2 = (Graphics2D)g;
		draw(g2);

	}
	
	
	//////////
	//SET-UP//
	//////////
	
	//CONSTRUCTOR
	private ChessPanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		this.addMouseListener(new MyMouseAdapter());
	}
	
	//manually places all of the pieces
	//I can't remember why its in a try catch block, investigate later
	private void initialize() {
		try {
			pieces[0] = new Pawn(PieceColor.BLACK, UNIT_SIZE * 0, UNIT_SIZE * 1);
			initialPlacement(pieces[0]);
			pieces[1] = new Pawn(PieceColor.BLACK, UNIT_SIZE * 1, UNIT_SIZE * 1);
			initialPlacement(pieces[1]);
			pieces[2] = new Pawn(PieceColor.BLACK, UNIT_SIZE * 2, UNIT_SIZE * 1);
			initialPlacement(pieces[2]);
			pieces[3] = new Pawn(PieceColor.BLACK, UNIT_SIZE * 3, UNIT_SIZE * 1);
			initialPlacement(pieces[3]);
			pieces[4] = new Pawn(PieceColor.BLACK, UNIT_SIZE * 4, UNIT_SIZE * 1);
			initialPlacement(pieces[4]);
			pieces[5] = new Pawn(PieceColor.BLACK, UNIT_SIZE * 5, UNIT_SIZE * 1);
			initialPlacement(pieces[5]);
			pieces[6] = new Pawn(PieceColor.BLACK, UNIT_SIZE * 6, UNIT_SIZE * 1);
			initialPlacement(pieces[6]);
			pieces[7] = new Pawn(PieceColor.BLACK, UNIT_SIZE * 7, UNIT_SIZE * 1);
			initialPlacement(pieces[7]);
			pieces[8] = new Rook(PieceColor.BLACK, UNIT_SIZE * 0, UNIT_SIZE * 0);
			initialPlacement(pieces[8]);
			pieces[9] = new Knight(PieceColor.BLACK, UNIT_SIZE * 1, UNIT_SIZE * 0);
			initialPlacement(pieces[9]);
			pieces[10] = new Bishop(PieceColor.BLACK, UNIT_SIZE * 2, UNIT_SIZE * 0);
			initialPlacement(pieces[10]);
			pieces[11] = new King(PieceColor.BLACK, UNIT_SIZE * 3, UNIT_SIZE * 0);
			initialPlacement(pieces[11]);
			pieces[12] = new Queen(PieceColor.BLACK, UNIT_SIZE * 4, UNIT_SIZE * 0);
			initialPlacement(pieces[12]);
			pieces[13] = new Bishop(PieceColor.BLACK, UNIT_SIZE * 5, UNIT_SIZE * 0);
			initialPlacement(pieces[13]);
			pieces[14] = new Knight(PieceColor.BLACK, UNIT_SIZE * 6, UNIT_SIZE * 0);
			initialPlacement(pieces[14]);
			pieces[15] = new Rook(PieceColor.BLACK, UNIT_SIZE * 7, UNIT_SIZE * 0);
			initialPlacement(pieces[15]);

			pieces[16] = new Pawn(PieceColor.WHITE, UNIT_SIZE * 0, UNIT_SIZE * 6);
			initialPlacement(pieces[16]);
			pieces[17] = new Pawn(PieceColor.WHITE, UNIT_SIZE * 1, UNIT_SIZE * 6);
			initialPlacement(pieces[17]);
			pieces[18] = new Pawn(PieceColor.WHITE, UNIT_SIZE * 2, UNIT_SIZE * 6);
			initialPlacement(pieces[18]);
			pieces[19] = new Pawn(PieceColor.WHITE, UNIT_SIZE * 3, UNIT_SIZE * 6);
			initialPlacement(pieces[19]);
			pieces[20] = new Pawn(PieceColor.WHITE, UNIT_SIZE * 4, UNIT_SIZE * 6);
			initialPlacement(pieces[20]);
			pieces[21] = new Pawn(PieceColor.WHITE, UNIT_SIZE * 5, UNIT_SIZE * 6);
			initialPlacement(pieces[21]);
			pieces[22] = new Pawn(PieceColor.WHITE, UNIT_SIZE * 6, UNIT_SIZE * 6);
			initialPlacement(pieces[22]);
			pieces[23] = new Pawn(PieceColor.WHITE, UNIT_SIZE * 7, UNIT_SIZE * 6);
			initialPlacement(pieces[23]);
			pieces[24] = new Rook(PieceColor.WHITE, UNIT_SIZE * 0, UNIT_SIZE * 7);
			initialPlacement(pieces[24]);
			pieces[25] = new Knight(PieceColor.WHITE, UNIT_SIZE * 1, UNIT_SIZE * 7);
			initialPlacement(pieces[25]);
			pieces[26] = new Bishop(PieceColor.WHITE, UNIT_SIZE * 2, UNIT_SIZE * 7);
			initialPlacement(pieces[26]);
			pieces[27] = new King(PieceColor.WHITE, UNIT_SIZE * 3, UNIT_SIZE * 7);
			initialPlacement(pieces[27]);
			pieces[28] = new Queen(PieceColor.WHITE, UNIT_SIZE * 4, UNIT_SIZE * 7);
			initialPlacement(pieces[28]);
			pieces[29] = new Bishop(PieceColor.WHITE, UNIT_SIZE * 5, UNIT_SIZE * 7);
			initialPlacement(pieces[29]);
			pieces[30] = new Knight(PieceColor.WHITE, UNIT_SIZE * 6, UNIT_SIZE * 7);
			initialPlacement(pieces[30]);
			pieces[31] = new Rook(PieceColor.WHITE, UNIT_SIZE * 7, UNIT_SIZE * 7);
			initialPlacement(pieces[31]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		initialized = true;
	}
	
	//Draws the board
	//Might be called too many times, check after more draw calls
	public void drawBoard(Graphics g) {
		//System.out.println("draw board");
		boolean colorChange = false;
		for (int i = 0; i < COL; i++) {
			colorChange = !colorChange;
			for (int j = 0; j < ROW; j++) {
				if (colorChange == false) {
					g.setColor(Color.black);
					g.fillRect(i*UNIT_SIZE, j*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
					colorChange = true;
				}
				else if (colorChange == true) {
					g.setColor(Color.white);
					g.fillRect(i*UNIT_SIZE, j*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
					colorChange = false;
				}
			}
		}
	}
	
	//UNFINISHED
	//used by the mouse to check if a piece is on a clicked tile and then select that piece.
	public void selectPiece(int x, int y) {
		Tile currentTile = TileManager.findTile(x, y);
		System.out.println(currentTile.getPiece());
		//System.out.println("color: " + currentTile.getPiece().getColor());
		if (selectedPiece != null && selectedPiece.getColor() == currentTurnColor) {
			boolean didMove = selectedPiece.move(currentTile.getLeftX(), currentTile.getTopY());
			//System.out.println("Didmove: " + didMove);
			
			if (didMove) {
				if (currentTurnColor == PieceColor.WHITE) {
					currentTurnColor = PieceColor.BLACK;
				} else {
					currentTurnColor = PieceColor.WHITE;
				}
			}
			
			selectedPiece = null;
			
		}
		
		else if (selectedPiece == null && currentTile.getPiece() != null && currentTile.getPiece().getColor() == currentTurnColor) {
			selectedPiece = currentTile.getPiece();
			System.out.println("Current piece: " + selectedPiece);
		}
	}
	
	//////////////
	//AUTOMATION//
	//////////////
	
	
	//reduce code by automating the initial placement of pieces
	private void initialPlacement(Piece piece) {
		TileManager.findTile(piece.getX(), piece.getY()).occupyTile(piece);
		piece.assignTile(TileManager.findTile(piece.getX(), piece.getY()));
	}
	
	private void switchToAmongUsSkin() throws IOException {
		for (Piece piece : pieces) {
			piece.switchSkin("AmongUs");
		}
	}
	private void switchToDefaultSkin() throws IOException {
		for (Piece piece : pieces) {

			piece.switchSkin("Default");
		}
	}
	
	///////////////////
	//ActionListeners//
	///////////////////

	//Mouse events
	public class MyMouseAdapter extends MouseAdapter{
		//mouse click
		 public void mousePressed(MouseEvent e){
	            int mouseX = e.getX();
	            int mouseY = e.getY();
	        
	            selectPiece(mouseX, mouseY);//checks if you clicked on a piece
	            System.out.println("X: " + mouseX + "\n" + "Y: " +mouseY); //debug: prints out click location
		 }
	}
	
	//Key events
	//Unused for now
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_J: 
				try {
					switchToDefaultSkin();
					callDraw();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case KeyEvent.VK_K:
				try {
					switchToAmongUsSkin();
					callDraw();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
		}
	}

	//I have no idea what it does but the program gets real sad if it goes away
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}
