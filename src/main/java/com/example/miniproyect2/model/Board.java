package com.example.miniproyect2.model;

import com.example.miniproyect2.controller.BoardAdapter;

import java.util.ArrayList;
import java.util.List;

public class Board extends BoardAdapter {
    /**
     * Constructor initializes the board with zeros and then fills each block with one number.
     */
    public Board() {
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
}
