package com.example.miniproyect2;

import com.example.miniproyect2.view.SudokuStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class
main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        new SudokuStage();
    }

    public static void main(String[] args) {
        launch();
    }
}
