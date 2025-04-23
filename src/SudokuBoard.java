public class SudokuBoard {
    // Useful reference: https://www.geeksforgeeks.org/program-sudoku-generator/
    private int[][] board = new int[9][9]; // 1-9 valid, index 0 means cell null
    private String solutionCondensed = "";

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
        int startY = col - (col % 3); // same idea; mod 3 always gets to the start of whatever subgrid you want
        // This loop checks any of the 9 "mini" grids within the sudoku puzzle.
        for (int x = 0; x < 3; x++) { // Checks validity by mini row
            for (int y = 0; y < 3; y++) { //Checks validity by mini col
                if (grid[x + startX][y + startY] == num) {
                    return false; // if selected square is num then square is not valid
                }
            }
        }
        return true; // Pass all tests
    }

    // https://w.wiki/8Zj I just changed the JS implementation to Java, chatGPT told me about this bc I was stuck
    static void mix(int[] nums) {
        for (int i = nums.length - 1; i > 0; i--) {
            final int j = (int) (Math.random() * (i +1)); // no Math.floor because casting to int is functionally the same and thus redundant
            int variableParkingLot = nums[i]; // the end of the JS example is not as easy to do in Java
            nums[i] = nums[j];
            nums[j] = variableParkingLot;
        }
    }

    static boolean fillGrid(int[][] grid) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if(grid[row][col] == 0) { // remember, 0 means null so this loop takes advantage of that
                    int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9}; // acceptable entries, zero not included!
                    mix(nums);
                    for (int num : nums) {
                        if(validNum(grid,row,col,num)) { // Validate num and make sure it follows game rules
                            grid[row][col] = num; // Slot in accepted number
                            if (fillGrid(grid)) { // if we reached an acceptable number
                                return true; // and can now exit early
                            }
                            grid[row][col] = 0; // did not pass prev if statement, so undo and try num above this one
                        }
                    }
                    return false; // If nums[] array was contains no matches.
                }
            }
        }
        return true;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard() {
        fillGrid(board);
    }

    public void solutionToString() {
        int[][] game = getBoard();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                solutionCondensed += game[row][col];
            }
        }
        System.out.print(solutionCondensed);
    }
}