package tetris;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BoardSceneController {
    public static final int ROWS = 22;
    public static final int COLS = 10;
    private boolean _canSwap;
    private Rectangle[][] _squares;
    private Tetrimino _activePiece;
    private Tetrimino _heldPiece;
    private Timer _timer;
    private TetrisBoard _mainBoard;
    private TetrisBoard _holderBoard;
    private int _score = 0;

    @FXML private Button _startGameButton;
    @FXML private ListView<String> _highScoresView;
    @FXML private GridPane _endOverlay;
    @FXML private Label _scoreLabel;
    @FXML private GridPane _boardPane;
    @FXML private GridPane _holderPane;

    @FXML
    private void initialize() {
        _mainBoard = new TetrisBoard(_boardPane);
        _holderBoard = new TetrisBoard(_holderPane);
        _endOverlay.setVisible(false);
        _boardPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
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
                } else if (event.getCode() == KeyCode.SPACE) {
                    // this moves the piece to lowest possible position and spawns a new piece instantly.
                    for (int i=0;i<ROWS;i++) {
                        _activePiece.moveDown();
                    }
                    newPiece();
                } else if (event.getCode() == KeyCode.SHIFT) {
                    // This stores the active piece in the holderBoard on the right. If there is a piece held already
                    // then it becomes active otherwise a new piece is added.
                    if (_canSwap) {
                        // only one swap can be done before a new piece is spawned.
                        _canSwap = false;
                        Tetrimino temp = _activePiece;
                        temp.unpaintWithSilhouette();
                        if (_heldPiece != null) {
                            _activePiece = _heldPiece;
                            _activePiece.playOnBoard(_mainBoard);
                        } else {
                            newPiece();
                        }
                        temp.holdOnBoard(_holderBoard);
                        _heldPiece = temp;
                    }
                }
                event.consume();
            }
        });
    }

    @FXML
    public void startGame() {
        _endOverlay.setVisible(false);
        _startGameButton.setDisable(true);
        _boardPane.setDisable(false);
        _boardPane.requestFocus();
        _holderBoard.clearBoard();
        _mainBoard.clearBoard();
        _heldPiece = null;
        newPiece();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                // moves the active piece once down, if the piece has no room to fall then a new piece will be added.
                if (!_activePiece.moveDown()) {
                    Platform.runLater(() -> {
                        newPiece();
                    });
                }
            }
        };
        _timer = new Timer();
        _timer.schedule(timerTask,0, 1000);
    }

    /**
     * This ends focus for the current active piece and puts it on a random new piece. In ending the focus it also
     * checks if there are any full lines to remove and adds removed lines to the score. If the new piece cannot be
     * added, then the game will end.
     */
    private void newPiece() {
        _canSwap = true;
        _score += _mainBoard.removeFullLines();
        _scoreLabel.setText("SCORE: " + _score);
        Random r = new Random();
        try {
            switch (r.nextInt(7)) {
                case 0: _activePiece = new IPiece(_mainBoard);
                    break;
                case 1: _activePiece = new JPiece(_mainBoard);
                    break;
                case 2: _activePiece = new LPiece(_mainBoard);
                    break;
                case 3: _activePiece = new SPiece(_mainBoard);
                    break;
                case 4: _activePiece = new TPiece(_mainBoard);
                    break;
                case 5: _activePiece = new ZPiece(_mainBoard);
                    break;
                case 6: _activePiece = new OPiece(_mainBoard);
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            _timer.cancel();
            _boardPane.setDisable(true);
            _endOverlay.setVisible(true);
            _startGameButton.setDisable(false);
        }
    }


}
