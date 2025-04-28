public class Cell {
    private int value; // Can be 0 for empty, or 1-9 for a number
    private final int row;  // In Java, a protected variable is a class member that can be accessed within the same class,
    private final int col; // by subclasses, and by other classes within the same package. -Google AI Summarization
    // Can be final because once the application gets to the user, they user is now only changing the JTable

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.value = 0;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    //  I initially made getters and setters for col/row, but because I am looping through the rows/columns within my
    //  SudokuBoardWithCells.java class, I already have access to that information within scope while I would want it.

    public boolean isValid(Cell[][] grid, int num) {
        for (int c = 0; c < 9; c++) {
            if(grid[row][c].value == num) { // if value of cell == num
                return false;
            } // Num not valid if found at any point on y level row
        }
        for (int r = 0; r < 9; r++) { // if value of cell == num
            if(grid[r][col].value == num) {
                return false;
            } // Num not valid if found at any point on x level col
        }
        int startRow = row - row % 3; // Define subgrid bounds
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) { // if value of cell == num
                if (grid[startRow + i][startCol + j].value == num)
                    return false;
            } // Num not valid within subgrid
        }
        return true; // Never returned false, num must be valid
    }
}