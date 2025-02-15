package pieces;

import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;

import tiles.Tile;
import tiles.TileManager;

public class King extends Piece {
	Stack<Tile> checkStorage = new Stack<Tile>();
	
	public King(PieceColor color, int x, int y) throws IOException {
		super(color, x, y, "/pieces/White_King.png", "/pieces/Black_King.png", "/pieces/test.png");
		Tile[][] tiles = TileManager.getTiles();
		for (Tile[] tileArray : tiles) {
			for (Tile tile : tileArray) { 
				if((tile.getLeftX() == x - 100) && (tile.getTopY() == y - 100) 
						|| (tile.getLeftX() == x - 100) && (tile.getTopY() == y)
						|| (tile.getLeftX() == x - 100) && (tile.getTopY() == y + 100)
						|| (tile.getLeftX() == x) && (tile.getTopY() == y - 100)
						|| (tile.getLeftX() == x) && (tile.getTopY() == y + 100) 
						||(tile.getLeftX() == x + 100) && (tile.getTopY() == y - 100)
						|| (tile.getLeftX() == x + 100) && (tile.getTopY() == y)
						|| (tile.getLeftX() == x + 100) && (tile.getTopY() == y + 100)) {
					tile.setCheckable();  
					checkStorage.push(tile); 
				}
				else if ((tile.getLeftX() == x) && (tile.getTopY() == y)) {
					tile.setResidentKing();
				}
			}
		}
		
	}
	@Override
	public boolean checkMate(Piece[] pieces) {
		if (currentTile.isInCheck()) { //the king is checked
			System.out.println("is in check");
			Stack<Tile> tempStorage = new Stack<>();
		    Stack<Tile> checkStorage2 = new Stack<>();
		    while (!checkStorage.isEmpty()) {
	            tempStorage.push(checkStorage.pop());
	        }

		    
	        while (!tempStorage.isEmpty()) {
	            Tile tempTile = tempStorage.pop();
	            checkStorage2.push(tempTile);
	            checkStorage.push(tempTile); 
	        }
		    
			while (!checkStorage2.isEmpty()) { 
				Tile tile = checkStorage2.pop();
				if (!tile.isOccupied()) { //there isnt someone on the tile
					System.out.println(tile.getName() + "is not occupied");
					if (!tile.isChecked()) { // the tile isnt checked
						System.out.println(tile.getName() + "is not checked");
						return false;
					} else {
						for (Tile[] tempTileArray : tiles) {
							for (Tile tempTile : tempTileArray) {
								tempTile.setNotPathable();
							}
						}
						for (Piece piece : pieces) {
							if (piece.getClass() != this.getClass() && piece.isAlive()) {
								System.out.println("AUGHHH");
								if (piece.getColor() == color) {
									piece.showPathing();
									
									if (tile.isPathable()) {
										System.out.println(piece.getClass());
										System.out.println("PATHABLE: " + tile.getName());
										piece.hidePathing();
										return false;
									} else {
										System.out.println("NAMENAMENAME: " + tile.getName());
										piece.hidePathing();
									}
								}
							}
						}
					}
				}
			}
			return true;
		}
		System.out.println("is not in check");
		return false;
	}
	
	@Override
	public void showPathing() {
		for (int i = 0; i < tiles.length; ++i)	{
			for (int j = 0; j < tiles[i].length; ++j) {
				if (((y - tiles[i][j].getTopY() > 0 &&  y -  tiles[i][j].getTopY() <= 100) && (x - tiles[i][j].getLeftX() < 100 && x - tiles[i][j].getLeftX() > -100)) //NORTH
						|| ((y - tiles[i][j].getTopY() >= -100 && y - tiles[i][j].getTopY() < 0) && (x - tiles[i][j].getLeftX() < 100 && x - tiles[i][j].getLeftX() > -100)) //SOUTH 
						|| ((x - tiles[i][j].getLeftX() > 0 && x - tiles[i][j].getLeftX() <= 100) && (y - tiles[i][j].getTopY() < 100 &&  y -  tiles[i][j].getTopY() > -100)) //EAST
						|| ((x - tiles[i][j].getLeftX() >= -100 && x - tiles[i][j].getLeftX() < 0) && (y - tiles[i][j].getTopY() < 100 &&  y -  tiles[i][j].getTopY() > -100)) //WEST
						|| ((y - tiles[i][j].getTopY() <= 100 && y - tiles[i][j].getTopY() > 0) && x - tiles[i][j].getLeftX() == y - tiles[i][j].getTopY()) //NORTHEAST
						|| ((y - tiles[i][j].getTopY() <= 100 && y - tiles[i][j].getTopY() > 0) && -(x - tiles[i][j].getLeftX()) == y - tiles[i][j].getTopY()) //NORTHWEST
						|| ((y - tiles[i][j].getTopY() < 0 && y - tiles[i][j].getTopY() >= -100) && -(x - tiles[i][j].getLeftX()) == y - tiles[i][j].getTopY()) //SOUTHEAST
						|| ((y - tiles[i][j].getTopY() < 0 && y - tiles[i][j].getTopY() >= -100) && x - tiles[i][j].getLeftX() == y - tiles[i][j].getTopY())) {
						
					if (tiles[i][j].isOccupied()) {
						if (tiles[i][j].getPiece().getColor() != color) {
							tiles[i][j].setTargetable();
							targetingStorage.push(new int[]{i, j});
						}
					} else {
						tiles[i][j].setPathable();
						pathingStorage.push(new int[]{i, j});
					}
					
				}
			}
		}
	}
	
