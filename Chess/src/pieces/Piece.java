package pieces;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Stack;

import javax.imageio.ImageIO;

import board.ChessPanel;
import tiles.Tile;
import tiles.TileManager;

public class Piece {
	protected PieceColor color;
	protected int y;
	protected int x;
	protected Tile currentTile;
	private BufferedImage image;
	private boolean alive = true;
	protected Stack<int[]> pathingStorage = new Stack<int[]>();
	protected Stack<int[]> targetingStorage = new Stack<int[]>();
	protected final Tile[][] tiles = TileManager.getTiles();
	private final String whiteImage;
	private final String blackImage;
	private final String amongUsImage;
	protected final ChessPanel chessPanel = Objects.requireNonNull(ChessPanel.getChessPanel());
	
	//add images to this parent constructor
	Piece(PieceColor color, int x, int y, String whiteImage, String blackImage, String amongUsImage) throws IOException {
		this.color = Objects.requireNonNull(color);
		this.x = Objects.requireNonNull(x);
		this.y = Objects.requireNonNull(y);
		this.whiteImage = Objects.requireNonNull(whiteImage);
		this.blackImage = Objects.requireNonNull(blackImage);
		this.amongUsImage = Objects.requireNonNull(amongUsImage);
		
		if (color == PieceColor.WHITE) {
			this.image = ImageIO.read(getClass().getResourceAsStream(whiteImage));
		} else {
			this.image = ImageIO.read(getClass().getResourceAsStream(blackImage));;
		}
	}
	
	//required for every piece
	//implementation is unique for each subclass
	public void showPathing() {
		
	}
	
	public void hidePathing() {
		while (!pathingStorage.empty()) {
			int[] coordinates = pathingStorage.pop();
			tiles[coordinates[0]][coordinates[1]].setNotPathable();
		}
		while (!targetingStorage.empty()) {
			int[] coordinates = targetingStorage.pop();
			tiles[coordinates[0]][coordinates[1]].setNotTargetable();
		}
	}
	public void selectPiece() throws IOException {
		showPathing();
		chessPanel.callDraw();
	}
	
	public void unselectPiece() throws IOException {
		if (color == PieceColor.WHITE) {
			this.image = ImageIO.read(getClass().getResourceAsStream(whiteImage));
		} else {
			this.image = ImageIO.read(getClass().getResourceAsStream(blackImage));;
		}
		chessPanel.callDraw();
	}
	
