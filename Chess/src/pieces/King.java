package pieces;

import java.io.IOException;

import javax.imageio.ImageIO;

public class King extends Piece {
	public King(PieceColor color, int x, int y) throws IOException {
		super(color, x, y, "/pieces/White_King.png", "/pieces/Black_King.png");
	}
	@Override
	public boolean move(int targetX, int targetY) {
		return false;
	}
}
