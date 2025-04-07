package Tugas.RecursiveMethod;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class RecursiveMethod extends Application {
    private final TextArea outputArea = new TextArea();
    @Override
    public void start(Stage stage) {
        outputArea.setEditable(false);

        int start = 5;
        outputArea.appendText("Counting down from " + start + ":\n");
        countDown(start);

        VBox root = new VBox(outputArea);
        Scene scene = new Scene(root, 300, 200);
        stage.setTitle("Simple Recursive Example");
        stage.setScene(scene);
        stage.show();
    }

    private void countDown(int n) {
        if (n == 0) {
            outputArea.appendText("Done!\n");
            return;
        }
        outputArea.appendText(n + "\n");
        countDown(n - 1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
