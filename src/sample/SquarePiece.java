package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SquarePiece extends Tetrimino {

    public SquarePiece (Rectangle[][] squares) {
        super(squares);
    }

    protected Boolean checkValid(Angle arrowKeyPress) {
        try {
            if (arrowKeyPress == Angle.UP) {
                return true;
            } else if (arrowKeyPress == Angle.RIGHT) {
                if (_squares[_xOrd + 2][_yOrd].getFill().equals(Color.WHITE) && _squares[_xOrd + 2][_yOrd + 1].getFill().equals(Color.WHITE)) {
                    return true;
                }
            } else if (arrowKeyPress == Angle.LEFT) {
                if (_squares[_xOrd-1][_yOrd].getFill().equals(Color.WHITE) && _squares[_xOrd-1][_yOrd + 1].getFill().equals(Color.WHITE)) {
                    return true;
                }
            } else if (arrowKeyPress == Angle.DOWN) {
                if (_squares[_xOrd][_yOrd+2].getFill().equals(Color.WHITE) && _squares[_xOrd+1][_yOrd+2].getFill().equals(Color.WHITE)) {
                    return true;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }

    protected void unpaint() {

    }

    /**
     * This method paints the tetrimino on the board using the current x and y coordinates and orientation.
     */
    protected void repaint() {

    }
}
