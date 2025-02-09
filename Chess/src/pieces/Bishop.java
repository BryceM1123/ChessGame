package pieces;

import java.io.IOException;

import javax.imageio.ImageIO;

import tiles.Tile;
import tiles.TileManager;

public class Bishop extends Piece{
	
	public Bishop(PieceColor color, int x, int y) throws IOException {
		super(color, x, y, "/pieces/White_Bishop.png", "/pieces/Black_Bishop.png", "/pieces/test.png");
	}

	@Override
	public boolean move(int targetX, int targetY) {
		Tile oldTile = currentTile;
		Tile targetTile = TileManager.findTile(targetX, targetY);
		boolean canMove = false;
		MovementDirection direction;

		//NORTHEAST
		if ((y - targetTile.getTopY() <= 700 && y - targetTile.getTopY() > 0) && x - targetTile.getLeftX() == y - targetTile.getTopY()) {
			System.out.println("1");
			direction = MovementDirection.NORTHEAST;
			if (checkCollision(direction, x, y, targetTile.getLeftX(), targetTile.getTopY())) {
				canMove = true; 
			} else {
				return false;
			}
		}
		//NORTHWEST
		else if ((y - targetTile.getTopY() <= 700 && y - targetTile.getTopY() > 0) && -(x - targetTile.getLeftX()) == y - targetTile.getTopY()) {
			System.out.println("3");
			direction = MovementDirection.NORTHWEST;
			if (checkCollision(direction, x, y, targetTile.getLeftX(), targetTile.getTopY())) {
				canMove = true; 
			} else {
				return false;
			}
		}
		
		//SOUTHEAST
		else if((y - targetTile.getTopY() < 0 && y - targetTile.getTopY() >= -700) && -(x - targetTile.getLeftX()) == y - targetTile.getTopY()) {
			System.out.println("4");
			direction = MovementDirection.SOUTHEAST;
			if (checkCollision(direction, x, y, targetTile.getLeftX(), targetTile.getTopY())) {
				canMove = true; 
			} else {
				return false;
			}
		}
		//SOUTHWEST
		else if ((y - targetTile.getTopY() < 0 && y - targetTile.getTopY() >= -700) && x - targetTile.getLeftX() == y - targetTile.getTopY()) {
			System.out.println("2");
			direction = MovementDirection.SOUTHWEST;
			if (checkCollision(direction, x, y, targetTile.getLeftX(), targetTile.getTopY())) {
				canMove = true; 
			} else {
				return false;
			}
		}
		
		if (canMove) {
			if (targetTile.getPiece() != null) {
				if (targetTile.getPiece().getColor() != color) {
					if (tryAttack(targetTile)) {
						return true;
					}
				} 
				
				
			} else {
				//take an unoccupied tile
				targetTile.occupyTile(this);
				currentTile = targetTile;
				this.x = targetTile.getLeftX();
				this.y = targetTile.getTopY();
				oldTile.unoccupyTile();	
				chessPanel.callDraw();
				return true;
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
