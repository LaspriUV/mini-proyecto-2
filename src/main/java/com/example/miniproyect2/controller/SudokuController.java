package com.example.miniproyect2.controller;

import com.example.miniproyect2.model.Board;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.util.function.UnaryOperator;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class SudokuController {

    @FXML
    private GridPane boardGridPane;
    private Board board = new Board();

    @FXML
    public void initialize() {
        fillBoard();
        Image heart = new Image(getClass().getResource("/com/example/miniproyect2/corazon.png").toExternalForm());
        life1.setImage(heart);
        life2.setImage(heart);
        life3.setImage(heart);
        life4.setImage(heart);
        life5.setImage(heart);
    }

    //5 CORAZONES
    @FXML private ImageView life1;
    @FXML private ImageView life2;
    @FXML private ImageView life3;
    @FXML private ImageView life4;
    @FXML private ImageView life5;
    private int livesRemaining = 5;


    private void fillBoard() {
        System.out.println("\nSudoku Board\n");
        board.printBoard();

        for (int row = 0; row < board.getBoard().size(); row++) {
            for (int col = 0; col < board.getBoard().size(); col++) {
                int number = board.getBoard().get(row).get(col);
                TextField textField = new TextField();
                /*HU-1- INTERFAZ GRAFICA
                *CUADRICULA DE 6X6 CLARAMENTE VISIBLE*/
                textField.setAlignment(Pos.CENTER);
                textField.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                //CAMBIO PARA QUE EL DISEÑO SE MANEJE DESDE EL ARCHIVO DE CSS
                textField.getStyleClass().add("text-field");

                /*HU-1 INTERFAZ GRAFICA
                *DISEÑO DEBE SER VISUALMENTE ATRACTIVO Y COHERENTE*/
                // Fondo de bloque claro u oscuro
                int blockRow = row / 2;
                int blockCol = col / 3;
                if ((blockRow + blockCol) % 2 == 0) {
                    //CAMBIADO PARA QUE SE MANEJE DESDE CSS
                    textField.getStyleClass().add("block-dark");
                } else {
                    textField.getStyleClass().add("block-light");
                }

                //Bordes según la posición, para separar los bloques
                if (col % 3 == 0) textField.getStyleClass().add("border-left");
                if (row % 2 == 0) textField.getStyleClass().add("border-top");
                if (col == board.getBoard().size() - 1) textField.getStyleClass().add("border-right");
                if (row == board.getBoard().size() - 1) textField.getStyleClass().add("border-bottom");

                //Celdas fijas o jugables
                if (number > 0 && number < 7) {
                    textField.setText(String.valueOf(number));
                    textField.setEditable(false);
                    textField.getStyleClass().add("fixed-cell");
                } else {
                    textField.getStyleClass().add("player-cell");
                }

                boardGridPane.add(textField, col, row);
                handleNumberTextField(textField, row, col);
            }
        }
    }

    /*HU-2 INGRESO DE NUMEROS*/
    private void handleNumberTextField(TextField textField, int row, int col) {
        //Filtro para restringir la entrada a un solo dígito del 1 al 6
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            return newText.matches("[1-6]?") ? change : null;
        };
        //Aplica el filtro al TextField
        textField.setTextFormatter(new TextFormatter<>(filter));

        /*HU-2 INGRESO DE NÚMEROS
         *CELDA SOLO PERMITE LA ENTRADA DE NUMEROS UNICAMENTE DEL 1 AL 6
         *Evita que el usuario escriba letras, símbolos o números fuera del rango permitido.
         */
        textField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty()) {
                try {
                    int num = Integer.parseInt(newVal);
                    if (num < 1 || num > 6) return;

                    boolean isValid = board.isValid(row, col, num);
                    if (isValid) {
                        textField.getStyleClass().remove("invalid");
                        board.putNumber(row, col, num);
                    } else {
                        if (!textField.getStyleClass().contains("invalid")) {
                            textField.getStyleClass().add("invalid");
                        }
                        loseLife(); //Restar una vida al fallar
                    }
                } catch (NumberFormatException e) {
                    textField.getStyleClass().add("invalid");
                }
            } else {
                textField.getStyleClass().remove("invalid");
            }
        });
        textField.setOnContextMenuRequested(event -> event.consume());
    }
    //Metodo para los corazones
    private void loseLife() {
        switch (livesRemaining) {
            case 5 -> life5.setVisible(false);
            case 4 -> life4.setVisible(false);
            case 3 -> life3.setVisible(false);
            case 2 -> life2.setVisible(false);
            case 1 -> life1.setVisible(false);
        }

        livesRemaining--;

        if (livesRemaining == 0) {
            System.out.println("¡Perrrrdistee!");
            //Desabiilitamos tablero si se pierde
            boardGridPane.setDisable(true);
        }
    }


    @FXML
    private void helpButtonAction() {
        // lógica del botón de ayuda
    }
}
