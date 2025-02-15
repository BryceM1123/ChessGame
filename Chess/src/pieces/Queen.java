package pieces;

import java.io.IOException;

import javax.imageio.ImageIO;

import tiles.Tile;
import tiles.TileManager;

public class Queen extends Piece{ 
	
	public Queen(PieceColor color, int x, int y) throws IOException {
		super(color, x, y, "/pieces/White_Queen.png", "/pieces/Black_Queen.png", "/pieces/test.png");
	}
	
	@Override
	public void showPathing() {
		for (int i = 0; i < tiles.length; ++i)	{
			for (int j = 0; j < tiles[i].length; ++j) {
				if ((y - tiles[i][j].getTopY() > 0 &&  y -  tiles[i][j].getTopY() <= 800)
						&& (x - tiles[i][j].getLeftX() < 100 && x - tiles[i][j].getLeftX() > -100)) {
					if (checkCollision(MovementDirection.NORTH, x, y, tiles[i][j].getLeftX() ,tiles[i][j].getTopY())) {
						if (tiles[i][j].isOccupied()) {
							if (tiles[i][j].getPiece().getColor() != color) {
								if (tiles[i][j].isResidentKing()) {
									tiles[i][j].setInCheck();
								} else {
								tiles[i][j].setTargetable();
								targetingStorage.push(new int[]{i, j});
								}
							}
						} else {
							if (tiles[i][j].isCheckable()) {
								tiles[i][j].setChecked();
							}
							tiles[i][j].setPathable();
							pathingStorage.push(new int[]{i, j});
						}
					} 
				}
				else if ((y - tiles[i][j].getTopY() >= -800 && y - tiles[i][j].getTopY() < 0)
						&& (x - tiles[i][j].getLeftX() < 100 && x - tiles[i][j].getLeftX() > -100)) {
					
					if (checkCollision(MovementDirection.SOUTH, x, y, tiles[i][j].getLeftX(), tiles[i][j].getTopY())) {
						if (tiles[i][j].isOccupied()) {
							if (tiles[i][j].getPiece().getColor() != color) {
								if (tiles[i][j].isResidentKing()) {
									tiles[i][j].setInCheck();
								} else {
								tiles[i][j].setTargetable();
								targetingStorage.push(new int[]{i, j});
								}
							}
						} else {
							if (tiles[i][j].isCheckable()) {
								tiles[i][j].setChecked();
							}
							tiles[i][j].setPathable();
							pathingStorage.push(new int[]{i, j});
						}
					}
				}
				else if ((x - tiles[i][j].getLeftX() > 0 && x - tiles[i][j].getLeftX() <= 800)
						&& (y - tiles[i][j].getTopY() < 100 &&  y -  tiles[i][j].getTopY() > -100)) {
					if (checkCollision(MovementDirection.EAST, x, y, tiles[i][j].getLeftX(), tiles[i][j].getTopY())) {
						if (tiles[i][j].isOccupied()) {
							if (tiles[i][j].getPiece().getColor() != color) {
								if (tiles[i][j].isResidentKing()) {
									tiles[i][j].setInCheck();
								} else {
								tiles[i][j].setTargetable();
								targetingStorage.push(new int[]{i, j});
								}
							}
						} else {
							if (tiles[i][j].isCheckable()) {
								tiles[i][j].setChecked();
							}
							tiles[i][j].setPathable();
							pathingStorage.push(new int[]{i, j});
						}
					}
				}
				//
				else if ((x - tiles[i][j].getLeftX() >= -800 && x - tiles[i][j].getLeftX() < 0)
						&& (y - tiles[i][j].getTopY() < 100 &&  y -  tiles[i][j].getTopY() > -100)) {
					if (checkCollision(MovementDirection.WEST, x, y, tiles[i][j].getLeftX(), tiles[i][j].getTopY())) {
						if (tiles[i][j].isOccupied()) {
							if (tiles[i][j].getPiece().getColor() != color) {
								if (tiles[i][j].isResidentKing()) {
									tiles[i][j].setInCheck();
								} else {
								tiles[i][j].setTargetable();
								targetingStorage.push(new int[]{i, j});
								}
							}
						} else {
							if (tiles[i][j].isCheckable()) {
								tiles[i][j].setChecked();
							}
							tiles[i][j].setPathable();
							pathingStorage.push(new int[]{i, j});
						}
					}
				}
				else if ((y - tiles[i][j].getTopY() <= 700 && y - tiles[i][j].getTopY() > 0) && x - tiles[i][j].getLeftX() == y - tiles[i][j].getTopY()) {
					if (checkCollision(MovementDirection.NORTHEAST, x, y, tiles[i][j].getLeftX(), tiles[i][j].getTopY())) {
						if (tiles[i][j].isOccupied()) {
							if (tiles[i][j].getPiece().getColor() != color) {
								if (tiles[i][j].isResidentKing()) {
									tiles[i][j].setInCheck();
								} else {
								tiles[i][j].setTargetable();
								targetingStorage.push(new int[]{i, j});
								}
							}
						} else {
							if (tiles[i][j].isCheckable()) {
								tiles[i][j].setChecked();
							}
							tiles[i][j].setPathable();
							pathingStorage.push(new int[]{i, j});
						}
					}
				}
				else if ((y - tiles[i][j].getTopY() <= 700 && y - tiles[i][j].getTopY() > 0) && -(x - tiles[i][j].getLeftX()) == y - tiles[i][j].getTopY()) {
					if (checkCollision(MovementDirection.NORTHWEST, x, y, tiles[i][j].getLeftX(), tiles[i][j].getTopY())) {
						if (tiles[i][j].isOccupied()) {
							if (tiles[i][j].getPiece().getColor() != color) {
								if (tiles[i][j].isResidentKing()) {
									tiles[i][j].setInCheck();
								} else {
								tiles[i][j].setTargetable();
								targetingStorage.push(new int[]{i, j});
								}
							}
						} else {
							if (tiles[i][j].isCheckable()) {
								tiles[i][j].setChecked();
							}
							tiles[i][j].setPathable();
							pathingStorage.push(new int[]{i, j});
						}
					}
				}
				else if ((y - tiles[i][j].getTopY() < 0 && y - tiles[i][j].getTopY() >= -700) && -(x - tiles[i][j].getLeftX()) == y - tiles[i][j].getTopY()) {
					if (checkCollision(MovementDirection.SOUTHEAST, x, y, tiles[i][j].getLeftX(), tiles[i][j].getTopY())) {
						if (tiles[i][j].isOccupied()) {
							if (tiles[i][j].getPiece().getColor() != color) {
								if (tiles[i][j].isResidentKing()) {
									tiles[i][j].setInCheck();
								} else {
								tiles[i][j].setTargetable();
								targetingStorage.push(new int[]{i, j});
								}
							}
						} else {
							if (tiles[i][j].isCheckable()) {
								tiles[i][j].setChecked();
							}
							tiles[i][j].setPathable();
							pathingStorage.push(new int[]{i, j});
						}
					}
				}
				else if ((y - tiles[i][j].getTopY() < 0 && y - tiles[i][j].getTopY() >= -700) && x - tiles[i][j].getLeftX() == y - tiles[i][j].getTopY()) {
					if (checkCollision(MovementDirection.SOUTHWEST, x, y, tiles[i][j].getLeftX(), tiles[i][j].getTopY())) {
						if (tiles[i][j].isOccupied()) {
							if (tiles[i][j].getPiece().getColor() != color) {
								if (tiles[i][j].isResidentKing()) {
									tiles[i][j].setInCheck();
								} else {
								tiles[i][j].setTargetable();
								targetingStorage.push(new int[]{i, j});
								}
							}
						} else {
							if (tiles[i][j].isCheckable()) {
								tiles[i][j].setChecked();
							}
							tiles[i][j].setPathable();
							pathingStorage.push(new int[]{i, j});
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean move(int targetX, int targetY) {
		Tile oldTile = currentTile;
		Tile targetTile = TileManager.findTile(targetX, targetY);
		boolean canMove = false;
		MovementDirection direction;
		boolean notFriendlyFire = true;
		
		//preemptively checks that the target tile does not contain a piece of the same color
		if (targetTile.getPiece() != null) {
			if (targetTile.getPiece().getColor() == color) {
				notFriendlyFire = false;
			} 
		}

		if (notFriendlyFire) {
			//check if the movement is valid
			//if valid, assign a movement direction for path checking
			//NORTH
			
			if ((y - targetTile.getTopY() > 0 &&  y -  targetTile.getTopY() <= 800)
					&& (x - targetTile.getLeftX() < 100 && x - targetTile.getLeftX() > -100)) {
				direction = MovementDirection.NORTH;
				if (checkCollision(direction, x, y, targetTile.getLeftX(), targetTile.getTopY())) {
					canMove = true; 
				}
			}
			
			//SOUTH
			else if ((y - targetTile.getTopY() >= -800 && y - targetTile.getTopY() < 0)
					&& (x - targetTile.getLeftX() < 100 && x - targetTile.getLeftX() > -100)) {
				direction = MovementDirection.SOUTH;
				if (checkCollision(direction, x, y, targetTile.getLeftX(), targetTile.getTopY())) {
					canMove = true; 
				}
			}	
			
			//EAST
			else if ((x - targetTile.getLeftX() > 0 && x - targetTile.getLeftX() <= 800)
					&& (y - targetTile.getTopY() < 100 &&  y -  targetTile.getTopY() > -100)) {
				direction = MovementDirection.EAST;
				if (checkCollision(direction, x, y, targetTile.getLeftX(), targetTile.getTopY())) {
					canMove = true; 
				} 
			}
			
			//WEST 
			else if ((x - targetTile.getLeftX() >= -800 && x - targetTile.getLeftX() < 0)
					&& (y - targetTile.getTopY() < 100 &&  y -  targetTile.getTopY() > -100)) {
				direction = MovementDirection.EAST;
				if (checkCollision(direction, x, y, targetTile.getLeftX(), targetTile.getTopY())) {
					canMove = true; 
				} 
			}
			
			//NORTHEAST
			if ((y - targetTile.getTopY() <= 700 && y - targetTile.getTopY() > 0) && x - targetTile.getLeftX() == y - targetTile.getTopY()) {
				System.out.println("1");
				direction = MovementDirection.NORTHEAST;
				if (checkCollision(direction, x, y, targetTile.getLeftX(), targetTile.getTopY())) {
					canMove = true; 
				}
			}
			
			//NORTHWEST
			else if ((y - targetTile.getTopY() <= 700 && y - targetTile.getTopY() > 0) && -(x - targetTile.getLeftX()) == y - targetTile.getTopY()) {
				System.out.println("3");
				direction = MovementDirection.NORTHWEST;
				if (checkCollision(direction, x, y, targetTile.getLeftX(), targetTile.getTopY())) {
					canMove = true; 
				}
			}
			
			//SOUTHEAST
			else if((y - targetTile.getTopY() < 0 && y - targetTile.getTopY() >= -700) && -(x - targetTile.getLeftX()) == y - targetTile.getTopY()) {
				System.out.println("4");
				direction = MovementDirection.SOUTHEAST;
				if (checkCollision(direction, x, y, targetTile.getLeftX(), targetTile.getTopY())) {
					canMove = true; 
				}
			}
			
			//SOUTHWEST
			else if ((y - targetTile.getTopY() < 0 && y - targetTile.getTopY() >= -700) && x - targetTile.getLeftX() == y - targetTile.getTopY()) {
				System.out.println("2");
				direction = MovementDirection.SOUTHWEST;
				if (checkCollision(direction, x, y, targetTile.getLeftX(), targetTile.getTopY())) {
					canMove = true; 
				} 
			}
			
			//if the movement is valid:
			if (canMove) {
				if (tryAttack(targetTile)) {
					return true;
				} else {
					targetTile.occupyTile(this);
					currentTile = targetTile;
					this.x = targetTile.getLeftX();
					this.y = targetTile.getTopY();
					oldTile.unoccupyTile();
					
					chessPanel.callDraw();
					return true;
				}
			} else if (!canMove) {
				return false;
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
