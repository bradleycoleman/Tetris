package tetris;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ZPiece extends Tetrimino {

    public ZPiece(TetrisBoard board) {
        super(board, 2, new int[][] {{1,0},{0,1},{1,1},{0,2}}, Color.RED);
    }
}