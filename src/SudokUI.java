import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.border.Border;
import java.awt.*;
import javax.swing.UIManager.*;
public class SudokUI {

    private JPanel  sudokuPanel;
    private JTable  sudokuGame;
    private JButton buttonEasy;
    private JButton buttonMed;
    private JButton buttonHard;

    private void createUIComponents() {
        startPuzzle();
        sudokuPanel.setOpaque(true);
        sudokuPanel.setBackground(Color.BLACK);
    }

    private void startPuzzle() {
        SudokuBoardWithCells sudokuBoard = new SudokuBoardWithCells();
        Cell[][] unsolvedBoard = sudokuBoard.getPuzzleUnsolved();

        sudokuPanel = new JPanel(null);
        sudokuGame = new JTable(9,9);

        designGame(sudokuGame);
        designButtons(sudokuPanel);

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
        g.setForeground(Color.WHITE); // White font
        g.setCellSelectionEnabled(false); // This prevents a multiselect rectangle being formed on drag.
        // ChatGPT helped me with custom GUI renderer here for thicker borders and centered text
        g.setShowGrid(false); // Disable default grid and gaps or else it will be ugly
        g.setIntercellSpacing(new Dimension(0,0));
        g.setOpaque(true);
        g.setBackground(Color.GRAY);
        // Applies thick borders every third row/col as well as around the entire board
        g.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                setHorizontalAlignment(SwingConstants.CENTER); // Center text within each cell
                setBackground(Color.DARK_GRAY);
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
                editor.setBackground(Color.GRAY);
                // Make each selected cell have thicker borders
                int top = 2; // top and
                int left = 2; // left border
                int bottom = 2; // every 3rd row
                int right = 2; // every 3rd col
                // Match font to unedited font style
                editor.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
                editor.setForeground(Color.WHITE); // White font while editing
                editor.setBorder(BorderFactory.createMatteBorder(top,left,bottom,right,Color.BLACK));
                return editor;
            }
        });
    }

    private void designButtons(JPanel sudokuPanel) {
        buttonEasy = new JButton("EASY");
        buttonEasy.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        buttonEasy.setBounds(30,475, 100,65);

        buttonMed = new JButton("MEDIUM");
        buttonMed.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        buttonMed.setBounds(196, 475, 100, 65);

        buttonHard = new JButton("HARD");
        buttonHard.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        buttonHard.setBounds(362, 475, 100, 65);

        sudokuPanel.add(buttonEasy);
        sudokuPanel.add(buttonMed);
        sudokuPanel.add(buttonHard);
    }



    public static void main(String[] args) {
        try { // https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/nimbus.html
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                } // Makes my buttons look fancy :)
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        JFrame frame = new JFrame("SudokUI");
        frame.setContentPane(new SudokUI().sudokuPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(800, 130, 500, 600);
        frame.setVisible(true);
    }
}
