package tetris;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Tetrimino which is 4 blocks in a straight line making a '|' shape
 */
public class IPiece extends Tetrimino{
    public IPiece (Rectangle[][] squares) {
        super(squares, 3, new int[][] {{1,0},{1,1},{1,2},{1,3}}, Color.CYAN);
    }
}
