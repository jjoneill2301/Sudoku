import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SudokUI {

    private final Color DARKER  = new Color(3, 4, 94);
    private final Color DARK   = new Color(0, 119, 182);
    private final Color NEUTRAL  = new Color(0, 180, 216);
    private final Color LIGHTER  = new Color(144, 224, 239);
    private final Color LIGHTEST = new Color(202, 240, 248);

    private JPanel  sudokuPanel;
    private JTable  sudokuGame;
    private JButton buttonEasy;
    private JButton buttonMed;
    private JButton buttonHard;

    private void createUIComponents() {
        startPuzzle();
    }

    private void startPuzzle() {
        SudokuBoardWithCells sudokuBoard = new SudokuBoardWithCells();
        Cell[][] unsolvedBoard = sudokuBoard.getPuzzleUnsolved();

        sudokuPanel = new JPanel(null);
        sudokuPanel.setOpaque(true);
        sudokuPanel.setBackground(DARK);
        sudokuGame = new JTable(9,9);
        designGame(sudokuGame);

        buttonEasy = new JButton("EASY");
        buttonMed  = new JButton("MEDIUM");
        buttonHard = new JButton("HARD");
        designButtons(buttonEasy, 0);
        designButtons(buttonMed, 161);
        designButtons(buttonHard,322);

        buttonEasy.addActionListener(_ -> {
            buttonEasy.setVisible(false);
            buttonMed.setVisible(false);
            buttonHard.setVisible(false);
            sudokuBoard.removeCellsFromGrid(33);// Easy puzzle generation
            for (int row = 0; row < 9; row++) { // Iterate through the table, setting the specific cell to the value
                for (int col = 0; col < 9; col++) { // contained in the solved sudokuBoard at point [row,col]
                    sudokuGame.setValueAt(unsolvedBoard[row][col].getValue(), row ,col);
                    if (unsolvedBoard[row][col].getValue() == 0) {
                        sudokuGame.setValueAt("", row, col);// Display as ""
                    }
                }
            }
        });

        buttonMed.addActionListener(_ -> {
            buttonEasy.setVisible(false);
            buttonMed.setVisible(false);
            buttonHard.setVisible(false);
            sudokuBoard.removeCellsFromGrid(44);// Medium puzzle generation
            for (int row = 0; row < 9; row++) { // Iterate through the table, setting the specific cell to the value
                for (int col = 0; col < 9; col++) { // contained in the solved sudokuBoard at point [row,col]
                    sudokuGame.setValueAt(unsolvedBoard[row][col].getValue(), row ,col);
                    if (unsolvedBoard[row][col].getValue() == 0) {
                        sudokuGame.setValueAt("", row, col);// Display as ""
                    }
                }
            }
        });

        buttonHard.addActionListener(_ -> {
            buttonEasy.setVisible(false);
            buttonMed.setVisible(false);
            buttonHard.setVisible(false);
            sudokuBoard.removeCellsFromGrid(55);// Hard puzzle generation
            for (int row = 0; row < 9; row++) { // Iterate through the table, setting the specific cell to the value
                for (int col = 0; col < 9; col++) { // contained in the solved sudokuBoard at point [row,col]
                    sudokuGame.setValueAt(unsolvedBoard[row][col].getValue(), row ,col);
                    if (unsolvedBoard[row][col].getValue() == 0) {
                        sudokuGame.setValueAt("", row, col);// Display as ""
                    }
                }
            }
        });
    }

    private void designGame(JTable g) {
        g.setBounds(50, 50, 400, 500);
        g.setRowHeight(50);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
        g.setCellSelectionEnabled(false); // This prevents a multiselect rectangle being formed on drag.
        // ChatGPT helped me with custom GUI renderer here for thicker borders and centered text
        g.setShowGrid(false); // Disable default grid and gaps or else it will be ugly
        g.setIntercellSpacing(new Dimension(0,0));
        g.setBackground(DARK); // Area around buttons
        // Applies thick borders every third row/col as well as around the entire board
        g.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                setHorizontalAlignment(SwingConstants.CENTER); // Center text within each cell
                setBackground(DARK);
                setForeground(LIGHTER);
                // Calculate border thickness
                int top = (row == 0 ? 4 : 1);               // top and
                int left = (col == 0 ? 4 : 1);              // left border
                int bottom = ((row % 3) == 2 ? 4 : 1);      // every 3rd row
                int right = ((col % 3) == 2 ? 4 : 1);       // every 3rd col */
                Border b = BorderFactory.createMatteBorder(top, left, bottom, right, DARKER);
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
                editor.setHorizontalAlignment(JTextField.CENTER);
                editor.setBackground(DARK);
                editor.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
                editor.setForeground(LIGHTER);
                // Makes the cell currently being edited have slightly thicker borders
                editor.setBorder(BorderFactory.createMatteBorder(2,2,2,2,DARKER));
                return editor;
            }
        });
    }

    private void designButtons(JButton difficulty, int x) {
        difficulty.setBounds(x,475, 160,65);
        //difficulty.setBorder(BorderFactory.createMatteBorder(2,2,2,2,DARKER));
        difficulty.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        difficulty.setBackground(NEUTRAL);
        difficulty.setForeground(LIGHTEST);
        difficulty.setBorder(BorderFactory.createMatteBorder(2,2,2,2,DARKER));

//        difficulty.setRolloverEnabled(false); // Disables color shifting when the mouse hovers over each button
        difficulty.setFocusPainted(false); // Removes a tiny rectangle around each button's text
        /* Google search: "mouselistener signature"           Google AI Overview:
           public void mouseEntered(MouseEvent e): Invoked when the mouse enters a component.
           public void mouseExited(MouseEvent e): Invoked when the mouse exits a component.                           */
        difficulty.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { // Hover events
                difficulty.setBackground(NEUTRAL);
                difficulty.setForeground(DARKER);
                difficulty.setBorder(BorderFactory.createMatteBorder(2,2,2,2,DARKER));
            }

            @Override
            public void mouseExited(MouseEvent e) { // Hover events
                difficulty.setBackground(NEUTRAL);
                difficulty.setForeground(LIGHTEST);
                difficulty.setBorder(BorderFactory.createMatteBorder(2,2,2,2,DARKER));
            }
        });

        sudokuPanel.add(difficulty);
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("SudokUI");
        frame.setContentPane(new SudokUI().sudokuPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(800, 130, 500, 600);
        frame.setBackground(new Color(3, 4, 94)); // DARKER
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
