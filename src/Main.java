public class Main {
    /* This printGrid method was fully generated as ChatGPT
    I am not going to use this once I begin designing the GUI
    I just needed to ensure my code is functioning as intended */
    static void printGrid(int[][] grid) {
        for (int r = 0; r < 9; r++) {
            if (r % 3 == 0) System.out.println("+-------+-------+-------+");
            for (int c = 0; c < 9; c++) {
                if (c % 3 == 0) System.out.print("| ");
                int v = grid[r][c];
                System.out.print(v == 0 ? ". " : v + " ");
            }
            System.out.println("|");
        }
        System.out.println("+-------+-------+-------+");
    }

    static void printPuzzle(String[][] p) {
        for (int r = 0; r < 9; r++) {
            if (r % 3 == 0) System.out.println("+-------+-------+-------+");
            for (int c = 0; c < 9; c++) { // Pasted method above and just used here so I can test the unsolved
                if (c % 3 == 0) System.out.print("| ");
                String v = p[r][c];
                System.out.print(v.isEmpty() ? ". " : v + " ");
            }
            System.out.println("|");
        }
        System.out.println("+-------+-------+-------+");
    }

    public static void main(String[] args) {
        SudokuBoard s = new SudokuBoard();
        s.setBoard();
        printGrid(s.getPuzzleSolved()); // Difficulty 0-1
        printPuzzle(s.getPuzzleUnsolved(0.8));
    }
}