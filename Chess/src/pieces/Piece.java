package pieces;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import board.ChessPanel;
import tiles.Tile;
import tiles.TileManager;

public class Piece {
	protected PieceColor color;
	protected int y;
	protected int x;
	protected Tile currentTile;
	public BufferedImage image;
	public boolean alive = true;
	
	ChessPanel chessPanel = ChessPanel.getChessPanel();
	
	//add images to this parent constructor
	Piece(PieceColor color, int x, int y, String whiteImage, String blackImage) throws IOException {
		this.color = color;
		this.x = x;
		this.y = y;
		
		if (color == PieceColor.WHITE) {
			this.image = ImageIO.read(getClass().getResourceAsStream(whiteImage));
		} else {
			this.image = ImageIO.read(getClass().getResourceAsStream(blackImage));;
		}
	}
	
	public int getY() {
		return y;
	}
	
	public int getX() {
		return x;
	}
	
	public Tile getCurrentTile() {
		return currentTile;
	}
	
	public PieceColor getColor() {
		return color;
	}
	
	public void assignTile(Tile tile) {
		this.currentTile = tile;
	}
	
	public boolean move(int targetX, int targetY) {
		return false;
	}
	
	public boolean getAlive() {
		return alive;
	}
	
	public void kill() {
		alive = false;
		y = -1000;
		x = -1000;
	}
	
	public boolean checkCollision(MovementDirection direction, int  pieceX, int pieceY, int tileX, int tileY) {
		Tile[][] tiles = TileManager.getTiles();
		int goal;
		int rowNumber = -1;
		int colNumber = -1;
		switch (direction)  {
			case NORTH:
				goal = tileY + 100;
				rowNumber = 1;
				for (int i = 0; i < tiles[0].length; i++) {
					if (pieceX == tiles[0][i].getLeftX()) {
						rowNumber = i;
						break;
						
					}
				}
				for (int i = 0; i < tiles.length; i++) {
					if (tiles[i][rowNumber].getTopY() >= goal && tiles[i][rowNumber].getTopY() < pieceY) {
						if (tiles[i][rowNumber].getPiece() != null) {
							System.out.println("piece y: " + tiles[i][rowNumber].getPiece().getY());
							System.out.println("piece x: " + tiles[i][rowNumber].getPiece().getX());
							return false;
						}
					}
				}
				break;
				
			case SOUTH:
				goal = tileY - 100;
				for (int i = 0; i < tiles[0].length; i++) {
					if (pieceX == tiles[0][i].getLeftX()) {
						rowNumber = i;
						break;
						
					}
				}
				for (int i = 0; i < tiles.length; i++) {
					if (tiles[i][rowNumber].getTopY() <= goal && tiles[i][rowNumber].getTopY() > pieceY) {
						if (tiles[i][rowNumber].getPiece() != null) {
							System.out.println("piece y: " + tiles[i][rowNumber].getPiece().getY());
							System.out.println("piece x: " + tiles[i][rowNumber].getPiece().getX());
							return false;
						}
					}
				}
				break;
				
			case EAST:
				goal = tileX + 100;
				
				for (int i = 0; i < tiles.length; i++) {
					if (pieceY == tiles[i][0].getTopY()) {
						colNumber = i;
						break;
					}
				}
				
				for (int i = 0; i < tiles[colNumber].length; i++) {
					if (tiles[colNumber][i].getLeftX() >= goal && tiles[colNumber][i].getLeftX() < pieceX) {
						if (tiles[colNumber][i].getPiece() != null) {
							System.out.println("piece y: " + tiles[colNumber][i].getPiece().getY());
							System.out.println("piece x: " + tiles[colNumber][i].getPiece().getX());
							return false;
						}
					}
				}
				break;
				
			case WEST:
				goal = tileX - 100;
				
				for (int i = 0; i < tiles.length; i++) {
					if (pieceY == tiles[i][0].getTopY()) {
						colNumber = i;
						break;
					}
				}
				
				for (int i = 0; i < tiles[colNumber].length; i++) {
					if (tiles[colNumber][i].getLeftX() <= goal && tiles[colNumber][i].getLeftX() > pieceX) {
						if (tiles[colNumber][i].getPiece() != null) {
							System.out.println("piece y: " + tiles[colNumber][i].getPiece().getY());
							System.out.println("piece x: " + tiles[colNumber][i].getPiece().getX());
							return false;
						}
					}
				}
				break;
			case NORTHEAST:
				break;
			case NORTHWEST:
				break;
			case SOUTHEAST:
				break;
			case SOUTHWEST:
				break;
							
			
		}
		return true;
	}
	
	public BufferedImage getImage() {
		return image;
	}
}
