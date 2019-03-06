package tetris;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LPiece extends Tetrimino {

    public LPiece(TetrisBoard board) {
        super(board, 2, new int[][] {{0,0},{0,1},{0,2},{1,2}}, new Color(1,0.6,0.227,1));
    }
}