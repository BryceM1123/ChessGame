package pieces;

import java.io.IOException;

import javax.imageio.ImageIO;

import tiles.Tile;
import tiles.TileManager;

public class King extends Piece {
	
	public King(PieceColor color, int x, int y) throws IOException {
		super(color, x, y, "/pieces/White_King.png", "/pieces/Black_King.png", "/pieces/test.png");
	}
	
	@Override
	public void showPathing() {
		for (int i = 0; i < tiles.length; i++)	{
			for (int j = 0; j < tiles[i].length; j++) {
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
					targetTile.occupyTile(this);
					currentTile = targetTile;
					this.x = targetTile.getLeftX();
					this.y = targetTile.getTopY();
					oldTile.unoccupyTile();
					
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
