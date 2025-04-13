package com.example.miniproyect2.controller;

import com.example.miniproyect2.model.Board;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class SudokuController {
    @FXML
    private GridPane boardGridPane;
    private Board board;
    private Board.BoardFull boardFull;

    @FXML
    private Button helpButton;

    @FXML
    public void initialize() {
        initializaFullBoard();
        fillBoard();
    }

    public void initializaFullBoard() {
        Board board = new Board();
        this.boardFull = board.new BoardFull();
        System.out.println("Sudoku Board Full\n");
        boardFull.printBoardFull();
    }


    /*HU-1 Creacion de los campos de texto, crea un for, donde empieza en la fila 0, columna 0, donde
    * cada vez va sumando 1 en 1*/
    private void fillBoard() {
        board = new Board();
        board.printBoard();

        System.out.println("\n\n\n\n");


        for (int row = 0; row < board.getBoard().size(); row++) {
            for (int col = 0; col < board.getBoard().size(); col++) {
                int number = board.getBoard().get(row).get(col);
                TextField textField = new TextField();
                textField.setAlignment(Pos.CENTER);
                textField.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                GridPane.setHgrow(textField, Priority.ALWAYS);
                GridPane.setVgrow(textField, Priority.ALWAYS);

                // --------------------------------------
                // 1. Base del estilo (sin fondo todavía)
                // --------------------------------------
                String style = "-fx-border-color: white; -fx-border-width: 1px; -fx-text-fill: white; ";

                // --------------------------------------
                // 2. Estilo de fondo alternado por bloque 2x3
                // --------------------------------------
                int blockRow = row / 2;   // Bloques de 2 filas
                int blockCol = col / 3;   // Bloques de 3 columnas
                if ((blockRow + blockCol) % 2 == 0) {
                    style += "-fx-background-color: #2b2b2b;";  // Fondo más oscuro
                } else {
                    style += "-fx-background-color: #1e1e1e;";  // Fondo más claro
                }

                // --------------------------------------
                // 3. Bordes extra para separar los bloques
                // --------------------------------------
                if (col % 3 == 0) {
                    style += "-fx-border-left-width: 3px;";
                }
                if (row % 2 == 0) {
                    style += "-fx-border-top-width: 3px;";
                }
                if (col == board.getBoard().size() - 1) {
                    style += "-fx-border-right-width: 3px;";
                }
                if (row == board.getBoard().size() - 1) {
                    style += "-fx-border-bottom-width: 3px;";
                }

                // --------------------------------------
                // 4. Aplicar estilo y contenido
                // --------------------------------------
                textField.setStyle(style);

                if (number > 0 && number < 7) {
                    textField.setText(String.valueOf(number));
                    textField.setEditable(false);
                    textField.setStyle(style + "-fx-text-fill: #FF9999;"); // Color distinto para los fijos
                } else {
                    textField.setText("");
                    textField.setStyle(style + "-fx-text-fill: white;"); // Color blanco para el jugador
                }

                boardGridPane.setRowIndex(textField, row);
                boardGridPane.setColumnIndex(textField, col);
                boardGridPane.getChildren().add(textField);
                handleNumberTextField(textField, row, col);
            }
        }
    }

    /*HU-1 Si se digita un caracter diferente a un numero, se colorea el textField de color rojo*/
    private void handleNumberTextField(TextField textField, int row, int col) {
        textField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty()) {
                try {
                    int num = Integer.parseInt(newVal);
                    boolean isValid = board.isValid(row, col, num);
                    System.out.println(isValid);

                    // Retroalimentación visual, cambiado para que
                    if (isValid) {
                        textField.getStyleClass().remove("invalid");
                    } else {
                        if (!textField.getStyleClass().contains("invalid")) {
                            textField.getStyleClass().add("invalid");
                        }
                    }
                } catch (NumberFormatException e) {
                    textField.setStyle("-fx-border-color: red;");
                }
            } else {
                textField.setStyle(""); // Reset si está vacío
            }
        });
    }

    @FXML
    private void helpButtonAction() {

    }
}
