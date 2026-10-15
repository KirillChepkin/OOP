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

        Parser parser = new Parser();
        Expression expression = parser.getExpressionFromString(string);
        System.out.println(expression);
        string = scanner.nextLine();
        Assignment assignment = Parser.getAssignment(string);
        System.out.println(expression.eval(assignment));
        string = scanner.nextLine();
        Expression derivative = expression.derivative(string);
        System.out.println(derivative);
        string = scanner.nextLine();
        assignment = Parser.getAssignment(string);
        System.out.println(derivative.eval(assignment));
    }
}
