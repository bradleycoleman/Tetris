package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Tetrimino which is 4 blocks in a straight line making a '|' shape
 */
public class IPiece extends Tetrimino{
    public IPiece (Rectangle[][] squares) {
        super(squares);
    }

    @Override
    protected Boolean checkValid(Angle arrowKeyPress) {
        try {
            if (arrowKeyPress.equals(Angle.UP)) {
                // finding what the next orientation will be once up is pressed
                Angle nextOrientation = ANGLES[(_orientation.ordinal() + 1) % ANGLES.length];
                // if the next orientation is vertical then check if the vertical squares exist and are empty
                if (nextOrientation == Angle.DOWN || nextOrientation == Angle.UP) {
                    if (_squares[_xOrd][_yOrd + 1].getFill().equals(Color.WHITE) && _squares[_xOrd][_yOrd + 2].getFill().equals(Color.WHITE)
                            && _squares[_xOrd][_yOrd + 3].getFill().equals(Color.WHITE)) {
                        return true;
                    }
                }
                // if the next orientation is horizontal then check if the horizontal squares exist and are empty
                else if (nextOrientation == Angle.RIGHT || nextOrientation == Angle.LEFT) {
                    if (_squares[_xOrd + 1][_yOrd].getFill().equals(Color.WHITE) && _squares[_xOrd + 2][_yOrd].getFill().equals(Color.WHITE)
                            && _squares[_xOrd + 3][_yOrd].getFill().equals(Color.WHITE)) {
                        return true;
                    }
                }
            } else if (arrowKeyPress.equals(Angle.DOWN)) {
                if (_orientation == Angle.DOWN || _orientation == Angle.UP) {
                    // if the piece is falling vertically then only the next square beneath need be checked
                    if (_squares[_xOrd][_yOrd + 4].getFill().equals(Color.WHITE)) {
                        return true;
                    }
                } else {
                    // if the piece is falling horizontally then the 4 squares directly below the ipiece need to be checked
                    if (_squares[_xOrd][_yOrd + 1].getFill().equals(Color.WHITE) && _squares[_xOrd + 1][_yOrd + 1].getFill().equals(Color.WHITE)
                            && _squares[_xOrd + 2][_yOrd + 1].getFill().equals(Color.WHITE) && _squares[_xOrd + 3][_yOrd + 1].getFill().equals(Color.WHITE)) {
                        return true;
                    }
                }
            } else if (arrowKeyPress.equals(Angle.RIGHT)) {
                if (_orientation == Angle.DOWN || _orientation == Angle.UP) {
                    // if the piece is vertical then all four squares directly to the right of the piece must be checked
                    if (_squares[_xOrd + 1][_yOrd].getFill().equals(Color.WHITE) && _squares[_xOrd + 1][_yOrd + 1].getFill().equals(Color.WHITE)
                            && _squares[_xOrd + 1][_yOrd + 2].getFill().equals(Color.WHITE) && _squares[_xOrd + 1][_yOrd + 3].getFill().equals(Color.WHITE)) {
                        return true;
                    }
                } else {
                    // if the piece is horizontal then only the square to the right of the piece need be checked
                    if (_squares[_xOrd + 4][_yOrd].getFill().equals(Color.WHITE)) {
                        return true;
                    }
                }
            } else if (arrowKeyPress.equals(Angle.LEFT)) {
                if (_orientation == Angle.DOWN || _orientation == Angle.UP) {
                    // if the piece is vertical then all four squares directly to the left of the piece must be checked
                    if (_squares[_xOrd - 1][_yOrd].getFill().equals(Color.WHITE) && _squares[_xOrd - 1][_yOrd + 1].getFill().equals(Color.WHITE)
                            && _squares[_xOrd - 1][_yOrd + 2].getFill().equals(Color.WHITE) && _squares[_xOrd - 1][_yOrd + 3].getFill().equals(Color.WHITE)) {
                        return true;
                    }
                } else {
                    // if the piece is horizontal then only the square to the left of the piece need be checked
                    if (_squares[_xOrd - 1][_yOrd].getFill().equals(Color.WHITE)) {
                        return true;
                    }
                }
            }
            // all previous checks determine if the squares the piece is moving to will be white. This catches excpetions
            // for when the move is to squares that don't exist and make the move invalid.
        } catch(ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }

    @Override
    protected void repaint() {
            if (_orientation == Angle.UP || _orientation == Angle.DOWN) {
                if (_squares[_xOrd][_yOrd].getFill().equals(Color.WHITE) && _squares[_xOrd][_yOrd + 1].getFill().equals(Color.WHITE)
                        && _squares[_xOrd][_yOrd + 2].getFill().equals(Color.WHITE) && _squares[_xOrd][_yOrd + 3].getFill().equals(Color.WHITE)) {
                    _squares[_xOrd][_yOrd].setFill(Color.AQUA);
                    _squares[_xOrd][_yOrd + 1].setFill(Color.AQUA);
                    _squares[_xOrd][_yOrd + 2].setFill(Color.AQUA);
                    _squares[_xOrd][_yOrd + 3].setFill(Color.AQUA);
                } else {
                    throw new ArrayIndexOutOfBoundsException();
                }
            } else {
                if (_squares[_xOrd][_yOrd].getFill().equals(Color.WHITE) && _squares[_xOrd + 1][_yOrd].getFill().equals(Color.WHITE)
                        && _squares[_xOrd + 2][_yOrd].getFill().equals(Color.WHITE) && _squares[_xOrd + 3][_yOrd].getFill().equals(Color.WHITE)) {
                    _squares[_xOrd][_yOrd].setFill(Color.AQUA);
                    _squares[_xOrd + 1][_yOrd].setFill(Color.AQUA);
                    _squares[_xOrd + 2][_yOrd].setFill(Color.AQUA);
                    _squares[_xOrd + 3][_yOrd].setFill(Color.AQUA);
                } else {
                    throw new ArrayIndexOutOfBoundsException();
                }
            }
    }

    @Override
    protected void unpaint() {
        if (_orientation == Angle.UP || _orientation == Angle.DOWN) {
            _squares[_xOrd][_yOrd].setFill(Color.WHITE);
            _squares[_xOrd][_yOrd + 1].setFill(Color.WHITE);
            _squares[_xOrd][_yOrd + 2].setFill(Color.WHITE);
            _squares[_xOrd][_yOrd + 3].setFill(Color.WHITE);
        } else {
            _squares[_xOrd][_yOrd].setFill(Color.WHITE);
            _squares[_xOrd + 1][_yOrd].setFill(Color.WHITE);
            _squares[_xOrd + 2][_yOrd].setFill(Color.WHITE);
            _squares[_xOrd + 3][_yOrd].setFill(Color.WHITE);
        }
    }
}
