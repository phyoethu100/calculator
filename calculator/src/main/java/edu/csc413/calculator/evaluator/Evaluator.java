package edu.csc413.calculator.evaluator;

import edu.csc413.calculator.exceptions.InvalidTokenException;
import edu.csc413.calculator.operators.Operator;

import java.util.Stack;
import java.util.StringTokenizer;

public class Evaluator {
  private Stack<Operand> operandStack;
  private Stack<Operator> operatorStack;
  private StringTokenizer expressionTokenizer;
  private static final String delimiters = " +/*-^()";

  public Evaluator() {
    operandStack = new Stack<>();
    operatorStack = new Stack<>();
  }

  public int evaluateExpression (String expression) throws InvalidTokenException {

    this.expressionTokenizer = new StringTokenizer (expression, this.delimiters, true);

    while (this.expressionTokenizer.hasMoreTokens()) {

      String expressionToken;
      // filter out spaces
      if (!(expressionToken = this.expressionTokenizer.nextToken()).equals(" ")) {
        // check if token is an operand
        if (Operand.check(expressionToken)) {
          operandStack.push(new Operand(expressionToken));
        }
        else
        {
          if (!Operator.check(expressionToken)) {
            throw new InvalidTokenException(expressionToken);
          }

          Operator newOperator = Operator.getOperator(expressionToken);

          if (!operatorStack.empty() && expressionToken.equals(")"))
          {
            processOperator(newOperator); // process until open parentheses is found
            continue;
          }

          while ((!operatorStack.empty()) && (!expressionToken.equals("(")) && (operatorStack.peek().priority() >= newOperator.priority())) {

            if (operatorStack.peek().priority() == newOperator.priority())
            {
              Operator operatorFromStack = operatorStack.pop();
              Operand operandTwo = operandStack.pop();
              Operand operandOne = operandStack.pop();
              Operand result = operatorFromStack.execute(operandOne, operandTwo);
              operandStack.push(result);
            }
            else
              processOperator(newOperator);
          }

          operatorStack.push(newOperator);
        }
      }
    }

    processOperator(Operator.getOperator(")"));
    return operandStack.pop().getValue();

  }

  /*
   Control gets here when we've picked up all of the tokens; you must add
   code to complete the evaluation - consider how the code given here
   will evaluate the expression 1+2*3
   When we have no more tokens to scan, the operand stack will contain 1 2
   and the operator stack will have + * with 2 and * on the top;
   In order to complete the evaluation we must empty the stacks,
   that is, we should keep evaluating the operator stack until it is empty;
   Suggestion: create a method that processes the operator stack until empty.
  */

  private void processOperator (Operator token) {

    while (!operatorStack.empty())
    {
      Operator operatorFromStack = operatorStack.pop();

      if (operatorFromStack == Operator.getOperator("("))
      {
        if (token == Operator.getOperator(")"))
          break;

        operatorStack.push(operatorFromStack);
          break;
      }

      Operand operandTwo = operandStack.pop();
      Operand operandOne = operandStack.pop();
      Operand result = operatorFromStack.execute(operandOne, operandTwo);
      operandStack.push(result);
    }
  }

}