package tetris;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TPiece extends Tetrimino {

    public TPiece(Rectangle[][] squares) {
        super(squares, 2, new int[][] {{0,2},{1,2},{2,2},{1,1}}, Color.MAGENTA);
    }
}