package com.example.miniproyect2.model;

import com.example.miniproyect2.controller.BoardAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Board extends BoardAdapter {
    /**
     * Constructor initializes the board with zeros and then fills each block with one number.
     */
    public Board() { /* Falta implementar la logica del sudoku
     que revise fila, columna y bloque */
        board = new ArrayList<>();
        // Initialize the board with zeros.
        for (int i = 0; i < SIZE; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < SIZE; j++) {
                row.add(0);
            }
            board.add(row);
        }
        // Attempt to fill each block with a valid number.
        if (!fillBlocks(0)) {
            System.out.println("Failed to generate the Sudoku board.");
        }
    }

    // Clase interna para el tablero
    public class BoardFull {
        private List<List<Integer>> board;
        private final int SIZE = 6;
        private Random random = new Random();

        public BoardFull() {
            board = new ArrayList<>();
            initializeBoardFull();
            fillBoard();
        }

        private void initializeBoardFull() {
            for (int i = 0; i < SIZE; i++) {
                List<Integer> row = new ArrayList<>();
                for (int j = 0; j < SIZE; j++) {
                    row.add(0);
                }
                board.add(row);
            }
        }

        private void fillBoard() {
            // Llenar cada fila con números del 1 al 6 sin repetir
            for (int i = 0; i < SIZE; i++) {
                List<Integer> numbers = new ArrayList<>();
                for (int num = 1; num <= SIZE; num++) {
                    numbers.add(num);
                }
                Collections.shuffle(numbers, random);

                for (int j = 0; j < SIZE; j++) {
                    board.get(i).set(j, numbers.get(j));
                }
            }

        }

        public void printBoardFull() {
            System.out.println("\nSudoku Board Full\n");
            for (List<Integer> row : board) {
                for (Integer number : row) {
                    System.out.print(number + " ");
                }
                System.out.println();
            }
        }
    }
}
