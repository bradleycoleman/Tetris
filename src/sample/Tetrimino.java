package sample;


import javafx.scene.shape.Rectangle;

public abstract class Tetrimino {
    protected int _xOrd;
    protected int _yOrd;
    protected Angle _orientation;
    protected Rectangle[][] _squares;
    public static final Angle[] ANGLES = Angle.values();

    protected enum Angle {UP, RIGHT, DOWN, LEFT}

    public Tetrimino(Rectangle[][] squares) throws ArrayIndexOutOfBoundsException {
        _squares = squares;
        _xOrd = squares.length/2;
        _yOrd = 0;
        _orientation = Angle.RIGHT;
        if (!moveDown()) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Moves the piece one index down in the grid
     */
    public Boolean moveDown() {
        if (checkValid(Angle.DOWN)) {
            unpaint();
            _yOrd++;
            repaint();
            return true;
        }
        return false;
    }

    /**
     * Moves the piece one index left in the grid
     */
    public void moveLeft() {
        if (checkValid(Angle.LEFT)) {
            unpaint();
            _xOrd--;
            repaint();
        }
    }

    /**
     * Moves the piece one index right in the grid
     */
    public void moveRight() {
        if (checkValid(Angle.RIGHT)) {
            unpaint();
            _xOrd++;
            repaint();
        }
    }

    /**
     * Rotates the piece by 90 degrees clockwise.
     */
    public void rotate() {
        if (checkValid(Angle.UP)) {
            unpaint();
            _orientation = ANGLES[(_orientation.ordinal() + 1) % ANGLES.length];
            repaint();
        }
    }

    /**
     * This method checks if the requested move is a valid one (i.e. the tetrimino is not going to overlap any others
     * or go out of bounds)
     */
    protected abstract Boolean checkValid(Angle arrowKeyPress);

    /**
     * This method removes the tetrimino from the board before it gets repainted in its new location
     */
    protected abstract void unpaint();

    /**
     * This method paints the tetrimino on the board using the current x and y coordinates and orientation.
     */
    protected abstract void repaint();

}
