import java.util.Random;
public class SudokuBoardWithCells extends Cell {
    private Cell[][] puzzleSolved = new Cell[9][9];    // 2d array of Cell objects for the finished puzzle
    private Cell[][] puzzleUnsolved = new Cell[9][9];  // 2d array of Cell objects for the puzzle
    private int cellsToRemove = 0; // Will control difficulty

    // Getters for main
    public Cell[][] getPuzzleSolved() {
        return puzzleSolved;
    }

    public Cell[][] getPuzzleUnsolved() {
        return puzzleUnsolved;
    }

    // Generate a solved sudoku as well as its corresponding unsolved state by removing cells from the solved board
    public void generateBoard() {
        fillGridWithCells(puzzleSolved);
        // Loop through the entire grid
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) { // Copy every value from first board into second
                puzzleUnsolved[row][col].setValue(puzzleSolved[row][col].getValue());
            }
        } // TODO Make easy, medium, hard buttons that will set x automatically in SudokUI.java
        // Generate unsolved puzzle by removing x number of cells (they are set to zero, and later on zero is shown as "")
        // Currently x is a hardcoded placeholder, but eventually it will be preset numbers that will be passed
        // depending on the difficulty the player selects
        removeCellsFromGrid(0); //placeholder
    }

    public SudokuBoardWithCells() {
        super(0, 0); // Constructor from super. Necessary or compiler will not like it
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) { // Instantiate empty cells within 2d array
                puzzleSolved[row][col] = new Cell(row, col);
                puzzleUnsolved[row][col] = new Cell(row, col);
            }
        } // Generate both new boards
        generateBoard();
    }

    private static boolean validNumWithCells(Cell[][] grid, int row, int col, int num) {
        return grid[row][col].isValid(grid, num); // This was way harder to figure out than it should have been
    }  // Return true or false for whether the cell at point [row, col] follows the rules of sudoku

    private static boolean fillGridWithCells(Cell[][] grid) {
        for (int row = 0; row < 9; row++) { // Pretty much copy and pasted from my previous SudokuBoard.java class
            for (int col = 0; col < 9; col++) {
                if (grid[row][col].getValue() == 0) { // If cell is "empty" (.value = 0)
                    int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
                    mix(nums); // Shuffle this ^ so we can further randomize each subgrid's cell placement
                    for (int n : nums) { // Loop through shuffled numbers
                        if (validNumWithCells(grid, row, col, n)) { // Ensure number n is allowed at point [row, col]
                            grid[row][col].setValue(n);  // If so, assign number n to cell at point [row, col]
                            if (fillGridWithCells(grid))
                                return true;
                            grid[row][col].setValue(0);
                        }  // If all allowed numbers have been tried and still none are allowed,
                    }      // backtrack by resetting the cell's value to 0 again
                    return false;
                }
            }
        }
        return true;
    }

    private void removeCellsFromGrid(int cellsToRemove) {
        int cellsRemoved = 0; // Larger values remove more cells resulting in more difficult puzzles
        Random r = new Random();
        while (cellsRemoved < cellsToRemove) { // r.nextint goes from 0 to 8, but our JTable has those as bounds
            int row = r.nextInt(9); // Choose random row
            int col = r.nextInt(9); // Choose random col
            if (puzzleUnsolved[row][col].getValue() != 0) { // If cell at random point is nonzero, set it to 0.
                puzzleUnsolved[row][col].setValue(0);       // Later in main, this will be shown as an empty string in
                cellsRemoved++; // Increment counter        // the cell rather than a 0.
            }
        }
    }

    // Copy and pasted from SudokuBoard.java and renamed variable
    // Uses the Fisher-Yates shuffle algorithm to mix the nums array in the fillGridWithCells method
    private static void mix(int[] nums) { // https://w.wiki/8Zj
        for (int i = nums.length - 1; i > 0; i--) {
            final int j = (int) (Math.random() * (i + 1));
            int k = nums[i];
            nums[i] = nums[j];
            nums[j] = k; // Use temp variable k to change indices of numbers
        }
    }
}