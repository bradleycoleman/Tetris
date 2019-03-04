package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Timer;
import java.util.TimerTask;

public class BoardSceneController {
    private final int ROWS = 20;
    private final int COLS = 10;
    private final int DIM = 20;
    private Rectangle[][] _squares;
    private Tetrimino _activePiece;
    @FXML private GridPane _board;

    @FXML
    private void initialize() {
        // A 2D array of rectangles to be coloured
        _squares = new Rectangle[COLS][ROWS];
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                _squares[i][j] = new Rectangle(DIM, DIM);
                _squares[i][j].setFill(Color.WHITE);
                _squares[i][j].setStroke(Color.BLACK);
                _board.add(_squares[i][j], i, j);
            }
        }
        _activePiece = new IPiece(_squares);
        _board.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.DOWN) {
                    _activePiece.moveDown();
                } else if (event.getCode() == KeyCode.LEFT) {
                    _activePiece.moveLeft();
                } else if (event.getCode() == KeyCode.UP) {
                    _activePiece.rotate();
                } else if (event.getCode() == KeyCode.RIGHT) {
                    _activePiece.moveRight();
                }
                event.consume();
            }
        });
    }

    @FXML
    public void startGame() {
        _board.requestFocus();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                // this will attempt to move the current piece down one square each second. if it cannot then it will
                // attempt to add a new piece. if that is not possible then the game will end.
                if (!_activePiece.moveDown()) {
                    Boolean lineFull = true;
                    for (int j = 0; j < ROWS; j++) {
                        lineFull = true;
                        for (int i = 0; i < COLS; i++) {
                            if (_squares[i][j].getFill().equals(Color.WHITE)) {
                                lineFull = false;
                            }
                        }
                        if (lineFull) {
                            removeLine(j);
                        }
                    }
                    try {
                        _activePiece = new IPiece(_squares);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("game over man");
                        // TODO: make something happen when the game ends.
                    }
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,0, 1000);
    }

    private void removeLine(int line) {
        for (int i = 0; i < COLS; i++) {
            for (int j = line; j > 0; j--) {
                _squares[i][j].setFill(_squares[i][j-1].getFill());
            }
            _squares[i][0].setFill(Color.WHITE);
        }
    }
}
