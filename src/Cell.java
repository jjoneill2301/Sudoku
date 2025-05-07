public class Cell {
    private final int row;  // Row and col can both be final because
    private final int col;  // Cells will never change coordinates
    private int value;      // Can be 0 for empty, or 1-9 for a number

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.value = 0;
    }

    public int getValue() { // Originally had mutators for col/row, but SudokuBoardWithCells already has access
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isValid(Cell[][] grid, int num) {
        for (int c = 0; c < 9; c++) {
            if (grid[row][c].value == num) { // if value of cell == num
                return false;
            } // Num not valid if found at any point on y level row
        }
        for (int r = 0; r < 9; r++) { // if value of cell == num
            if (grid[r][col].value == num) {
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