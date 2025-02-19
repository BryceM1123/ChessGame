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
								System.out.println("1");
								if (tiles[i][j].isResidentKing()) {
									System.out.println("2");
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
			}
		}
	}
}
