package pieces;

import java.io.IOException;

import javax.imageio.ImageIO;

import tiles.Tile;
import tiles.TileManager;

public class Rook extends Piece {

	public Rook(PieceColor color, int x, int y) throws IOException {
		super(color, x, y, "/pieces/White_Rook.png", "/pieces/Black_Rook.png", "/pieces/test.png");
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
			//Check what direction the piece is going
			//Check collision in the direction the piece is going
			//if there is a valid direction and no collision, set canMove to true
			
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
				} else {
					return false;
				}
			}
			
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
