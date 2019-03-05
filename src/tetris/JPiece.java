package tetris;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class JPiece extends Tetrimino {

    public JPiece(Rectangle[][] squares) {
        super(squares, 2, new int[][] {{1,2},{2,0},{2,1},{2,2}}, new Color(0.227, 0.318,1,1));
    }
}
