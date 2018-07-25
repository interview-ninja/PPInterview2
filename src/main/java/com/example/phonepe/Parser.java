package com.example.phonepe;

public class Parser {
	private SyntaxTree syntaxTree;
	private int symbol;
	private EvaluateExpression root;

	private final True t = new True();
	private final False f = new False();

	public Parser(SyntaxTree syntaxTree) {
		this.syntaxTree = syntaxTree;
	}

	public EvaluateExpression build() {
		checkOr();
		return root;
	}

	private void checkOr() {
		checkAnd();
		while (symbol == SyntaxTree.OR) {
			Or or = new Or();
			or.setLeft(root);
			checkAnd();
			or.setRight(root);
			root = or;
		}
	}

	private void checkAnd() {
		checkSymbol();
		while (symbol == SyntaxTree.AND) {
			And and = new And();
			and.setLeft(root);
			checkSymbol();
			and.setRight(root);
			root = and;
		}
	}

	private void checkSymbol() {
		symbol = syntaxTree.getNextValue();
		if (symbol == SyntaxTree.TRUE) {
			root = t;
			symbol = syntaxTree.getNextValue();
		} else if (symbol == SyntaxTree.FALSE) {
			root = f;
			symbol = syntaxTree.getNextValue();
		} else if (symbol == SyntaxTree.NOT) {
			Not not = new Not();
			checkSymbol();
			not.setChild(root);
			root = not;
		} else if (symbol == SyntaxTree.OPEN) {
			checkOr();
			symbol = syntaxTree.getNextValue();
		}
	}
}
