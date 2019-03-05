package tetris;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static tetris.BoardSceneController.BLANKCOLOUR;

/**
 * This represents the active piece in the game of tetris. The piece is confined within a grid of squares _dim x _dim.
 * The top left corner square of this grid is at the listed _xOrd and _yOrd on the grid _squares. The shape of the
 * tetrimino within its grid is defined by the set of co ordinates _coOrds. These co-ordinates are relative to the top
 * left corner of the tetrimino's grid and determine which squares in the grid to be coloured with _colour.
 */
public abstract class Tetrimino {
    private int _xOrd;
    private int _yOrd;
    private int _dim;
    private Color _colour;
    private Angle _orientation;
    private Rectangle[][] _squares;
    private static final Angle[] ANGLES = Angle.values();
    private int[][] _coOrds;

    protected enum Angle {UP, RIGHT, DOWN, LEFT}

    public Tetrimino(Rectangle[][] squares, int dim, int[][] coOrds, Color colour) throws ArrayIndexOutOfBoundsException {
        _squares = squares;
        _xOrd = squares.length/2;
        _yOrd = 0;
        _orientation = Angle.RIGHT;
        _dim = dim;
        _coOrds = coOrds;
        _colour = colour;
        if (!moveDown()) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Moves the piece one index down in the grid. If this is not possible then the tetrimino stays put and false is
     * returned. Otherwise returns true.
     */
    public boolean moveDown() {
        repaint(BLANKCOLOUR);
        _yOrd++;
        if (checkValid()) {
            repaint(_colour);
            return true;
        }
        _yOrd--;
        repaint(_colour);
        return false;
    }

    /**
     * Moves the piece one index left in the grid. If this is not possible then nothing happens
     */
    public void moveLeft() {
        repaint(BLANKCOLOUR);
        _xOrd--;
        if (!checkValid()) {
            _xOrd++;
        }
        repaint(_colour);
    }

    /**
     * Moves the piece one index right in the grid. If this is not possible then nothing happens
     */
    public void moveRight() {
        repaint(BLANKCOLOUR);
        _xOrd++;
        if (!checkValid()) {
            _xOrd--;
        }
        repaint(_colour);
    }

    /**
     * Rotates the piece in it's square by 90 degrees clockwise.
     */
    public void rotate() {
        repaint(BLANKCOLOUR);
        _orientation = ANGLES[(_orientation.ordinal() + 1) % ANGLES.length];
        if (!checkValid()) {
            _orientation = ANGLES[(_orientation.ordinal() + 3) % ANGLES.length];
        }
        repaint(_colour);
    }

    /**
     * This method paints the tetrimino on the board using the current x and y coOrdinates and orientation in the
     * specified colour.
     */
    private void repaint(Color colour) {
        switch (_orientation) {
            case UP:
                for (int[] coOrd : _coOrds) {
                    _squares[_xOrd + coOrd[0]][_yOrd + coOrd[1]].setFill(colour);
                }
                break;
            case RIGHT:
                for (int[] coOrd : _coOrds) {
                    _squares[_xOrd + _dim - coOrd[1]][_yOrd + coOrd[0]].setFill(colour);
                }
                break;
            case DOWN:
                for (int[] coOrd : _coOrds) {
                    _squares[_xOrd + _dim - coOrd[0]][_yOrd + _dim - coOrd[1]].setFill(colour);
                }
                break;
            case LEFT:
                for (int[] coOrd : _coOrds) {
                    _squares[_xOrd + coOrd[1]][_yOrd + _dim - coOrd[0]].setFill(colour);
                }
                break;
        }
    }

    /**
     * This method checks if the tetrimino would be safe to paint given its current orientation and location. It is
     * unsafe to paint if any of the tetrimino would be painted outside the limits of _squares, or otherwise if any
     * of the tetrimino goes over an already painted (non-BLANKCOLOUR) square.
     * @return false if not safe, true otherwise.
     */
    private boolean checkValid() {
        try {
            switch (_orientation) {
                case UP:
                    for (int[] coOrd : _coOrds) {
                        if (!_squares[_xOrd + coOrd[0]][_yOrd + coOrd[1]].getFill().equals(BLANKCOLOUR)) {
                            return false;
                        }
                    }
                    break;
                case RIGHT:
                    for (int[] coOrd : _coOrds) {
                        if (!_squares[_xOrd + _dim - coOrd[1]][_yOrd + coOrd[0]].getFill().equals(BLANKCOLOUR)) {
                            return false;
                        }
                    }
                    break;
                case DOWN:
                    for (int[] coOrd : _coOrds) {
                        if (!_squares[_xOrd + _dim - coOrd[0]][_yOrd + _dim - coOrd[1]].getFill().equals(BLANKCOLOUR)) {
                            return false;
                        }
                    }
                    break;
                case LEFT:
                    for (int[] coOrd : _coOrds) {
                        if (!_squares[_xOrd + coOrd[1]][_yOrd + _dim - coOrd[0]].getFill().equals(BLANKCOLOUR)) {
                            return false;
                        }
                    }
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }


}
