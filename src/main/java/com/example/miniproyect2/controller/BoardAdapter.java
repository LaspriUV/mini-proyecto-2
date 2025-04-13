package com.example.miniproyect2.controller;

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
            return true;
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

        System.out.println(numbers);
        Collections.shuffle(numbers, random);
        System.out.println("\n");
        System.out.println(numbers);

        // 🧩 Llenar las celdas del bloque actual
        for (int i = startRow; i < startRow + BLOCK_ROWS; i++) {
            for (int j = startCol; j < startCol + BLOCK_COLS; j++) {
                for (Integer number : numbers) {
                    if (isValid(i, j, number)) {
                        board.get(i).set(j, number);
                        if (fillBlocks(blockIndex + 1)) {
                            return true;
                        }
                        board.get(i).set(j, 0);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean isValid(int row, int col, int candidate) {
        for (int j = 0; j < SIZE; j++) {
            if (board.get(row).get(j) == candidate) {
                return false;
            }
        }

        for (int i = 0; i < SIZE; i++) {
            if (board.get(row).get(i) == candidate) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void printBoard() {
        for (List<Integer> row : board) {
            for (Integer number : row) {
                System.out.print(number + " ");
            }
            System.out.println();
        }
    }

    public List<List<Integer>> getBoard() {
        return board;
    }

}
