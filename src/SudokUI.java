import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

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
    private JLabel labelTimer;
    private JLabel labelDifficulty;

    public static void main(String[] args) {
        JFrame frame = new JFrame("SudokUI");
        frame.setContentPane(new SudokUI().sudokuPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(800, 130, 500, 600);
        frame.setBackground(new Color(144, 224, 239)); // LIGHTER (scope issue is preventing use of const)
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        startPuzzle();
    }

    private void startPuzzle() {
        SudokuBoardWithCells sudokuBoard = new SudokuBoardWithCells();
        Cell[][] unsolvedBoard = sudokuBoard.getPuzzleUnsolved();
        initGame();
        initButtons();
        initLabels();
        buttonEasy.addActionListener(_ -> {
            buttonEasy.setVisible(false);
            buttonMed.setVisible(false);
            buttonHard.setVisible(false);
            startInfo(sudokuBoard, unsolvedBoard, 30, labelArr, labelTimer, labelDifficulty, "Easy");
        });
        buttonMed.addActionListener( _ -> {
            buttonEasy.setVisible(false);
            buttonMed.setVisible(false);
            buttonHard.setVisible(false);
            startInfo(sudokuBoard, unsolvedBoard, 40, labelArr, labelTimer, labelDifficulty, "Medium");
        });
        buttonHard.addActionListener(_ -> {
            buttonEasy.setVisible(false);
            buttonMed.setVisible(false);
            buttonHard.setVisible(false);
            startInfo(sudokuBoard, unsolvedBoard, 50, labelArr, labelTimer, labelDifficulty, "Hard");
        });
    }

    private void startInfo(SudokuBoardWithCells sudokuBoard, Cell[][] unsolvedBoard, int intDifficulty, JLabel[] labelA, JLabel labelT, JLabel labelD, String strDifficulty) {
        sudokuBoard.removeCellsFromGrid(intDifficulty);
        for (int row = 0; row < 9; row++) {             //  Iterate through the table, setting the specific cell to the
            for (int col = 0; col < 9; col++) {         // value contained in the solved sudokuBoard at point [row,col]
                sudokuGame.setValueAt(unsolvedBoard[row][col].getValue(), row, col);
                switch (unsolvedBoard[row][col].getValue()) {
                    case 1: // Use the tally tied to the class
                        SudokuBoardWithCells.incrementTally(0);
                        break;
                    case 2:
                        SudokuBoardWithCells.incrementTally(1);
                        break;
                    case 3:
                        SudokuBoardWithCells.incrementTally(2);
                        break;
                    case 4:
                        SudokuBoardWithCells.incrementTally(3);
                        break;
                    case 5:
                        SudokuBoardWithCells.incrementTally(4);
                        break;
                    case 6:
                        SudokuBoardWithCells.incrementTally(5);
                        break;
                    case 7:
                        SudokuBoardWithCells.incrementTally(6);
                        break;
                    case 8:
                        SudokuBoardWithCells.incrementTally(7);
                        break;
                    case 9:
                        SudokuBoardWithCells.incrementTally(8);
                        break;
                    case 0:sudokuGame.setValueAt("", row, col);
                }
            }
        }

        for (int i = 0; i < labelA.length; i++) {
            labelA[i].setVisible(true);
            if(SudokuBoardWithCells.returnCellTally(i) == 9) {
                labelA[i].setOpaque(true); // If 9 of a number are filled upon generation,
                labelA[i].setBackground(DARK); // set the same visual cue that occurs when
                labelA[i].setForeground(LIGHTER); // stopCellEditing() clears a number
            }
            if (i == labelA.length - 1) {
                labelD.setVisible(true); // For non array labels, just call once
                labelD.setText("Playing: " + strDifficulty);
                labelT.setVisible(true);
                initTimer(labelT);
            }
        }
    }

    private void designDifficulty(JLabel d) {
        d.setBounds(243, 450, 237, 50);
        d.setVisible(false);
        d.setFont(new Font(Font.SERIF, Font.BOLD, 24));
        d.setBackground(new Color(0, 119, 182)); //
        d.setForeground(new Color(3, 4, 94));
        d.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(3, 4, 94)));
        sudokuPanel.add(d);
    }

    private void designTimer(JLabel t) {
        t.setBounds(3, 450, 237, 50);
        t.setVisible(false);
        t.setFont(new Font(Font.SERIF, Font.BOLD, 24));
        t.setBackground(new Color(0, 119, 182));
        t.setForeground(new Color(3, 4, 94));
        t.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(3, 4, 94)));
        sudokuPanel.add(t);
    }

    private void initTimer(JLabel labelTimer) {
        final long START = System.currentTimeMillis();
        Timer t = new Timer(1000, _ -> {
            long elapsed = System.currentTimeMillis() - START;
            int seconds = (int) (elapsed/1000);
            int minutes = (seconds/60);
            seconds %= 60;
            labelTimer.setText("Time Elapsed - " + String.format("%02d:%02d", minutes, seconds));
        });
        t.start();
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
            if (i == 2 || i == 5) {  // 2 vertical subgrid dividers from default renderer
                x += 55;
            } else {
                x += 53;
            }
            i++; // move along
            sudokuPanel.add(num);
        }
    }

    private void initLabels() {
        labelArr = new JLabel[9];
        labelTimer = new JLabel("Time Elapsed 00:00", SwingConstants.CENTER);
        labelDifficulty = new JLabel("", SwingConstants.CENTER);

        // Instantiate labels within the label array via an indexed loop, and then call design methods on final iteration
        for (int i = 0; i < labelArr.length; i++) {
            labelArr[i] = new JLabel(Integer.toString(i + 1), SwingConstants.CENTER);
            if(i == labelArr.length - 1) {
                designDifficulty(labelDifficulty); // Apply all design methods at last index so all are called once
                designTimer(labelTimer);
                designLabels(labelArr);
            }
        }
    }

    private void designGame(JTable g) {
        g.setBounds(50, 50, 400, 500);
        g.setRowHeight(50);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
        g.setCellSelectionEnabled(false);
        g.setShowGrid(false);
        g.setBackground(DARK);
        applyRenderer(g);
        applyEditor(g);
    }

    private void applyRenderer(JTable g) {
        g.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                setHorizontalAlignment(0);
                setBackground(DARK);
                setForeground(LIGHTER);
                // Calculate border thickness
                int top = (row == 0 ? 4 : 1);
                int left = (col == 0 ? 4 : 1);
                int bottom = ((row % 3) == 2 ? 4 : 1);      // every 3rd row
                int right = ((col % 3) == 2 ? 4 : 1);       // every 3rd col
                Border b = BorderFactory.createMatteBorder(top, left, bottom, right, LIGHTER);
                setBorder(b);
                return this;
            }
        });
    }

    private void applyEditor(JTable g) {
        g.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()) {
            String cellOldValue = "";
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value,
                                                         boolean isSelected, int row, int col) {
                cellOldValue = Objects.toString(value, ""); // cell is either a num or blank
                JTextField editor = (JTextField) super.getTableCellEditorComponent(table, value, isSelected, row, col);

                editor.setHorizontalAlignment(0);
                editor.setBackground(DARK);
                editor.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
                editor.setCaretColor(LIGHTER); // blinking | will match text color
                editor.setForeground(LIGHTER);
                editor.setSelectionColor(LIGHTEST);
                editor.setSelectedTextColor(DARKER);
                editor.setFocusable(true);
                for (KeyListener kl : editor.getKeyListeners()) {
                    editor.removeKeyListener(kl); // Without this, the incrementer fires repeatedly
                }
                editor.addKeyListener(new KeyAdapter() {    // If user types into a cell anything other than
                    @Override                               // a number or backspace, ignore
                    public void keyTyped(KeyEvent e) {      // (AI helped me with this keyListener)
                        char c = e.getKeyChar();
                        if (c < '1' || c > '9') {
                            if (c == '\b') { // If not a number but is a backspace
                                editor.setText(""); // otherwise a backspace character shows
                                stopCellEditing(); // Overridden
                            }
                            e.consume();
                            return;
                        }
                    // This code can only happen if the above did not
                    editor.setText(""+c);
                    stopCellEditing(); // Overridden
                    }
                });
                // Makes the cell currently being edited have slightly thicker borders
                editor.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, LIGHTER));
                return editor;
            }

            // Override stopCellEditing() method means I have control over what happens depending on what the user does
            @Override
            public boolean stopCellEditing() {
                String cellNewValue = getCellEditorValue().toString(); // to compare with cellOldValue
                // If the user clears a cell value that had info in it:
                if (!cellOldValue.isEmpty() && cellNewValue.isEmpty()) {
                    SudokuBoardWithCells.decrementTally(Integer.parseInt(cellOldValue)-1);
                    labelArr[Integer.parseInt(cellOldValue)-1].setOpaque(false);
                    labelArr[Integer.parseInt(cellOldValue)-1].setBackground(DARK); // seems redundant but only setting
                    labelArr[Integer.parseInt(cellOldValue)-1].setForeground(LIGHTER); // opacity here does nothing
                // If the user enters a value into an empty cell:
                } else if (cellOldValue.isEmpty() && !cellNewValue.isEmpty()) {
                    SudokuBoardWithCells.incrementTally(Integer.parseInt(cellNewValue)-1);
                    if(SudokuBoardWithCells.returnCellTally(Integer.parseInt(cellNewValue)-1) == 9) {
                        labelArr[Integer.parseInt(cellNewValue)-1].setOpaque(true);
                        labelArr[Integer.parseInt(cellNewValue)-1].setBackground(DARK);
                        labelArr[Integer.parseInt(cellNewValue)-1].setForeground(LIGHTER);
                    }
                }
                return super.stopCellEditing();
            }
        });
    }

    private void initGame() {
        sudokuPanel = new JPanel(null);
        sudokuPanel.setBackground(DARK);

        sudokuGame = new JTable(9, 9);
        designGame(sudokuGame);
    }


    private void designButtons(JButton difficulty, int x) {
        difficulty.setBounds(x, 475, 160, 65);
        difficulty.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        difficulty.setBackground(new Color(0, 119, 182));
        difficulty.setForeground(new Color(144, 224, 239));
        difficulty.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(144, 224, 239)));
        difficulty.setFocusPainted(false);
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

    private void initButtons() {
        buttonEasy = new JButton("EASY");
        buttonMed = new JButton("MEDIUM");
        buttonHard = new JButton("HARD");
        designButtons(buttonEasy, 0);
        designButtons(buttonMed, 162);
        designButtons(buttonHard, 323);
    }
}