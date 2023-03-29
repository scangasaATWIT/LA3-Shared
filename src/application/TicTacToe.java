package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TicTacToe extends Application {

    private final String X_IMAGE = "file:./images/x.png";
    private final String O_IMAGE = "file:./images/o.png";

    @Override
    public void start(Stage primaryStage) {
        String[][] board = {
                {"O", "X", "O"},
                {"X", null, "X"},
                {"X", null, null},
        };

        primaryStage.setTitle("JavaFX Tic-Tac-Toe");
        primaryStage.setScene(new Scene(tictacBoard(board), 400, 400));
        primaryStage.show();
    }

    private GridPane tictacBoard(String[][] board) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ImageView imageView = null;
                if (board[i][j] != null) {
                    if (board[i][j].equals("X")) {
                        imageView = new ImageView(new Image(X_IMAGE));
                    } else if (board[i][j].equals("O")) {
                        imageView = new ImageView(new Image(O_IMAGE));
                    }
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    imageView.setPreserveRatio(true);
                    imageView.setSmooth(true);
                    }
                    gridPane.add(imageView == null ? new ImageView() : imageView, j, i);
                    }
                    }
                    return gridPane;
                    }
    public static void main(String[] args) {
        launch(args);
    }
}
