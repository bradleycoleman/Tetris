package tetris;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class OPiece extends Tetrimino {

    public OPiece(TetrisBoard board) {
        super(board, 1, new int[][] {{0,0},{0,1},{1,1},{1,0}}, Color.YELLOW);
    }
}