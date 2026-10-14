package org.example;

import org.expresssion.Assignment;
import org.expresssion.Expression;
import org.parsing.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();

        Parser parser = new Parser();
//        System.out.println(string.matches("([a-zA-Z0-9+/*\\-(). \t\n\r])+"));
        Expression expression = parser.getExpressionFromString(string);
        System.out.println(expression);
//        System.out.println(parser.getTokens());
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
