package pieces;

import java.io.IOException;

import javax.imageio.ImageIO;

import board.ChessPanel;
import tiles.Tile;
import tiles.TileManager;

public class Pawn extends Piece{
	//manage pawns unique ability to move 2 spaces on its first turn.
	int firstTurnMultiplier = 2;
	
	int maxDistance = 100; //this is multiplied by the firstTurnMultiplier so it can be 100 by default.
	
	public Pawn(PieceColor color, int x, int y) throws IOException {
		super(color, x, y, "/pieces/White_Pawn.png", "/pieces/Black_Pawn.png");
	}
	
	@Override
	public boolean move(int targetX, int targetY) {
		System.out.println("moving pawn");
		Tile oldTile = currentTile;
		Tile targetTile = TileManager.findTile(targetX, targetY);

		if (targetTile.getPiece() == null) {
			//makes sure that the pawn can only move forward and only by 1-2 spaces
			if (((color == PieceColor.BLACK && y - targetTile.getTopY() >= -100 * firstTurnMultiplier && y - targetTile.getTopY() <= 0) 
					|| (color == PieceColor.WHITE && targetTile.getTopY() - y >= -100 * firstTurnMultiplier &&  targetTile.getTopY() - y <= 0))
					&& (x - targetTile.getLeftX() < 100 && x - targetTile.getLeftX() > -100)) {
				//check collision for white pieces
				if (color == PieceColor.WHITE) {
					if (!checkCollision(MovementDirection.NORTH, x, y, targetTile.getLeftX(), targetTile.getTopY())) {
						return false;
					}
				} 
				//check collision for black pieces
				else if (color == PieceColor.BLACK) {
					if (!checkCollision(MovementDirection.SOUTH, x, y, targetTile.getLeftX(), targetTile.getTopY())) {
						return false;
					}
				}
				//take an unoccupied tile
				targetTile.occupyTile(this);
				currentTile = targetTile;
				this.x = targetTile.getLeftX();
				this.y = targetTile.getTopY();
				oldTile.unoccupyTile();
				
				chessPanel.callDraw();
				firstTurnMultiplier = 1;
				return true;
			} 
		} else {
			if (tryAttack(targetTile)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean tryAttack(Tile targetTile) {
		Piece targetPiece;
		if (targetTile.getPiece() != null) {
			targetPiece= targetTile.getPiece();
			if ((targetPiece.getColor() != color) && ((color == PieceColor.BLACK && y - targetTile.getTopY() >= -100 && y - targetTile.getTopY() <= 0) 
					|| (color == PieceColor.WHITE && targetTile.getTopY() - y >= -100 &&  targetTile.getTopY() - y <= 0))
					&& ((x - targetTile.getLeftX() > 0 && x - targetTile.getLeftX() <= 100) || (x - targetTile.getLeftX() <= -100 && x - targetTile.getLeftX() > -200)))
				{
				
				Tile oldTile = currentTile;
				targetTile.occupyTileByForce(this);
				currentTile = targetTile;
				oldTile.unoccupyTile();
				targetPiece.kill();
				this.x = targetTile.getLeftX();
				this.y = targetTile.getTopY();
				chessPanel.callDraw();
				
				
				System.out.println("attack");
				firstTurnMultiplier = 1;//ensures that if the pawn attacks before moving it can't move 
				return true;
			}
		}
		return false; //returns false if a move doesn't occur.
	}
}
