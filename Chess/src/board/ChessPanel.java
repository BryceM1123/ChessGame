package board;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import pieces.*;
import tiles.Tile;
import tiles.TileManager;

import java.util.Random;

public final class ChessPanel extends JPanel implements ActionListener {
	public static final ChessPanel chessPanel = new ChessPanel(); //ensures that the ChessPanel class is a singleton
	
	//define the resolution for chess
	private static final int UNIT_SIZE = 100; //100 is default. if changed, it ideally should change the size of the screen. this doesn't work right now
	private static final int ROW = 8;
	private static final int COL = 8;
	private static final int SCREEN_WIDTH = ROW * UNIT_SIZE;
	private static final int SCREEN_HEIGHT = COL * UNIT_SIZE;
	private PieceColor currentTurnColor = PieceColor.WHITE;
	private Graphics2D g2;
	private boolean gameOver = false;
	private boolean initialized; // checks whether pieces have been initialized.
	
	//Kings
	private Piece whiteKing;
	private Piece blackKing;
	
	private BufferedImage darkeningEffect;
	private BufferedImage pathableTile;
	private BufferedImage targetableTile;
	private BufferedImage tileOutline;
	private BufferedImage selectedTile;
	private BufferedImage checkTile;
	
	PieceColor winningTeam = null;
	
	Piece selectedPiece; 
	Tile[][] tiles = TileManager.getTiles();
	Piece[] pieces = new Piece[32]; //creates the array. Should be 32 when all pieces are implemented.
	
	public void gameOver(PieceColor winningTeam) {
		this.winningTeam = winningTeam;
		gameOver = true;
		repaint();	
	}
	
	public void newGame() {
		TileManager.resetTiles();
		chessPanel.reinitializePieces();
		currentTurnColor = PieceColor.WHITE;
		selectedPiece = null;
		gameOver = false;
		repaint();
	}
	
	public void callDraw() {
		repaint();
	}
	
