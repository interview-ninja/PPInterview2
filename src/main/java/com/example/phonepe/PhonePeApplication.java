package com.example.phonepe;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PhonePeApplication {

    public static void main(String[] args) throws IOException {
        String jsonObject = "{\"brand\": {\"value\" : \"ford\"}, \"doors\":true, \"valueList\" : [{\"len\" : 5}]}";

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonMap = objectMapper.readTree(jsonObject);

        System.out.println(jsonMap.at("/brand/value"));

        Scanner sc = new Scanner((System.in));
        String expression = "";
        expression = sc.nextLine();

        Stack<String> values = new Stack<String>();
        String output = "";
        Stack<Character> ops = new Stack<Character>();
        char[] tokens = expression.toCharArray();
        try {
            for (int i = 0; i < tokens.length; ) {
                if (tokens[i] == ' ') {
                    i++;
                    continue;
                }
                if (tokens[i] == '$' || (tokens[i] >= '0' && tokens[i] <= '9') || (tokens[i] >= 'a' && tokens[i] <= 'z')
                        || (
                        tokens[i] >= 'A' && tokens[i] <= 'Z')) {
                    StringBuilder sbuf = new StringBuilder();
                    if (tokens[i] == '$') {
                        sbuf.append("/");
                        i++;
                    }

                    while (i < tokens.length && (tokens[i] >= '0' && tokens[i] <= '9'
                            || tokens[i] >= 'a' && tokens[i] <= 'z'
                            || tokens[i] >= 'A' && tokens[i] <= 'Z' || tokens[i] == '.')) {
                        if (tokens[i] == '.') {
                            sbuf.append('/');
                        } else
                            sbuf.append(tokens[i]);
                        i++;
                    }

                    try {
                        values.push(jsonMap.at(sbuf.toString()).toString().replaceAll("^\"|\"$", ""));
                    } catch (Exception e) {
                        System.out.println(false);
                        System.exit(-1);
                    }
                } else if (tokens[i] == '=') {
                    ops.push(tokens[i]);
                    i++;
                    if ((tokens[i] >= '0' && tokens[i] <= '9') || (tokens[i] >= 'a' && tokens[i] <= 'z') || (
                            tokens[i] >= 'A' && tokens[i] <= 'Z')) {
                        StringBuilder sbuf = new StringBuilder();
                        while (i < tokens.length && (tokens[i] >= '0' && tokens[i] <= '9'
                                || tokens[i] >= 'a' && tokens[i] <= 'z'
                                || tokens[i] >= 'A' && tokens[i] <= 'Z')) {
                            sbuf.append(tokens[i++]);
                        }
                        values.push(sbuf.toString());
                    }
                    output += (String.valueOf(applyOp(ops.pop(), values.pop(), values.pop())));
                } else {
                    output += (String.valueOf(tokens[i]));
                    i++;
                }
            }
        } catch (Exception e) {
            System.out.println(false);
            System.out.println(e);
            System.exit(-1);
        }
        //$brand.doors = 5
        //$doors=5
        //$brand=320
        //$hadf=5
        //$doors=5 & $brand.value=ford
        //!($doors=5 & $brand.value=ford)
        //!($doors=5 & $brand.value=ford) | $brand.value=hond | $brand.value=ford
//        System.out.println(output);
        SyntaxTree syntaxTree = new SyntaxTree(new ByteArrayInputStream(output.getBytes()));
        Parser parser = new Parser(syntaxTree);
        EvaluateExpression evalExpr = parser.build();
        System.out.println(evalExpr.interpret());
    }

    public static boolean applyOp(char op, String b, String a)
    {
        switch (op)
        {
            case '=':
                return a.equals(b);
        }
        return false;
    }
}


