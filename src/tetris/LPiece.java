package tetris;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LPiece extends Tetrimino {

    public LPiece(Rectangle[][] squares) {
        super(squares, 2, new int[][] {{0,0},{0,1},{0,2},{1,2}}, Color.ORANGE);
    }
}