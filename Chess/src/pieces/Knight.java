package pieces;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import tiles.Tile;
import tiles.TileManager;

public class Knight extends Piece {
	
	public Knight(PieceColor color, int x, int y) throws IOException {
		
		super(color, x, y, "/pieces/White_Knight.png", "/pieces/Black_Knight.png", "/pieces/test.png");
	}
	
	@Override
	public void showPathing() {
		for (int i = 0; i < tiles.length; i++)	{
			for (int j = 0; j < tiles[i].length; j++) {
				if ((y - tiles[i][j].getTopY() == -200 && x - tiles[i][j].getLeftX() == 100) //down 2 left 1
						|| (y - tiles[i][j].getTopY() == -200 && x - tiles[i][j].getLeftX() == -100) //down 2 right 1
						|| (y - tiles[i][j].getTopY() == 200 && x - tiles[i][j].getLeftX() == 100) //up 2 left 1
						|| (y - tiles[i][j].getTopY() == 200 && x - tiles[i][j].getLeftX() == -100) //up 2 right 1 
						|| (y - tiles[i][j].getTopY() == -100 && x - tiles[i][j].getLeftX() == 200) //left 2 down 1
						|| (y - tiles[i][j].getTopY() == 100 && x - tiles[i][j].getLeftX() == 200) //left 2 up 1
						|| (y - tiles[i][j].getTopY() == -100 && x - tiles[i][j].getLeftX() == -200) //right 2 down 1
						|| (y - tiles[i][j].getTopY() == 100 && x - tiles[i][j].getLeftX() == -200)) {
						
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
			//makes sure that the pawn can only move forward and only by 1-2 spaces
		if ((y - targetTile.getTopY() == -200 && x - targetTile.getLeftX() == 100) //down 2 left 1
				|| (y - targetTile.getTopY() == -200 && x - targetTile.getLeftX() == -100) //down 2 right 1
				|| (y - targetTile.getTopY() == 200 && x - targetTile.getLeftX() == 100) //up 2 left 1
				|| (y - targetTile.getTopY() == 200 && x - targetTile.getLeftX() == -100) //up 2 right 1 
				|| (y - targetTile.getTopY() == -100 && x - targetTile.getLeftX() == 200) //left 2 down 1
				|| (y - targetTile.getTopY() == 100 && x - targetTile.getLeftX() == 200) //left 2 up 1
				|| (y - targetTile.getTopY() == -100 && x - targetTile.getLeftX() == -200) //right 2 down 1
				|| (y - targetTile.getTopY() == 100 && x - targetTile.getLeftX() == -200)){ //right 2 up 1
			if (targetTile.getPiece() != null) {
				System.out.println("piece is there");
				if (targetTile.getPiece().getColor() != color) {
					if (tryAttack(targetTile)) {
						return true;
					} else { //this else is probably not necessary
						return false;
					}
				} else {
					return false;
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
		Tile oldTile = currentTile;
		targetTile.getPiece().kill();
		targetTile.occupyTileByForce(this);
		currentTile = targetTile;
		oldTile.unoccupyTile();
		this.x = targetTile.getLeftX();
		this.y = targetTile.getTopY();
		chessPanel.callDraw();
		
		
		System.out.println("attack"); 
		return true;
	}
}
