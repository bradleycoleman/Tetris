package tetris;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SPiece extends Tetrimino {

    public SPiece(Rectangle[][] squares) {
        super(squares, 2, new int[][] {{0,0},{0,1},{1,1},{1,2}}, Color.GREEN);
    }
}
