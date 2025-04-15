package com.example.miniproyect2.controller;

import com.example.miniproyect2.view.SudokuStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/miniproyect2/instructions-view.fxml"));
            Parent root = loader.load();

            // Accedemos al controlador y le pasamos la escena anterior para poder volver
            InstructionsController controller = loader.getController();
            Stage currentStage = (Stage) instructionsButton.getScene().getWindow();
            controller.setMainScene(currentStage.getScene());

            // Mostramos la nueva escena
            Scene instructionScene = new Scene(root);
            currentStage.setScene(instructionScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
