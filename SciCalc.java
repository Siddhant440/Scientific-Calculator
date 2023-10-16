import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SciCalc extends JFrame implements ActionListener {
    JTextField tfield;
    double temp, temp1, result, a;
    int k = 1, x = 0, y = 0, z = 0;
    char ch;
    JButton[] numButtons;
    JButton[] opButtons;
    JButton backspaceButton;
    Container cont;
    JRadioButton degreeButton;
    JRadioButton radianButton;
    ButtonGroup angleGroup;

    SciCalc() {
        cont = getContentPane();
        cont.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        degreeButton = new JRadioButton("Degree");
        radianButton = new JRadioButton("Radian");
        degreeButton.setSelected(true);
        angleGroup = new ButtonGroup();
        angleGroup.add(degreeButton);
        angleGroup.add(radianButton);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        radioPanel.add(degreeButton);
        radioPanel.add(radianButton);
        topPanel.add("South", radioPanel); 


        tfield = new JTextField(30);
        tfield.setHorizontalAlignment(SwingConstants.RIGHT);
        tfield.setPreferredSize(new Dimension(tfield.getPreferredSize().width, 35));
        tfield.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent keyevent) {
                char c = keyevent.getKeyChar();
                if (!(c >= '0' && c <= '9')) {
                    keyevent.consume();
                }
            }
        });

        Font font = tfield.getFont();
        Font boldFont = new Font(font.getName(), Font.BOLD, 13);
        tfield.setFont(boldFont);

        topPanel.add("Center", tfield);

        JButton backspaceButton = new JButton("<");
        backspaceButton.setActionCommand("Backspace");
        backspaceButton.addActionListener(this);
        backspaceButton.setPreferredSize(new Dimension(39, 20)); 
        topPanel.add("East", backspaceButton);

        cont.add("North", topPanel);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(0, 3, 4, 3));

        numButtons = new JButton[11];
        for (int i = 1; i <= 9; i++) {
            numButtons[i] = new JButton(String.valueOf(i));
            numButtons[i].setPreferredSize(new Dimension(30, 30));
            numButtons[i].addActionListener(this);
            leftPanel.add(numButtons[i]);
        }

        JButton acButton = new JButton("AC");
        acButton.addActionListener(this);
        leftPanel.add(acButton);

        numButtons[0] = new JButton("0");
        numButtons[0].setPreferredSize(new Dimension(30, 30));
        numButtons[0].addActionListener(this);
        leftPanel.add(numButtons[0]);

        JButton equalsButton = new JButton("=");
        equalsButton.addActionListener(this);
        leftPanel.add(equalsButton);

        cont.add("West", leftPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(0, 3, 4, 3));

        String[] operators = { "+", "-", "*", "/", "+/-", ".", "log", "1/x", "Exp", "x^2", "x^3", "Sqrt", "SIN", "COS", "TAN", "n!" };
        for (String operator : operators) {
            JButton opButton = new JButton(operator);
            opButton.addActionListener(this);
            rightPanel.add(opButton);
        }

        cont.add("Center", rightPanel);

        JPanel leftPanelWithPadding = new JPanel();
        leftPanelWithPadding.setLayout(new BorderLayout());
        leftPanelWithPadding.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        leftPanelWithPadding.add(leftPanel, BorderLayout.CENTER);

        JPanel rightPanelWithPadding = new JPanel();
        rightPanelWithPadding.setLayout(new BorderLayout());
        rightPanelWithPadding.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        rightPanelWithPadding.add(rightPanel, BorderLayout.CENTER);

        cont.add("West", leftPanelWithPadding);
        cont.add("Center", rightPanelWithPadding);

        JPanel footerPanel = new JPanel();
        footerPanel.setPreferredSize(new Dimension(10, 20)); 
        cont.add("South", footerPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if (s.matches("\\d")) { 
            if (z == 0) {
                tfield.setText(tfield.getText() + s);
            } else {
                tfield.setText(s);
                z = 0;
            }
        }         if (s.equals(".")) {
            if (y == 0) {
                tfield.setText(tfield.getText() + ".");
                y = 1;
            }

        } else if (s.equals("+/-")) {
            String text = tfield.getText();
            if (!text.isEmpty() && !text.equals("0")) {
                if (text.charAt(0) != '-') {
                    tfield.setText("-" + text);
                tfield.setText(tfield.getText());
            } else {
                tfield.setText(text.substring(1));
            }
            }
        } else if (s.equals("AC")) {
            tfield.setText("");
            x = 0;
            y = 0;
            z = 0;
            temp = 0;
            ch = ' '; 
        } else if (s.equals("=")) {
            if (tfield.getText().equals("")) {
                tfield.setText("");
            } else {
                temp1 = Double.parseDouble(tfield.getText());
                switch (ch) {
                    case '+':
                        result = temp + temp1;
                        break;
                    case '-':
                        result = temp - temp1;
                        break;
                    case '/':
                        result = temp / temp1;
                        break;
                    case '*':
                        result = temp * temp1;
                        break;
                    default:
                        result = temp1;
                        break;
                }

                if (result == (int) result) { 
                    tfield.setText(String.valueOf((int) result)); 
                } else {
                    tfield.setText(String.valueOf(result)); 
                }

                temp = result; 
                z = 1;
            }
        } else if (s.matches("[+\\-*/]")) { 
            if (tfield.getText().equals("")) {
                tfield.setText("");
                temp = 0;
                ch = s.charAt(0);
            } else {
                temp = Double.parseDouble(tfield.getText());
                tfield.setText("");
                ch = s.charAt(0);
                y = 0;
                x = 0;
            }
            tfield.requestFocus();
        } else if (s.equals("Backspace")) {
            if (!tfield.getText().isEmpty()) {
                String currentText = tfield.getText();
                tfield.setText(currentText.substring(0, currentText.length() - 1));
            }
        } else if (s.equals("log")) {
            if (!tfield.getText().equals("")) {
                double val = Double.parseDouble(tfield.getText());
                if (val > 0) {
                    result = Math.log10(val);
                    tfield.setText(String.valueOf(result));
                } else {
                    tfield.setText("Invalid Input");
                }
            }
        } else if (s.equals("1/x")) {
            if (!tfield.getText().equals("")) {
                double val = Double.parseDouble(tfield.getText());
                if (val != 0) {
                    result = 1.0 / val;
                    tfield.setText(String.valueOf(result));
                } else {
                    tfield.setText("Division by zero error");
                }
            }
        } else if (s.equals("Sqrt")) {
            if (!tfield.getText().equals("")) {
                double val = Double.parseDouble(tfield.getText());
                if (val >= 0) {
                    result = Math.sqrt(val);
                    tfield.setText(String.valueOf(result));
                } else {
                    tfield.setText("Invalid Input");
                }
            }
        } else if (s.equals("Exp")) {
            if (!tfield.getText().equals("")) {
                double val = Double.parseDouble(tfield.getText());
                result = Math.exp(val);
                tfield.setText(String.valueOf(result));
            }
        } else if (s.equals("x^2")) {
            if (!tfield.getText().equals("")) {
                double val = Double.parseDouble(tfield.getText());
                result = Math.pow(val, 2);
                tfield.setText(String.valueOf(result));
            }
        } else if (s.equals("x^3")) {
            if (!tfield.getText().equals("")) {
                double val = Double.parseDouble(tfield.getText());
                result = Math.pow(val, 3);
                tfield.setText(String.valueOf(result));
            }
        }  else if (s.equals("n!")) {
            if (!tfield.getText().equals("")) {
                double val = Double.parseDouble(tfield.getText());
                if (val >= 0 && val == (int) val) {
                    result = fact(val);
                    tfield.setText(String.valueOf(result));
                } else {
                    tfield.setText("Invalid Input");
                }
            }
        } 
             if (degreeButton.isSelected()) {

            if (s.equals("SIN")) {
                if (!tfield.getText().equals("")) {
                    double val = Double.parseDouble(tfield.getText());
                    result = Math.sin(Math.toRadians(val));
                    tfield.setText(String.valueOf(result));
                }
            } else if (s.equals("COS")) {
                if (!tfield.getText().equals("")) {
                    double val = Double.parseDouble(tfield.getText());
                    result = Math.cos(Math.toRadians(val));
                    tfield.setText(String.valueOf(result));
                }
            } else if (s.equals("TAN")) {
                if (!tfield.getText().equals("")) {
                    double val = Double.parseDouble(tfield.getText());
                    result = Math.tan(Math.toRadians(val));
                    tfield.setText(String.valueOf(result));
                }
            }
        } else if (radianButton.isSelected()) {

            if (s.equals("SIN")) {
                if (!tfield.getText().equals("")) {
                    double val = Double.parseDouble(tfield.getText());
                    result = Math.sin(val);
                    tfield.setText(String.valueOf(result));
                }
            } else if (s.equals("COS")) {
                if (!tfield.getText().equals("")) {
                    double val = Double.parseDouble(tfield.getText());
                    result = Math.cos(val);
                    tfield.setText(String.valueOf(result));
                }
            } else if (s.equals("TAN")) {
                if (!tfield.getText().equals("")) {
                    double val = Double.parseDouble(tfield.getText());
                    result = Math.tan(val);
                    tfield.setText(String.valueOf(result));
                }
            }
        }

else if (s.equals("x^2")) {
    if (!tfield.getText().equals("")) {
        double val = Double.parseDouble(tfield.getText());
        result = Math.pow(val, 2);
        tfield.setText(String.valueOf(result));
    }
}

else if (s.equals("x^3")) {
    if (!tfield.getText().equals("")) {
        double val = Double.parseDouble(tfield.getText());
        result = Math.pow(val, 3);
        tfield.setText(String.valueOf(result));
    }
} else if (s.equals("Exp")) {
    if (!tfield.getText().equals("")) {
        double val = Double.parseDouble(tfield.getText());
        result = Math.exp(val);
        tfield.setText(String.valueOf(result));
    }
}


        tfield.requestFocus();
    }

    double fact(double x) {
        if (x == 0) {
            return 1;
        } else {
            return x * fact(x - 1);
        }
    } 

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        SciCalc f = new SciCalc();
        f.setTitle("Calculator");
        f.pack();
        f.setVisible(true);
    }
}
