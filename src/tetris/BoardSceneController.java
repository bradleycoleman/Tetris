package tetris;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;
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
                if (!_activePiece.moveDown()) {
                    boolean lineFull;
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
                        Random r = new Random();
                        switch (r.nextInt(6)) {
                            case 0: _activePiece = new IPiece(_squares);
                            break;
                            case 1: _activePiece = new JPiece(_squares);
                            break;
                            case 2: _activePiece = new LPiece(_squares);
                            break;
                            case 3: _activePiece = new SPiece(_squares);
                            break;
                            case 4: _activePiece = new TPiece(_squares);
                            break;
                            case 5: _activePiece = new ZPiece(_squares);
                            break;
                            case 6: _activePiece = new OPiece(_squares);
                            break;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        cancel();
                        System.out.println("This runs?");
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
