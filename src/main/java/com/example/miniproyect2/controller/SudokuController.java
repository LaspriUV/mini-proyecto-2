package com.example.miniproyect2.controller;

import com.example.miniproyect2.Utils.GameOverAlert;
import com.example.miniproyect2.model.Board;
import com.example.miniproyect2.controller.BoardAdapter;
import com.example.miniproyect2.view.SudokuStage;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.function.UnaryOperator;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SudokuController {

    @FXML
    private GridPane boardGridPane;
    private Board board = new Board();
    private BoardAdapter boardAdapter = new BoardAdapter();
    private BoardAdapter.LogicHelpButton logicButtonHelp = boardAdapter.createLogicHelpButton();
    private TextField[][] textFields;


    private TextField lastFocusedTextField;

    @FXML
    public void initialize() {
        boardGridPane.getChildren().clear();
        textFields = new TextField[6][6];
        fillBoard();
        Image heart = new Image(getClass().getResource("/com/example/miniproyect2/corazon.png").toExternalForm());
        life11.setImage(heart);
        life12.setImage(heart);
        life111.setImage(heart);
        life121.setImage(heart);
        life1.setImage(heart);
    }

    //5 CORAZONES
    @FXML private ImageView life1;
    @FXML private ImageView life11;
    @FXML private ImageView life12;
    @FXML private ImageView life111;
    @FXML private ImageView life121;
    @FXML private VBox livesBox;
    private int livesRemaining = 5;
    private int count = 0;


    private void fillBoard() {
        System.out.println("\nSudoku Board\n");
        board.printBoard();

        for (int row = 0; row < board.getBoard().size(); row++) {
            for (int col = 0; col < board.getBoard().size(); col++) {
                int number = board.getBoard().get(row).get(col);
                TextField textField = new TextField();
                textFields[row][col] = textField;  // Guarda la referencia

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
                /*
                // Tomar el ultimo listener
                textField.focusedProperty().addListener((obs, ldVal, newVal) -> {
                    if (newVal) {
                        lastFocusedTextField = textField;
                    }
                });*/
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
                        count++;
                        checkWin();
                        board.printBoard();
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
                // Quita el numero del Arraylist
                board.putNumber(row, col, 0);
                board.printBoard();
                textField.getStyleClass().remove("invalid");
            }
        });
        textField.setOnContextMenuRequested(event -> event.consume());
    }
    // Verifica que si tiene casilla roja, no funciona bien
    public boolean checkRed() {
        String claseRojo = "invalid";
        for (int i = 0; i < boardGridPane.getChildren().size(); i++) {
            if (boardGridPane.getChildren().get(i) instanceof TextField) {
                TextField textField = (TextField) boardGridPane.getChildren().get(i);
                if (textField.getStyleClass().contains(claseRojo)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Metodo para ganar.
    private void checkWin() {
        // 1 - Verificar que no alla textField rojos (No funciona bien)
        boolean value = checkRed();
        if(value == true) {
            System.out.println("No hay ninguna casilla roja");
        } else {
            System.out.println("Hay alguna casilla roja.");
        }

        // 2 - Verificar si esta lleno el tablero

        // 3 - Colocar un cuadro de texto de gano
    }


    //Metodo para los corazones
    private void loseLife() {
        switch (livesRemaining) {
            case 5 -> life1.setVisible(false);
            case 4 -> life12.setVisible(false);
            case 3 -> life11.setVisible(false);
            case 2 -> life121.setVisible(false);
            case 1 -> life111.setVisible(false);
        }

        livesRemaining--;

        if (livesRemaining == 0) {
            handleGameLost();
            boardGridPane.setDisable(true);
        }
    }

    private void reiniciar() {
        livesRemaining = 5;
        board = new Board();
        textFields = new TextField[6][6];
        initialize();
    }

    private void handleGameLost() {
        System.out.println("¡Perrrrdistee!");
        //Desabilitamos tablero si se pierde
        boardGridPane.setDisable(true);

        // Mostrar la alerta de "Perdiste"
        boolean volverAJugar = GameOverAlert.GameOverAlert((Stage) boardGridPane.getScene().getWindow());

        if (volverAJugar) {
            System.out.println("El usuario quiere volver a jugar.");
            // Lógica para reiniciar el juego
            Stage currentStage = (Stage) boardGridPane.getScene().getWindow();
            currentStage.close();
            reiniciar();
            try {
                new SudokuStage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Llamar a un metodo en SudokuApp para abrir una nueva ventana del juego

        } else {
            // Lógica para salir del juego
            System.out.println("El usuario quiere salir del juego.");
            Stage stage = (Stage) boardGridPane.getScene().getWindow();
            stage.close();
        }
    }



    // HU-4
    @FXML
    private void helpButtonAction() {
        if (logicButtonHelp.fillRandomCell()) {
            updateBoardUI(); // Actualiza la interfaz gráfica
            System.out.println("¡Celda ayudada!");
        } else {
            System.out.println("El tablero ya está completo.");
        }
    }

    private void updateBoardUI() {
        // Actualiza los TextFields según el estado actual del tablero
        for (int row = 0; row < board.getBoard().size(); row++) {
            for (int col = 0; col < board.getBoard().size(); col++) {
                TextField textField = textFields[row][col];
                int value = board.getNumber(row, col);
                textField.setText(value == 0 ? "" : String.valueOf(value));
            }
        }
    }
}
