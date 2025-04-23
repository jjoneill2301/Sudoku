import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Font;

public class SudokUI {

    private JPanel  sudokuPanel;
    private JTable  sudokuGame;

    private void createUIComponents() {
        startPuzzle();
    }

    @SuppressWarnings("unused") // unsolved and sudokuNumbers are necessary and are incorrectly labeled as unused
    private void startPuzzle() {
        SudokuBoard sudokuBoard = new SudokuBoard();
        sudokuBoard.setBoard();
        String[][] unsolved = sudokuBoard.getPuzzleUnsolved(.6);
        Object[] sudokuNumbers = new Object[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        sudokuPanel = new JPanel(null);
        sudokuGame = new JTable(9,9);
        for (int row = 0; row < 9; row++) { // Iterate through the table, setting the specific cell to the value
            for (int col = 0; col < 9; col++) { // contained in the unsolved 2d Array
                sudokuGame.setValueAt(sudokuBoard.getUnsolvedCell(row, col), row ,col);
            }
        }
        designGame(sudokuGame);
    }

    private void designGame(JTable g) {
        g.setBounds(50, 50, 400, 500);
        g.setRowHeight(50);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
        g.setCellSelectionEnabled(false); // This prevents a multiselect rectangle being formed on drag.

        // I asked ChatGPT to help here, because I could not find anything on how to do this despite other components
        // ↓↓↓ CENTER ALL CELLS ↓↓↓                               easily allowing you to freely adjust text alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        // tell the table to use that renderer for any Object (i.e. all) cells
        g.setDefaultRenderer(Object.class, centerRenderer);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SudokUI");
        frame.setContentPane(new SudokUI().sudokuPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(800, 130, 500, 600);
        frame.setVisible(true);
    }
}