	public static ChessPanel getChessPanel() {
		return chessPanel;
	}
	
	
	public void draw(Graphics2D g2) {
		
		
		for (Tile[] tilesArray: tiles) {
			for (Tile tile : tilesArray) {
				if (tile.isPathable()) {
					
					
					g2.drawImage(pathableTile, tile.getLeftX(), tile.getTopY(), UNIT_SIZE, UNIT_SIZE, null);
				}
				else if (tile.isTargetable()) {
					
					
					g2.drawImage(targetableTile, tile.getLeftX(), tile.getTopY(), UNIT_SIZE, UNIT_SIZE, null);
				}
				else if (tile.getPiece() != null && tile.getPiece() == selectedPiece) {
					g2.drawImage(selectedTile, tile.getLeftX(), tile.getTopY(), UNIT_SIZE, UNIT_SIZE, null);
				}
				else if (tile.isInCheck()) {
					g2.drawImage(checkTile, tile.getLeftX(), tile.getTopY(), UNIT_SIZE, UNIT_SIZE, null);
				}
			
				g2.drawImage(tileOutline, tile.getLeftX(), tile.getTopY(), UNIT_SIZE, UNIT_SIZE, null);
			}
			
		}
		for (Piece piece : pieces) {
			if (piece.isAlive()) {
				g2.drawImage(piece.getImage(), piece.getX(), piece.getY(), UNIT_SIZE, UNIT_SIZE, null);
			}
		}
	}
	public void paintComponent(Graphics g) {
		if (!initialized) {
			reinitializePieces();
		}
		super.paintComponent(g);
		drawBoard(g);
		g2 = (Graphics2D)g;
		draw(g2);
		if (gameOver) {
			g2.drawImage(darkeningEffect, 0, 0, UNIT_SIZE * COL, UNIT_SIZE * ROW, null);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 100)); 
			g2.setColor(new Color(255,0,0));
			if (winningTeam	== PieceColor.BLACK) {
				g2.drawString("Black wins", 200, 200);
			}
			else if( winningTeam == PieceColor.WHITE) {
				g2.drawString("White wins", 200, 200);
			}
			
		}
		g2.dispose();
		g.dispose();
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
	private void reinitializePieces() {
		if (darkeningEffect == null) {
			try {
				darkeningEffect = ImageIO.read(getClass().getResourceAsStream("/other/Darkening_Effect.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (pathableTile == null) {
			try {
				pathableTile = ImageIO.read(getClass().getResourceAsStream("/other/Pathable_Tile.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (targetableTile == null) {
			try {
				targetableTile = ImageIO.read(getClass().getResourceAsStream("/other/Targetable_Tile.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (tileOutline == null) {
			try {
				tileOutline = ImageIO.read(getClass().getResourceAsStream("/other/Tile_Outline.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (selectedTile == null) {
			try {
				selectedTile = ImageIO.read(getClass().getResourceAsStream("/other/Selected_Tile.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (checkTile == null) {
			try {
				checkTile = ImageIO.read(getClass().getResourceAsStream("/other/Check_Tile.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
			
			whiteKing = pieces[27];
			blackKing = pieces[11];
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
		for (int i = 0; i < COL; ++i) {
			colorChange = !colorChange;
			for (int j = 0; j < ROW; j++) {
				if (colorChange == false) {
					g.setColor(Color.gray);
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
	public void selectPiece(int x, int y) throws IOException {
		Tile currentTile = TileManager.findTile(x, y);
		System.out.println(currentTile.getPiece());
		//System.out.println("color: " + currentTile.getPiece().getColor());
		if (selectedPiece != null && selectedPiece.getColor() == currentTurnColor) {
			boolean didMove = selectedPiece.move(currentTile.getLeftX(), currentTile.getTopY());
			//System.out.println("Didmove: " + didMove);
			
			if (didMove) {
				for (Tile[] tileArray : tiles) {
					for (Tile tile : tileArray) {
						tile.setNotInCheck();
					}
				}
				for (Piece piece : pieces) {
					if (piece.isAlive()) {
						piece.showPathing();
						
					}
				}
				if (whiteKing.checkMate(pieces)) {
						System.out.println("Black wins");
					gameOver(PieceColor.BLACK);
				}
				else if (blackKing.checkMate(pieces)) {
					gameOver(PieceColor.WHITE);
				}
				for (Piece piece : pieces) {
					if (piece.isAlive()) {
						
						piece.hidePathing();
					}
				}
				if (currentTurnColor == PieceColor.WHITE) {
					currentTurnColor = PieceColor.BLACK;
				} else {
					currentTurnColor = PieceColor.WHITE;
				}
				selectedPiece.hidePathing();
				selectedPiece.unselectPiece();
				selectedPiece = null;
			} else if (didMove == false) {
				if (currentTile.getPiece() != null && currentTile.getPiece().getColor() == currentTurnColor) {
					selectedPiece.hidePathing();
					selectedPiece.unselectPiece();
					
					selectedPiece = currentTile.getPiece();
					selectedPiece.selectPiece();
					System.out.println("Current piece: " + selectedPiece);
				} else { 
					//Unselects current piece if an invalid move is picked.
					selectedPiece.hidePathing();
					selectedPiece.unselectPiece();
					selectedPiece = null;
				}
			}
			
			
			
		}
		
		else if (selectedPiece == null && currentTile.getPiece() != null && currentTile.getPiece().getColor() == currentTurnColor) {
			selectedPiece = currentTile.getPiece();
			selectedPiece.selectPiece();
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
	            if (!gameOver) {
	            	try {
						selectPiece(mouseX, mouseY); //checks if you clicked on a piece
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	            }
	            System.out.println("X: " + mouseX + "\n" + "Y: " +mouseY); //debug: prints out click location
		 }
	}
	
	//Key events
	//Unused for now
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				if (gameOver) {
					newGame();
				}
				break;
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
