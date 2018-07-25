package com.example.phonepe;

import java.io.*;

public class SyntaxTree {
  private StreamTokenizer expressionValues;

  private int symbol = 1;
  public static final int INVALID = -1;

  public static final int OR = 2;
  public static final int AND = 3;
  public static final int NOT = 4;

  public static final int TRUE = 5;
  public static final int FALSE = 6;

  public static final int OPEN = 7;
  public static final int CLOSE = 8;

  public static final String TRUE_STRING = "true";
  public static final String FALSE_STRING = "false";

  public SyntaxTree(InputStream in) {
    Reader reader = new BufferedReader(new InputStreamReader(in));
    expressionValues = new StreamTokenizer(reader);
  }

  public int getNextValue() {
    try {
      int nextValue = expressionValues.nextToken();
      if (nextValue == StreamTokenizer.TT_WORD) {
        if (expressionValues.sval.equalsIgnoreCase(TRUE_STRING))
          symbol = TRUE;
        else if (expressionValues.sval.equalsIgnoreCase(FALSE_STRING))
          symbol = FALSE;
      } else if (nextValue == '(') {
        symbol = OPEN;
      } else if (nextValue == ')') {
        symbol = CLOSE;
      } else if (nextValue == '&') {
        symbol = AND;
      } else if (nextValue == '|') {
        symbol = OR;
      } else if (nextValue == '!') {
        symbol = NOT;
      } else {
        symbol = INVALID;
      }
      return symbol;
    } catch (IOException e) {
      System.out.println(false);
      System.exit(-1);
    }
    return -100;
  }

}
