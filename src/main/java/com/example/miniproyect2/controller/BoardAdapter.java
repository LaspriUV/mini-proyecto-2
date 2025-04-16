package com.example.miniproyect2.controller;

import com.example.miniproyect2.model.Board;
import com.example.miniproyect2.model.Board.BoardFull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BoardAdapter implements IBoardController {
    protected int SIZE = 6;
    protected int BLOCK_ROWS = 2;
    protected int BLOCK_COLS = 3;

    protected int TOTAL_BLOCKS_ROWS = SIZE / BLOCK_ROWS;
    protected int TOTAL_BLOCKS_COLS = SIZE / BLOCK_COLS;
    protected int TOTAL_BLOCKS = TOTAL_BLOCKS_ROWS * TOTAL_BLOCKS_COLS;

    protected List<List<Integer>> board = new ArrayList<>();
    protected Random random = new Random();

    @Override
    public boolean fillBlocks(int blockIndex) {

        // Caso base de la recursión: si ya llenaste los 6 bloques (blockIndex == 6), el tablero está completo.
        if (blockIndex == TOTAL_BLOCKS) {
            return true; // tablero completado
        }

        // 🔍 Determinar la posición del bloque actual:
        int blockRow = blockIndex / TOTAL_BLOCKS_COLS;
        int blockCol = blockIndex % TOTAL_BLOCKS_COLS;
        int startRow = blockRow * BLOCK_ROWS;
        int startCol = blockCol * BLOCK_COLS;

        // 🔢 Generar números aleatorios
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= SIZE; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers, random);
        /* Se modifico, no se ha terminado, falta implementar logica */
        // 🧩 Llenar las celdas del bloque actual
        for (int i = startRow; i < startRow + BLOCK_ROWS; i++) {
            for (int j = startCol; j < startCol + BLOCK_COLS; j++) {
                if (board.get(i).get(j) == 0) { // Solo si esta vacia
                    for (Integer number : numbers) {
                        if (isValid(i, j, number)) {
                            board.get(i).set(j, number);
                            if (fillBlocks(blockIndex + 1)) {
                                return true;
                            }
                            board.get(i).set(j, 0); // Backtrack
                        }
                    }
                    return false; // No hay numero valido para esta celda
                }
            }
        }
        return true;
    }

    @Override
    public boolean isValid(int row, int col, int candidate) {

        // verificar fila
        for (int j = 0; j < SIZE; j++) {
            if (board.get(row).get(j) == candidate) {
                return false;
            }
        }

        // verificar columna
        for (int i = 0; i < SIZE; i++) {
            //se cambio board.get(row).get(i) por:
            if (board.get(i).get(col) == candidate) {
                return false;
            }
        }

        // Verificar bloque
        int blockStartRow = (row / BLOCK_ROWS) * BLOCK_ROWS;
        int blockStartCol = (col / BLOCK_COLS) * BLOCK_COLS;

        for (int i = blockStartRow; i < blockStartRow + BLOCK_ROWS; i++) {
            for (int j = blockStartCol; j < blockStartCol + BLOCK_COLS; j++) {
                if (board.get(i).get(j) == candidate) {
                    return false;
                }
            }
        }
        /*
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {

                boolean inRow = (i == row);
                boolean inCol = (j == col);
                boolean inBlock = (i >= blockStartRow && i < blockStartRow + BLOCK_ROWS)
                        && (j >= blockStartCol && j < blockStartCol + BLOCK_COLS);

                if ((inRow || inCol || inBlock) && board.get(i).get(j) == candidate) {
                    return false;
                }
            }
        }*/
        return true;
    }

    @Override
    public void printBoard() {
        System.out.println("\nSudoku Board\n");
        for (List<Integer> row : board) {
            for (Integer number : row) {
                System.out.print(number + " ");
            }
            System.out.println();
        }
    }

    // Ingresa los numeros a la lista de lista
    @Override
    public void putNumber(int row, int col, int candidate) {
        board.get(row).set(col, candidate);
    }

    // Devuelve un numero de la Arraylist (metodo no definido en la interface)
    public int getNumber(int row, int col) { return board.get(row).get(col);
    }

    public List<List<Integer>> getBoard() {
        return board;
    }

    public class LogicHelpButton {
        /**
         * Llena una celda vacía aleatoria con un número válido.
         *
         * @return true si se llenó una celda, false si el tablero está completo.
         */
        public boolean fillRandomCell() {
            List<int[]> emptyCells = new ArrayList<>();

            // Buscar celdas vacías (accede directamente a board de BoardAdapter)
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    if (board.get(row).get(col) == 0) {
                        emptyCells.add(new int[]{row, col});
                    }
                }
            }

            if (emptyCells.isEmpty()) {
                return false; // Tablero completo
            }

            // Elegir celda aleatoria
            int[] randomCell = emptyCells.get(random.nextInt(emptyCells.size()));
            int row = randomCell[0];
            int col = randomCell[1];

            // Probar números del 1 al 6 (usa isValid() de BoardAdapter)
            for (int num = 1; num <= SIZE; num++) {
                if (isValid(row, col, num)) {
                    board.get(row).set(col, num);
                    return true;
                }
            }
            return false; // No hubo número válido (raro en Sudoku)
        }
    }
    // Metodo para exponer el LogicHelpButton
    public LogicHelpButton createLogicHelpButton() {
        return new LogicHelpButton();
    }


}

