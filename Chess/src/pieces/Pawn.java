package pieces;

import java.io.IOException;
import java.util.Stack;

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
	public void showPathing() {
		System.out.println("pathing");
		
		int targetColumn = (x / 100);
		int currentMin = 0;
		int currentMax = 800;
		
	
		for (int i = 0; i < tiles[0].length; ++i)	{
			if (color == PieceColor.WHITE) { 
				if (tiles[i][targetColumn].getTopY() < y && currentMin < y && !tiles[i][targetColumn].isOccupied() && (y - tiles[i][targetColumn].getTopY()) <= maxDistance * firstTurnMultiplier) {
					if (checkCollision(MovementDirection.NORTH, x, y, tiles[i][targetColumn].getLeftX(), tiles[i][targetColumn].getTopY() )) {
						tiles[i][targetColumn].setPathable();
						pathingStorage.push(new int[]{i, targetColumn});
					} else {
						currentMin = tiles[i][targetColumn].getTopY();
					}
				}
			} else {
				if (tiles[i][targetColumn].getTopY() > y && currentMax > y  && !tiles[i][targetColumn].isOccupied() && -(y - tiles[i][targetColumn].getTopY()) <= maxDistance * firstTurnMultiplier) {
					if (checkCollision(MovementDirection.SOUTH, x, y,tiles[i][targetColumn].getLeftX() ,tiles[i][targetColumn].getTopY() )) {
						tiles[i][targetColumn].setPathable();
						pathingStorage.push(new int[]{i, targetColumn});
					} else {
						currentMin = tiles[i][targetColumn].getTopY();
					}
				}
			}
			
			for (int j = 0; j < tiles.length; j++) {
				if (((color == PieceColor.BLACK && y - tiles[i][j].getTopY() == -100) 
						|| color == PieceColor.WHITE && y - tiles[i][j].getTopY() == 100)
						&& (x - tiles[i][j].getLeftX() == 100 || x - tiles[i][j].getLeftX() == -100)) {
					
					if (tiles[i][j].isOccupied()) {
						if (tiles[i][j].getPiece().getColor() != color) {
							if (tiles[i][j].isResidentKing()) {
								tiles[i][j].setInCheck();
							} else {
								tiles[i][j].setTargetable();
								targetingStorage.push(new int[]{i, j});
							}
						}
					} 
					if (tiles[i][j].isCheckable()) {
						tiles[i][j].setChecked();
					}
				}
			}
		} 	
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
		if (targetTile.getPiece() != null && targetTile.getPiece().getColor() != color) {
			targetPiece = targetTile.getPiece(); //the piece being attacked
			//checks that the target tile is one space in front and to the left or right of the piece (taking piece color into account)
			//also ensures that the pawn doesn't attack it's own team
			if (((color == PieceColor.BLACK && y - targetTile.getTopY() == -100) 
					|| color == PieceColor.WHITE && y - targetTile.getTopY() == 100)
					&& (x - targetTile.getLeftX() == 100 || x - targetTile.getLeftX() == -100))
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
