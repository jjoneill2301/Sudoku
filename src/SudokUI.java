import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.border.Border;
import java.awt.*;

public class SudokUI {

    private JPanel  sudokuPanel;
    private JTable  sudokuGame;

    private void createUIComponents() {
        startPuzzle();
    }

    private void startPuzzle() {
        SudokuBoardWithCells sudokuBoard = new SudokuBoardWithCells();
        Cell[][] unsolvedBoard = sudokuBoard.getPuzzleUnsolved();

        sudokuPanel = new JPanel(null);
        sudokuGame = new JTable(9,9);
        for (int row = 0; row < 9; row++) { // Iterate through the table, setting the specific cell to the value
            for (int col = 0; col < 9; col++) { // contained in the solved sudokuBoard at point [row,col]
                sudokuGame.setValueAt(unsolvedBoard[row][col].getValue(), row ,col);
                if (unsolvedBoard[row][col].getValue() == 0) {
                    sudokuGame.setValueAt("", row, col);// Display as ""
                }
            }
        }
        designGame(sudokuGame);
    }

    private void designGame(JTable g) {
        g.setBounds(50, 50, 400, 500);
        g.setRowHeight(50);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
        g.setCellSelectionEnabled(false); // This prevents a multiselect rectangle being formed on drag.
        // ChatGPT helped me with custom GUI renderer here for thicker borders and centered text
        g.setShowGrid(false); // Disable default grid and gaps or else it will be ugly
        g.setIntercellSpacing(new Dimension(0,0));
        // Applies thick borders every third row/col as well as around the entire board
        g.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                setHorizontalAlignment(SwingConstants.CENTER); // Center text within each cell
                // Calculate border thickness
                int top = (row == 0 ? 4 : 1);               // top and
                int left = (col == 0 ? 4 : 1);              // left border
                int bottom = ((row % 3) == 2 ? 4 : 1);      // every 3rd row
                int right = ((col % 3) == 2 ? 4 : 1);       // every 3rd col */
                Border b = BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK);
                setBorder(b);
                return this;
            }
        });
        // When the user edits a cell to try and enter a number, a JTextField appears which does not share the
        // same matteBorder created above. To get around this we must recreate the border above and apply it to the
        // cellEditor as well
        g.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value,
                                                         boolean isSelected, int row, int col) {
                JTextField editor = (JTextField) super.getTableCellEditorComponent(table, value,isSelected,row,col);
                // Center text while editing
                editor.setHorizontalAlignment(JTextField.CENTER);
                // Copy and steal the border thickness code from above
                int top = (row == 0 ? 4 : 1); // top and
                int left = (col == 0 ? 4 : 1); // left border
                int bottom = ((row % 3) == 2 ? 4 : 1); // every 3rd row
                int right = ((col % 3) == 2 ? 4 : 1); // every 3rd col
                // Match font to unedited font style
                editor.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
                editor.setBorder(BorderFactory.createMatteBorder(top,left,bottom,right,Color.BLACK));
                return editor;
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SudokUI");
        frame.setContentPane(new SudokUI().sudokuPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(800, 130, 500, 600);
        frame.setVisible(true);
    }
}
