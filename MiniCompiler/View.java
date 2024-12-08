package MiniCompiler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class View {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Code Editor");
        frame.setSize(1300, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);


        frame.getContentPane().setBackground(new Color(0x181C14));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(0, 0, 250, 700);
        buttonPanel.setBackground(new Color(0x3C3D37));
        frame.add(buttonPanel);

        JButton button1 = createStyledButton("Open File");
        button1.setBounds(10, 10, 200, 75);
        button1.setForeground(Color.WHITE);
        buttonPanel.add(button1);

        JButton button2 = createStyledButton("Lexical Analysis");
        button2.setBounds(10, 95, 200, 75);
        button2.setEnabled(false);
        buttonPanel.add(button2);

        JButton button3 = createStyledButton("Syntax Analysis");
        button3.setBounds(10, 180, 200, 75);
        button3.setEnabled(false);
        buttonPanel.add(button3);

        JButton button4 = createStyledButton("Semantic Analysis");
        button4.setBounds(10, 265, 200, 75);
        button4.setEnabled(false);
        buttonPanel.add(button4);

        JButton button5 = createStyledButton("Clear");
        button5.setBounds(10, 350, 200, 75);
        button5.setForeground(Color.GRAY);
        buttonPanel.add(button5);

        JPanel codePanel = new JPanel();
        codePanel.setLayout(null);
        codePanel.setBounds(250, 0, 1050, 700);
        codePanel.setBackground(new Color(0x697565));
        frame.add(codePanel);

        JTextField resultBar = new JTextField("Result: Ready");
        resultBar.setEditable(false);
        resultBar.setHorizontalAlignment(JTextField.LEFT);
        resultBar.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultBar.setBackground(new Color(0x3C3D37));
        resultBar.setForeground(Color.WHITE);
        resultBar.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        resultBar.setBounds(10, 10, 1000, 30);
        codePanel.add(resultBar);

        JTextArea codeTextArea = new JTextArea();
        codeTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        codeTextArea.setLineWrap(true);
        codeTextArea.setWrapStyleWord(true);
        codeTextArea.setMargin(new Insets(10, 10, 10, 10));
        codeTextArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        codeTextArea.setBackground(new Color(0xECDFCC));

        JScrollPane scrollPane = new JScrollPane(codeTextArea);
        scrollPane.setBounds(10, 50, 1000, 590);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        codePanel.add(scrollPane);

        frame.setVisible(true);

        button1.addActionListener(e -> {
            String fileContent = OpenFile.openFile(frame);
            if (fileContent != null) {
                codeTextArea.setText(fileContent);
                resultBar.setText("File Opened: " + fileContent.substring(0, Math.min(20, fileContent.length())) + "...");
                button2.setEnabled(true);
            } else {
                resultBar.setText("No file selected");
            }
        });

        button2.addActionListener(e -> {
            String code = codeTextArea.getText();
            boolean isValid = LexicalAnalysis.performLexicalAnalysis(code);
            resultBar.setText(isValid ? "Passed Lexical Analysis" : "Failed Lexical Analysis");
            if (isValid) {
                button3.setEnabled(true);
            } else {
                highlightClearButton(button5);
            }
        });

        button3.addActionListener(e -> {
            String code = codeTextArea.getText();
            List<String> codeLines = List.of(code.split("\\n"));
            boolean isSyntaxValid = SyntaxAnalyzer.performSyntaxAnalysis(codeLines);
            resultBar.setText(isSyntaxValid ? "Passed Syntax Analysis" : "Failed Syntax Analysis");
            if (isSyntaxValid) {
                button4.setEnabled(true);
            } else {
                highlightClearButton(button5);
            }
        });

        button4.addActionListener(e -> {
            String code = codeTextArea.getText();
            List<String> codeLines = List.of(code.split("\\n"));
            boolean isSemanticValid = SemanticAnalysis.performSemanticAnalysis(codeLines);
            resultBar.setText(isSemanticValid ? "Passed Semantic Analysis" : "Failed Semantic Analysis");
            if (!isSemanticValid) {
                highlightClearButton(button5);
            }
        });

        button5.addActionListener(e -> {
            codeTextArea.setText("");
            resultBar.setText("Result: Ready");
            button2.setEnabled(false);
            button3.setEnabled(false);
            button4.setEnabled(false);
            button5.setBackground(null);
        });
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setBackground(new Color(0x181C14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));


        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0x299479));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0x181C14));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(new Color(0x35a576));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(new Color(0x181C14));
            }
        });

        return button;
    }

    private static void highlightClearButton(JButton button) {
        button.setBackground(Color.RED);
    }
}