	@Override
	public boolean move(int targetX, int targetY) {
		Tile oldTile = currentTile;
		Tile targetTile = TileManager.findTile(targetX, targetY);

		//preemptively checks that the target tile does not contain a piece of the same color
		boolean notFriendlyFire = true;
		if (targetTile.getPiece() != null) {
			if (targetTile.getPiece().getColor() == color) {
				notFriendlyFire = false;
			} 
		}

		if (notFriendlyFire) {			
			//THIS IS NOT ACCURATE CHANGE TO MAKE IT ONE SPACE IN EACH DIRECTION
			if (((y - targetTile.getTopY() > 0 && y - targetTile.getTopY() <= 100) && (x - targetTile.getLeftX() < 100 && x - targetTile.getLeftX() > -100)) // NORTH
				    || ((y - targetTile.getTopY() >= -100 && y - targetTile.getTopY() < 0) && (x - targetTile.getLeftX() < 100 && x - targetTile.getLeftX() > -100)) // SOUTH
				    || ((x - targetTile.getLeftX() > 0 && x - targetTile.getLeftX() <= 100) && (y - targetTile.getTopY() < 100 && y - targetTile.getTopY() > -100)) // EAST
				    || ((x - targetTile.getLeftX() >= -100 && x - targetTile.getLeftX() < 0) && (y - targetTile.getTopY() < 100 && y - targetTile.getTopY() > -100)) // WEST
				    || ((y - targetTile.getTopY() <= 100 && y - targetTile.getTopY() > 0) && x - targetTile.getLeftX() == y - targetTile.getTopY()) // NORTHEAST
				    || ((y - targetTile.getTopY() <= 100 && y - targetTile.getTopY() > 0) && -(x - targetTile.getLeftX()) == y - targetTile.getTopY()) // NORTHWEST
				    || ((y - targetTile.getTopY() < 0 && y - targetTile.getTopY() >= -100) && -(x - targetTile.getLeftX()) == y - targetTile.getTopY()) // SOUTHEAST
				    || ((y - targetTile.getTopY() < 0 && y - targetTile.getTopY() >= -100) && x - targetTile.getLeftX() == y - targetTile.getTopY())) { // SOUTHWEST
				if (tryAttack(targetTile)) {
					return true;
				} else {
					while (!checkStorage.isEmpty()) {
						checkStorage.pop().setNotCheckable();
					}
					currentTile.setNotResidentKing();
					targetTile.occupyTile(this);
					
					
					currentTile = targetTile;
					this.x = targetTile.getLeftX();
					this.y = targetTile.getTopY();
					oldTile.unoccupyTile();
					
					Tile[][] tiles = TileManager.getTiles();
					for (Tile[] tileArray : tiles) {
						for (Tile tile : tileArray) {
							if((tile.getLeftX() == x - 100) && (tile.getTopY() == y - 100)
									|| (tile.getLeftX() == x - 100) && (tile.getTopY() == y)
									|| (tile.getLeftX() == x - 100) && (tile.getTopY() == y + 100)
									|| (tile.getLeftX() == x) && (tile.getTopY() == y - 100)
								
									|| (tile.getLeftX() == x) && (tile.getTopY() == y + 100)
									||(tile.getLeftX() == x + 100) && (tile.getTopY() == y - 100)
									|| (tile.getLeftX() == x + 100) && (tile.getTopY() == y)
									|| (tile.getLeftX() == x + 100) && (tile.getTopY() == y + 100)) {
								tile.setCheckable();
								checkStorage.push(tile);
							}
							if ((tile.getLeftX() == x) && (tile.getTopY() == y)) {
								tile.setResidentKing();
							}
						}
					}
					
					chessPanel.callDraw();
					return true;
				}
			}
		} 
		return false;
	}
	
	public boolean tryAttack(Tile targetTile) {
		Piece targetPiece = targetTile.getPiece();
		if (targetPiece != null) {
			if (targetPiece.getColor() != color) {
				Tile oldTile = currentTile;
				targetTile.occupyTileByForce(this);
				currentTile = targetTile;
				oldTile.unoccupyTile();
				targetPiece.kill();
				this.x = targetTile.getLeftX();
				this.y = targetTile.getTopY();
				chessPanel.callDraw();
		
				return true;
			}
		}
		return false; //returns false if a move doesn't occur.
	}
}
