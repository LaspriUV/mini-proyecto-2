package com.example.miniproyect2.controller;

import java.util.List;

public interface IBoardController {

    boolean fillBlocks(int blockIndex);

    boolean isValid(int row, int col, int candidate);

    void printBoard();

    void putNumber(int row, int col, int candidate);

    List<List<Integer>> getBoard();
}
