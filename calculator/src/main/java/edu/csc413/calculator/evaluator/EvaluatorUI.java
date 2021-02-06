package edu.csc413.calculator.evaluator;

import edu.csc413.calculator.operators.*;
import edu.csc413.calculator.exceptions.*;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EvaluatorUI extends JFrame implements ActionListener {

    private TextField expressionTextField = new TextField();
    private Panel buttonPanel = new Panel();
    private String expression = null;

    // total of 20 buttons on the calculator,
    // numbered from left to right, top to bottom
    // bText[] array contains the text for corresponding buttons
    private static final String[] buttonText = {
            "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3",
            "*", "0", "^", "=", "/", "(", ")", "C", "CE"
    };

    /**
     * C  is for clear, clears entire expression
     * CE is for clear expression, clears last entry up until the last operator.
     */
    private Button[] buttons = new Button[buttonText.length];
    private ActionEvent actionEventObject;

    public static void main(String argv[]) {
        new EvaluatorUI();
    }

    public EvaluatorUI() {
        setLayout(new BorderLayout());
        this.expressionTextField.setPreferredSize(new Dimension(600, 50));
        this.expressionTextField.setFont(new Font("Courier", Font.BOLD, 28));

        add(expressionTextField, BorderLayout.NORTH);
        expressionTextField.setEditable(false);

        add(buttonPanel, BorderLayout.CENTER);
        buttonPanel.setLayout(new GridLayout(5, 4));

        //create 20 buttons with corresponding text in bText[] array
        Button tempButtonReference;
        for (int i = 0; i < EvaluatorUI.buttonText.length; i++) {
            tempButtonReference = new Button(buttonText[i]);
            tempButtonReference.setFont(new Font("Courier", Font.BOLD, 28));
            buttons[i] = tempButtonReference;
        }

        //add buttons to button panel
        for (int i = 0; i < EvaluatorUI.buttonText.length; i++) {
            buttonPanel.add(buttons[i]);
        }

        //set up buttons to listen for mouse input
        for (int i = 0; i < EvaluatorUI.buttonText.length; i++) {
            buttons[i].addActionListener(this);
        }

        setTitle("Calculator");
        setSize(400, 400);
        setLocationByPlatform(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * This function is triggered anytime a button is pressed
     * on our Calculator GUI.
     * @param actionEventObject Event object generated when a
     *                    button is pressed.
     */

    public void actionPerformed(ActionEvent actionEventObject)  {
        this.actionEventObject = actionEventObject;
        String keyCommand = actionEventObject.getActionCommand();
        String oldText = this.expressionTextField.getText();
        this.expressionTextField.setText(oldText + keyCommand);

        if (keyCommand.equals("=")) {
            Evaluator e = new Evaluator();
            try {
                expression = Integer.toString(e.evaluateExpression(expression));
            } catch (InvalidTokenException ex) {
                ex.printStackTrace();
            }
            this.expressionTextField.setText(expression);
        }

        else if (keyCommand.equals("CE")) { // Clear previous entry
            if (expression != null) {
                while (!Operator.check(String.valueOf(expression.charAt(expression.length() - 1)))) {
                    expression = expression.substring(0, expression.length() - 1);
                    this.expressionTextField.setText(expression);
                }
            } else
                this.expressionTextField.setText(null);
        }

        else if (keyCommand.equals("C")) { // Clear everything
            this.expressionTextField.setText(null);
            expression = null;
        }

        else {
            if (expression == null)
                expression = keyCommand;
            else
                expression += keyCommand;
        }

    }
}
