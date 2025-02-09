package pieces;

import java.io.IOException;

import javax.imageio.ImageIO;

import board.ChessPanel;
import tiles.Tile;
import tiles.TileManager;

public class Pawn extends Piece{

	int firstTurnMultiplier = 2; //number of spaces pawn can move on its first turn
	int maxDistance = 100; //distance pawn can move per turn normally (100 = 1 tile)
	
	public Pawn(PieceColor color, int x, int y) throws IOException {
		super(color, x, y, "/pieces/White_Pawn.png", "/pieces/Black_Pawn.png", "/pieces/test.png");
	
	}
	
	@Override
	public boolean move(int targetX, int targetY) {
		Tile targetTile = TileManager.findTile(targetX, targetY); //the tile we want to move to

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
				Tile oldTile = currentTile;
				targetTile.occupyTile(this);
				currentTile = targetTile;
				this.x = targetTile.getLeftX();
				this.y = targetTile.getTopY();
				oldTile.unoccupyTile();
				
				chessPanel.callDraw();
				firstTurnMultiplier = 1; //ensures the pawn can only move 1 space after it's first turn
				return true;
			} 
		}
		//because the attack pattern for pawns is different than the movement pattern, it tries to attack after the attempt to move fails
		else {
			if (tryAttack(targetTile)) {
				return true; //move() returns true if the attack is successful
			}
		}
		return false; //returns false if the piece did not move
	}
	
	//attempts to attack a tile that has a resident piece
	public boolean tryAttack(Tile targetTile) {
		Piece targetPiece;
		if (targetTile.getPiece() != null) {
			targetPiece = targetTile.getPiece(); //the piece being attacked
			//checks that the target tile is one space in front and to the left or right of the piece (taking piece color into account)
			//also ensures that the pawn doesn't attack it's own team
			if ((targetPiece.getColor() != color) && ((color == PieceColor.BLACK && y - targetTile.getTopY() >= -100 && y - targetTile.getTopY() <= 0) 
					|| (color == PieceColor.WHITE && targetTile.getTopY() - y >= -100 &&  targetTile.getTopY() - y <= 0))
					&& ((x - targetTile.getLeftX() > 0 && x - targetTile.getLeftX() <= 100) || (x - targetTile.getLeftX() <= -100 && x - targetTile.getLeftX() > -200)))
				{
				
				//takes an occupied tile and disposes of the targetPiece
				Tile oldTile = currentTile;
				targetTile.occupyTileByForce(this);
				currentTile = targetTile;
				oldTile.unoccupyTile();
				targetPiece.kill();
				this.x = targetTile.getLeftX();
				this.y = targetTile.getTopY();
				chessPanel.callDraw();
				firstTurnMultiplier = 1;//ensures that if the pawn attacks before moving it can't move 
				return true;
			}
		}
		return false; //returns false if a move doesn't occur.
	}
}
