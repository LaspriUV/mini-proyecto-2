package com.example.miniproyect2.controller;

import com.example.miniproyect2.view.SudokuStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartController {

    @FXML
    private Button playButton;

    @FXML
    private Button instructionsButton;

    @FXML
    public void initialize() {
        playButton.setOnAction(this::handlePlay);
        instructionsButton.setOnAction(this::handleInstructions);
    }

    private void handlePlay(ActionEvent event) {
        // Cierra la ventana actual y abre la del Sudoku
        Stage currentStage = (Stage) playButton.getScene().getWindow();
        currentStage.close();
        try {
            new SudokuStage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleInstructions(ActionEvent event) {
        // Aquí luego abrimos otra ventana con las instrucciones
        System.out.println("Instrucciones aún no implementadas.");
    }
}
