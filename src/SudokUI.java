import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SudokUI {

    private final Color DARKER = new Color(3, 4, 94);
    private final Color DARK = new Color(0, 119, 182);
    private final Color LIGHTER = new Color(144, 224, 239);
    private final Color LIGHTEST = new Color(202, 240, 248);

    private JPanel sudokuPanel;
    private JTable sudokuGame;

    private JButton buttonEasy;
    private JButton buttonMed;
    private JButton buttonHard;

    private JLabel[] labelArr;
    private JLabel debug;

    public static void main(String[] args) {
        JFrame frame = new JFrame("SudokUI");
        frame.setContentPane(new SudokUI().sudokuPanel);
        frame.setAlwaysOnTop(true); // For testing so it doesn't go away when clicking off panel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(800, 130, 500, 600);
        frame.setBackground(new Color(144, 224, 239)); // LIGHTER
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        startPuzzle();
        designButtons(buttonEasy, 0);
        designButtons(buttonMed, 162);
        designButtons(buttonHard, 323);

        labelArr = new JLabel[9];
        debug = new JLabel();
        for (int i = 0; i < labelArr.length; i++) {
            labelArr[i] = new JLabel(Integer.toString(i + 1), SwingConstants.CENTER);
        }

        designLabels(labelArr);
        debugLabel(debug);
    }

    private void startPuzzle() {
        SudokuBoardWithCells sudokuBoard = new SudokuBoardWithCells();
        Cell[][] unsolvedBoard = sudokuBoard.getPuzzleUnsolved();

        sudokuPanel = new JPanel(null);
        sudokuPanel.setBackground(DARK);

        sudokuGame = new JTable(9, 9);
        designGame(sudokuGame);

        buttonEasy = new JButton("EASY");
        buttonMed = new JButton("MEDIUM");
        buttonHard = new JButton("HARD");

        buttonEasy.addActionListener(_ -> startInfo(sudokuBoard, unsolvedBoard, 30, labelArr));

        buttonMed.addActionListener(_ -> startInfo(sudokuBoard, unsolvedBoard, 40, labelArr));

        buttonHard.addActionListener(_ -> startInfo(sudokuBoard, unsolvedBoard, 50, labelArr));
    }

    private void startInfo(SudokuBoardWithCells sudokuBoard, Cell[][] unsolvedBoard, int difficulty, JLabel[] labelArr) {
        sudokuBoard.removeCellsFromGrid(difficulty);// Hard puzzle generation
        for (int row = 0, i = 0; row < 9 && i < 9; row++, i++) { // Iterate through the table, setting the specific cell to the value
            for (int col = 0; col < 9; col++) { // contained in the solved sudokuBoard at point [row,col]
                sudokuGame.setValueAt(unsolvedBoard[row][col].getValue(), row, col);
                if (unsolvedBoard[row][col].getValue() == 0) {
                    sudokuGame.setValueAt("", row, col);// Display as ""
                }
                System.out.println(sudokuBoard.returnCellTally(i));
                switch (unsolvedBoard[row][col].getValue()) {
                    case 0:
                        sudokuGame.setValueAt("", row, col);
                        break;
                    case 1:
                        sudokuBoard.incrementTally(0);
                        break;
                    case 2:
                        sudokuBoard.incrementTally(1);
                        break;
                    case 3:
                        sudokuBoard.incrementTally(2);
                        break;
                    case 4:
                        sudokuBoard.incrementTally(3);
                        break;
                    case 5:
                        sudokuBoard.incrementTally(4);
                        break;
                    case 6:
                        sudokuBoard.incrementTally(5);
                        break;
                    case 7:
                        sudokuBoard.incrementTally(6);
                        break;
                    case 8:
                        sudokuBoard.incrementTally(7);
                        break;
                    case 9:
                        sudokuBoard.incrementTally(8);
                        break;
                }
            }
        }

        buttonEasy.setVisible(false);   // Remove buttons to make space for info
        buttonMed.setVisible(false);
        buttonHard.setVisible(false);

        debug.setVisible(true);
        for (JLabel num : labelArr) {
            num.setVisible(true);  // Show labels once buttons are gone
        }

    }

    private void debugLabel(JLabel d) {
        d.setBounds(325, 450, 154, 50);
        d.setVisible(false);
        d.setBackground(new Color(0, 119, 182));
        d.setForeground(new Color(3, 4, 94));
        d.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(3, 4, 94)));
        sudokuPanel.add(d);
    }

    private void designLabels(JLabel[] numberLabels) {
        int x = 3;
        int i = 0;
        for (JLabel num : numberLabels) {
            num.setVisible(false);
            num.setBounds(x, 506, 50, 50);
            num.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
            num.setBackground(new Color(0, 119, 182));
            num.setForeground(new Color(3, 4, 94));
            num.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(3, 4, 94)));
            if (i == 2 || i == 5) {  // This matches the spacing of the 2 vertical subgrid dividers
                x += 55;
            } else {
                x += 53;
            }
            i++;
            sudokuPanel.add(num);
            System.out.print(num.getBounds() + "\n");
        }
    }

    private void designGame(JTable g) {
        g.setBounds(50, 50, 400, 500);
        g.setRowHeight(50);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
        g.setCellSelectionEnabled(false); // This prevents a multiselect rectangle being formed on drag.
        // ChatGPT helped me with custom GUI renderer here for thicker borders and centered text
        g.setShowGrid(false); // Disable default grid and gaps or else it will be ugly
//        g.setIntercellSpacing(new Dimension(0,0));
        g.setBackground(DARK); // Area around buttons
        // Applies thick borders every third row/col as well as around the entire board
        g.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                setHorizontalAlignment(0); // Center text within each cell
                setBackground(DARK);
                setForeground(LIGHTER);
                // Calculate border thickness
                int top = (row == 0 ? 4 : 1);               // top and
                int left = (col == 0 ? 4 : 1);              // left border
                int bottom = ((row % 3) == 2 ? 4 : 1);      // every 3rd row
                int right = ((col % 3) == 2 ? 4 : 1);       // every 3rd col */
                Border b = BorderFactory.createMatteBorder(top, left, bottom, right, LIGHTER);
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
                JTextField editor = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, col);
                editor.setHorizontalAlignment(0);
                editor.setBackground(DARK);
                editor.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
                editor.setCaretColor(LIGHTER); // blinking | will match text color
                editor.setForeground(LIGHTER);
                editor.setSelectionColor(LIGHTEST);
                editor.setSelectedTextColor(DARKER);
                editor.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        char c = e.getKeyChar();
                        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                            e.consume(); // If user types into a cell anything other than
                        }                // a number or backspace, ignore
                    }                    // (AI helped me with this keyListener)
                });
                // Makes the cell currently being edited have slightly thicker borders
                editor.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, LIGHTER));
                return editor;
            }
        });
    }

    private void designButtons(JButton difficulty, int x) {
        difficulty.setBounds(x, 475, 160, 65);
        difficulty.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        difficulty.setBackground(new Color(0, 119, 182));
        difficulty.setForeground(new Color(144, 224, 239));
        difficulty.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(144, 224, 239)));
        difficulty.setFocusPainted(false); // Removes a tiny rectangle around each button's text
        /* Google search: "mouselistener signature"           Google AI Overview:
           public void mouseEntered(MouseEvent e): Invoked when the mouse enters a component.
           public void mouseExited(MouseEvent e): Invoked when the mouse exits a component.                           */
        difficulty.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { // Hover events
                difficulty.setBackground(DARK);
                difficulty.setForeground(DARKER);
                difficulty.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, DARKER));
            }

            @Override
            public void mouseExited(MouseEvent e) { // Hover events
                difficulty.setBackground(DARK);
                difficulty.setForeground(LIGHTEST);
                difficulty.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, LIGHTER));
            }
        });
        sudokuPanel.add(difficulty);
    }
}