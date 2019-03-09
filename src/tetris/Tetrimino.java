package tetris;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static tetris.TetrisBoard.BLANKCOLOUR;

/**
 * This represents the active piece in the game of tetris. Tetriminos are confined within a fitted square grid.
 * The shape of the tetrimino within its grid is defined by the set of co ordinates _coOrds. These co-ordinates are
 * relative to the top left corner of the tetrimino's grid and determine which squares in the board to be coloured with
 * _colour.
 */
public abstract class Tetrimino {
    private int _xOrd;
    private int _yOrd;
    private int _dim;
    private Color _colour;
    private Angle _orientation;
    private TetrisBoard _tetrisBoard;
    private static final Angle[] ANGLES = Angle.values();
    private int[][] _coOrds;
    private boolean _cancelNext;

    protected enum Angle {UP, RIGHT, DOWN, LEFT}

    public Tetrimino(TetrisBoard board, int dim, int[][] coOrds, Color colour) throws ArrayIndexOutOfBoundsException {
        _tetrisBoard = board;
        _xOrd = board.getCols()/2;
        _yOrd = 1;
        _orientation = Angle.RIGHT;
        _dim = dim;
        _coOrds = coOrds;
        _colour = colour;
        paintWithSilhouette();
        if (!moveDown()) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Moves the piece one index down in the grid. If this is not possible then the tetrimino stays put and false is
     * returned. Otherwise returns true.
     */
    public boolean moveDown() {
        repaint(BLANKCOLOUR, false);
        _yOrd++;
        if (checkValid()) {
            repaint(_colour, true);
            return true;
        }
        _yOrd--;
        repaint(_colour, true);
        return false;
    }

    /**
     * Moves the piece one index left in the grid. If this is not possible then nothing happens
     */
    public void moveLeft() {
        unpaintWithSilhouette();
        _xOrd--;
        if (!checkValid()) {
            _xOrd++;
        } else {
            _cancelNext = false;
        }
        paintWithSilhouette();
    }

    /**
     * Moves the piece one index right in the grid. If this is not possible then nothing happens
     */
    public void moveRight() {
        unpaintWithSilhouette();
        _xOrd++;
        if (!checkValid()) {
            _xOrd--;
        } else {
            _cancelNext = false;
        }
        paintWithSilhouette();
    }

    /**
     * Rotates the piece in it's square by 90 degrees clockwise.
     */
    public void rotate() {
        unpaintWithSilhouette();
        _orientation = ANGLES[(_orientation.ordinal() + 1) % ANGLES.length];
        if (!checkValid()) {
            // if the piece can't be rotated it is moved left and right by 1 to see if it can be
            _xOrd--;
            if (!checkValid()) {
                _xOrd += 2;
                if (!checkValid()) {
                    _xOrd--;
                    _orientation = ANGLES[(_orientation.ordinal() + 3) % ANGLES.length];
                } else {
                    _cancelNext = false;
                }
            } else {
                _cancelNext = false;
            }
        } else {
            _cancelNext = false;
        }
        paintWithSilhouette();
    }

    private void paintWithSilhouette() {
        int initY = _yOrd;
        for (int i = 0; i < _tetrisBoard.getRows(); i++) {
            moveDown();
        }
        repaint(Color.GREY, false);
        _yOrd = initY;
        repaint(_colour,true);
    }

    public void unpaintWithSilhouette() {
        int initY = _yOrd;
        for (int i = 0; i < _tetrisBoard.getRows(); i++) {
            moveDown();
        }
        repaint(BLANKCOLOUR, false);
        _yOrd = initY;
        repaint(BLANKCOLOUR,false);
    }

    /**
     * This method paints the tetrimino on the board using the current x and y coOrdinates and orientation in the
     * specified colour.
     * @param colour the colour for it to be painted
     * @param solid true if the tetrimino is 'solid' here. If removing/unpainting the piece select false.
     */
    private void repaint(Color colour, boolean solid) {
        switch (_orientation) {
            case UP:
                for (int[] coOrd : _coOrds) {
                    _tetrisBoard.paintSquare(_xOrd + coOrd[0],_yOrd + coOrd[1],colour,!solid);
                }
                break;
            case RIGHT:
                for (int[] coOrd : _coOrds) {
                    _tetrisBoard.paintSquare(_xOrd + _dim - coOrd[1],_yOrd + coOrd[0],colour,!solid);
                }
                break;
            case DOWN:
                for (int[] coOrd : _coOrds) {
                    _tetrisBoard.paintSquare(_xOrd + _dim - coOrd[0],_yOrd + _dim - coOrd[1],colour,!solid);
                }
                break;
            case LEFT:
                for (int[] coOrd : _coOrds) {
                    _tetrisBoard.paintSquare(_xOrd + coOrd[1],_yOrd + _dim - coOrd[0],colour,!solid);
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
                        if (!_tetrisBoard.isEmpty(_xOrd + coOrd[0],_yOrd + coOrd[1])) {
                            return false;
                        }
                    }
                    break;
                case RIGHT:
                    for (int[] coOrd : _coOrds) {
                        if (!_tetrisBoard.isEmpty(_xOrd + _dim - coOrd[1],_yOrd + coOrd[0])) {
                            return false;
                        }
                    }
                    break;
                case DOWN:
                    for (int[] coOrd : _coOrds) {
                        if (!_tetrisBoard.isEmpty(_xOrd + _dim - coOrd[0],_yOrd + _dim - coOrd[1])) {
                            return false;
                        }
                    }
                    break;
                case LEFT:
                    for (int[] coOrd : _coOrds) {
                        if (!_tetrisBoard.isEmpty(_xOrd + coOrd[1],_yOrd + _dim - coOrd[0])) {
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

    /**
     * This method places this tetrimino in the given rectangle matrix.
     * @param board A tetris board.
     */
    public void holdOnBoard(TetrisBoard board) {
        _tetrisBoard = board;
        _xOrd = board.getCols()/2 - (_dim+1)/2;
        _yOrd = board.getRows()/2 - (_dim+1)/2;
        repaint(_colour, false);
    }

    public void playOnBoard(TetrisBoard board) {
        unpaintWithSilhouette();
        _tetrisBoard = board;
        _xOrd = board.getCols()/2;
        _yOrd = 1;
        paintWithSilhouette();
    }

    public void setCancelNext() {
        _cancelNext = true;
    }

    public boolean isCancelNext() {
        return _cancelNext;
    }
}
