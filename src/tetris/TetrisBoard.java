package tetris;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class TetrisBoard {
    public static final int DIM = 20;
    private GridPane _grid;
    private Rectangle[][] _squares;
    private boolean[][] _emptySquares;
    private int _rows;
    private int _cols;
    public static Color BLANKCOLOUR = Color.WHITE;

    public TetrisBoard(GridPane grid) {
        _grid = grid;
        _rows = _grid.impl_getRowCount();
        _cols = _grid.impl_getColumnCount();
        _emptySquares = new boolean[_rows][_cols];
        _squares = new Rectangle[_rows][_cols];
        for (int i = 0; i < _rows; i++) {
            for (int j = 0; j < _cols; j++) {
                _squares[i][j] = new Rectangle(DIM, DIM);
                paintSquare(j,i,BLANKCOLOUR, true);
                _squares[i][j].setStroke(Color.BLACK);
                _emptySquares[i][j] = true;
                grid.add(_squares[i][j], j, i);
            }
        }
    }

    /**
     * Removes all full rows from the board and moves the rows above them down.
     * @return The points that should be added the score given the number of lines removed.
     */
    public int removeFullLines() {
        boolean lineFull;
        int linesRemoved = 0;
        for (int i = 0; i < _rows; i++) {
            lineFull = true;
            for (int j = 0; j < _cols; j++) {
                if (isEmpty(j,i)) {
                    lineFull = false;
                }
            }
            if (lineFull) {
                removeLine(i);
                linesRemoved++;
            }
        }
        if (linesRemoved == 1) {
            return 1;
        } else if (linesRemoved == 2) {
            return 3;
        } else if (linesRemoved == 3) {
            return 6;
        } else if (linesRemoved == 4) {
            return 10;
        }
        // returning more than 4 lines is impossible with default tetriminos - this will only occur if game is modified
        // to allow tetriminos of length 5+.
        return 3*linesRemoved;
    }


    private void removeLine(int line) {
        for (int i = 0; i < _cols; i++) {
            for (int j = line; j > 0; j--) {
                paintSquare(i,j,_squares[j-1][i].getFill(),_emptySquares[j-1][i]);
            }
            paintSquare(0, i, BLANKCOLOUR, true);
        }
    }

    /**
     * Paint the specified square a certain colour.
     * @param x The x-coordinate of the square in this board - or the column index
     * @param y The y-coordinate of the square in this board - or the row index
     * @param color The colour to be painted
     * @param empty say true if this square should now be considered 'empty' or false if the square is now 'filled'.
     *               This is used to determine if a tetrimino can move to this square.
     */
    public void paintSquare(int x, int y, Color color, boolean empty) {
        _squares[y][x].setFill(color);
        _emptySquares[y][x] = empty;
    }

    /**
     * Paint the specified square a certain colour.
     * @param x The x-coordinate of the square in this board - or the column index
     * @param y The y-coordinate of the square in this board - or the row index
     * @param paint The paint to be used in painting
     * @param empty say true if this square should now be considered 'empty' or false if the square is now 'filled'.
     *               This is used to determine if a tetrimino can move to this square.
     */
    public void paintSquare(int x, int y, Paint paint, boolean empty) {
        _squares[y][x].setFill(paint);
        _emptySquares[y][x] = empty;
    }

    public boolean isEmpty(int x, int y) {
        return _emptySquares[y][x];
    }

    public int getCols() {
        return _cols;
    }
    public int getRows() {
        return _rows;
    }

    public void clearBoard() {
        for (int i = 0; i< _rows; i++) {
            removeLine(i);
        }
    }
}
