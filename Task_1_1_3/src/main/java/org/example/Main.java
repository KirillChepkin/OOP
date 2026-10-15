package org.example;

import java.util.Scanner;
import org.expression.Assignment;
import org.expression.Expression;
import org.parsing.Parser;

/**
 * Standard main class.
 */
public class Main {
    /**
     * Standard main method.
     *
     * @param args default args.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();

        // Parsing the expression itself.
        Parser parser = new Parser();
        Expression expression = parser.getExpressionFromString(string);
        System.out.println(parser.getTokens());
        System.out.println(expression);

        // Parsing the assignment.
        string = scanner.nextLine();
        Assignment assignment = Parser.getAssignment(string);
        System.out.println(expression.eval(assignment));

        // Differentiating the expression.
        string = scanner.nextLine();
        Expression derivative = expression.derivative(string);
        System.out.println(derivative);

        // Parsing the assignment for derivative.
        string = scanner.nextLine();
        assignment = Parser.getAssignment(string);
        System.out.println(derivative.eval(assignment));
    }
}
