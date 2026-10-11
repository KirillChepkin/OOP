package org.main;

import org.parsing.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();

//        List<String> tokens = new ArrayList<>();
//        Pattern pattern = Pattern.compile("[0-9]*\\.?[0-9]+|[a-zA-Z][a-zA-Z0-9]*|[-+*/()^]");
//        Matcher matcher = pattern.matcher(expression);
//        while (matcher.find()) {
//            tokens.add(matcher.group());
//        }
//        System.out.println(tokens);

        System.out.println(Parser.parseExpressionTokens(expression));
    }
}
