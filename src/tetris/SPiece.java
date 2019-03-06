package tetris;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SPiece extends Tetrimino {

    public SPiece(TetrisBoard board) {
        super(board, 2, new int[][] {{0,0},{0,1},{1,1},{1,2}}, new Color(0.184, 0.918, 0.118,1));
    }
}