	public void switchSkin(String skinSet) throws IOException {
		switch (skinSet) {
		case "Default":
			if (color == PieceColor.WHITE) {
				this.image = ImageIO.read(getClass().getResourceAsStream(whiteImage));
			} else {
				this.image = ImageIO.read(getClass().getResourceAsStream(blackImage));;
			}
			break;
		case "AmongUs":
			this.image = ImageIO.read(getClass().getResourceAsStream(amongUsImage));
			break;
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
		Tile oldTile = currentTile;
		Tile targetTile = TileManager.findTile(targetX, targetY);
	
		if (targetTile.isPathable()) {
			//take an unoccupied tile
			targetTile.occupyTile(this);
			currentTile = targetTile;
			this.x = targetTile.getLeftX();
			this.y = targetTile.getTopY();
			oldTile.unoccupyTile();	
			chessPanel.callDraw();
			return true;
		}
		else if (targetTile.isTargetable() ) {
			attack(targetTile);
			return true;
		}	 
		return false;
	}
	
	public void attack(Tile targetTile) {
		Piece targetPiece = targetTile.getPiece();
		Tile oldTile = currentTile;
		targetTile.occupyTileByForce(this);
		currentTile = targetTile;
		oldTile.unoccupyTile();
		targetPiece.kill();
		this.x = targetTile.getLeftX();
		this.y = targetTile.getTopY();
		chessPanel.callDraw();
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public boolean checkMate(Piece[] pieces) {
		return false;
	}
	public void kill() {
		alive = false;
		y = -1000;
		x = -1000;
		
		
		if (this instanceof King) {
			if (this.color == PieceColor.BLACK) {
				chessPanel.gameOver(PieceColor.WHITE);
			} else {
			chessPanel.gameOver(PieceColor.BLACK);
			}
			chessPanel.callDraw();
		}
	}
	
	//checks whether there is another piece in between the current tile and the target tile
	
	public boolean checkCollision(MovementDirection direction, int  pieceX, int pieceY, int tileX, int tileY) {
		Tile[][] tiles = TileManager.getTiles();
		int goal; //the row or column that the piece is moving through (only applicable for North/South and East/West
		int rowNumber = -10000; //arbitrary number cause it can't be zero
		int colNumber = -10000; //^^
		
		switch (direction)  {
			case NORTH:	
				goal = tileY + 100;//i don't remember what this does, comment these later
				//finds the correct Row
				rowNumber = 1;
				for (int i = 0; i < tiles[0].length; ++i) {
					if (pieceX == tiles[0][i].getLeftX()) {
						rowNumber = i;
						break;
						
					}
				}
				
				// checks any tile in that row between the target tile and the piece
				for (int i = 0; i < tiles.length; ++i) {
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
				//finds the correct Row
				for (int i = 0; i < tiles[0].length; ++i) {
					if (pieceX == tiles[0][i].getLeftX()) {
						rowNumber = i;
						break;
						
					}
				}
				
				// checks any tile in that row between the target tile and the piece
				for (int i = 0; i < tiles.length; ++i) {
					if (tiles[i][rowNumber].getTopY() <= goal && tiles[i][rowNumber].getTopY() > pieceY) {
						if (tiles[i][rowNumber].getPiece() != null) {
							return false;
						}
					}
				}
				break;
				
			case EAST:
				goal = tileX + 100;
				//finds the correct column
				for (int i = 0; i < tiles.length; ++i) {
					if (pieceY == tiles[i][0].getTopY()) {
						colNumber = i;
						break;
					}
				}
				
				//checks any tile in that column between the target tile and the piece
				for (int i = 0; i < tiles[colNumber].length; ++i) {
					if (tiles[colNumber][i].getLeftX() >= goal && tiles[colNumber][i].getLeftX() < pieceX) {
						if (tiles[colNumber][i].getPiece() != null) {
							return false;
						}
					}
				}
				break;
				
			case WEST:
				goal = tileX - 100;
				//finds the correct column
				for (int i = 0; i < tiles.length; ++i) {
					if (pieceY == tiles[i][0].getTopY()) {
						colNumber = i;
						break;
					}
				}
				
				//checks any tile in that column between the target tile and the piece
				for (int i = 0; i < tiles[colNumber].length; ++i) {
					if (tiles[colNumber][i].getLeftX() <= goal && tiles[colNumber][i].getLeftX() > pieceX) {
						if (tiles[colNumber][i].getPiece() != null) {
							return false;
						}
					}
				}
				break;
				
			case NORTHEAST:
				//NORTHEAST
				if (y - tileY == x - tileX) {
					int numberOfTiles = (y - tileY);
					
					for (int i = 0 ; i < tiles.length; ++i) {
						for (int j = 0; j < tiles[i].length; ++j) {
							if ((y - tiles[i][j].getTopY() == x - tiles[i][j].getLeftX()) && y - tiles[i][j].getTopY() > 0 && y - tiles[i][j].getTopY() < numberOfTiles) {
								if (tiles[i][j].getPiece() != null) {	
									return false;
								}
							} 
						}
					}
				}
				break;
			case NORTHWEST:
				if (y - tileY == -(x - tileX)) {
					int numberOfTiles = (y - tileY);
					for (int i = 0 ; i < tiles.length; ++i) {
						for (int j = 0; j < tiles[i].length; ++j) {
							if ((y - tiles[i][j].getTopY() == -(x - tiles[i][j].getLeftX())) && y - tiles[i][j].getTopY() > 0 && y - tiles[i][j].getTopY() < numberOfTiles) {
								if (tiles[i][j].getPiece() != null) {	
									return false;
								}
							} 
						}
					}
				}
				break;
			case SOUTHEAST:

				if (y - tileY == -(x - tileX)) {

					int numberOfTiles = (y - tileY);
					for (int i = 0 ; i < tiles.length; ++i) {
						for (int j = 0; j < tiles[i].length; ++j) {
							if ((y - tiles[i][j].getTopY() == -(x - tiles[i][j].getLeftX())) && y - tiles[i][j].getTopY() < 0 && y - tiles[i][j].getTopY() > numberOfTiles) {
								if (tiles[i][j].getPiece() != null) {	
									return false;
								}
							} 
						}
					}
				}
				break;
			case SOUTHWEST:
				if (y - tileY == x - tileX) {
					int numberOfTiles = (y - tileY);
					for (int i = 0 ; i < tiles.length; ++i) {
						for (int j = 0; j < tiles[i].length; ++j) {
							if ((y - tiles[i][j].getTopY() == x - tiles[i][j].getLeftX()) && y - tiles[i][j].getTopY() < 0 && y - tiles[i][j].getTopY() > numberOfTiles) {
								if (tiles[i][j].getPiece() != null) {	
									return false;
								}
							} 
						}
					}
				}
				break;
							
			
		}
		return true;
	}
	
	public BufferedImage getImage() {
		return image;
	}
}
