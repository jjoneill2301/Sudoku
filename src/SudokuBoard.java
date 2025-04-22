public class SudokuBoard {
    // Useful reference: https://www.geeksforgeeks.org/program-sudoku-generator/
    private int[][] board = new int[9][9]; // 1-9 valid, index 0 means cell null

    static boolean validNum(int[][] grid, int row, int col, int num) {
        for (int iterableCol = 0; iterableCol < 9; iterableCol++) {
            if (grid[row][iterableCol] == num) { // This loop checks validity by row
                return false; // if number is  within the row, because we are only iterating through the column
            }
        }
        for (int iterableRow = 0; iterableRow < 9; iterableRow++) {
            if (grid[iterableRow][col] == num) { // This loop checks validity by column
                return false; // if number is within the column, bc we are only iterating through the row
            }
        }
        int startX = row - (row % 3); // Row we are on - mini length (i.e. [8 - (8 % 3)] -> 8 - 2 -> 6, AKA start of box 2
        int startY = col - (col % 3); // same idea
        // This loop checks any of the 9 "mini" grids within the sudoku puzzle.
        for (int x = 0; x < 3; x++) { // Checks validity by mini row
            for (int y = 0; y < 3; y++) { //Checks validity by mini col
                if (grid[x + startX][y + startY] == num) {
                    return false; // if selected square is num then square is not valid
                }
            }
        }
        return true; // escape all tests
    }
}