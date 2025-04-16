package com.example.miniproyect2.controller;

import com.example.miniproyect2.view.SudokuStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOverAlertController {
    @FXML
    private Button playButton;

    @FXML
    private Button exitButton;

    private Stage dialogStage;
    private boolean againPlay = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isAgainPlay() {
        return againPlay;
    }

    @FXML
    private void handlePlay(ActionEvent event) {
        againPlay = true;
        System.out.println("a jugar");
        dialogStage.close();
    }

    @FXML
    private void handleExit(ActionEvent event) {
        againPlay = false;
        System.out.println("chau");
        dialogStage.close();
    }
}
