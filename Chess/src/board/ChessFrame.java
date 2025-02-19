package board;

import javax.swing.JFrame;

public final class ChessFrame extends JFrame {
	public static final ChessFrame chessFrame = new ChessFrame();
	
	//Creates the frame for the game to be placed inside.
	private ChessFrame() {
		this.add(ChessPanel.chessPanel); //creates the panel
		this.setTitle("Chess");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false); //implement resizability later?
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
